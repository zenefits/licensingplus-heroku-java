package Core;

import Core.Nipr.*;
import Core.SalesForce.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.util.Calendar;
import Core.Utils.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by vthiruvengadam on 8/5/16.
 */
public class Reconciler extends Thread {

    static int SALES_FORCE_API_BATCH = 200;
    SalesForceClient SfClient = null;

    public void run() {

        System.out.println("Reconciler: Started the reconciler thread");
        SfClient = new SalesForceClient("https://test.salesforce.com/services/oauth2/token", Configuration.GetSalesForceAuthInfo());
        NiprClient lClient = NiprClientConfiguration.GetNiprClient(Configuration.GetNiprAuthToken());
        int lRetryInterval = Configuration.GetRetryInterval();

        while(true) {

            try
            {
                // Get the latest copy. This is a Deep Copy
                HashMap<String, LicenseInternal> lUnprocessedLicenses = LicenseDB.GetUnprocessedLicenses();
                HashMap<String, GregorianCalendar> lDaysToSync = LicenseDB.GetPendingNiprSyncDates();

                HashMap<String, LicenseInternal> lLicenses = new HashMap<String, LicenseInternal>();
                HashMap<String, GregorianCalendar> lSuccessDates = new HashMap<String, GregorianCalendar>();

                DoNiprSync(lClient, lDaysToSync, lUnprocessedLicenses, lLicenses, lSuccessDates);

                System.out.println("Reconciler: " + lLicenses.size() + " new licenses to be processed in Sales Force ");
                if(lLicenses.size() > 0) {

                    // Process information in sales force, save the remaining for next run
                    lUnprocessedLicenses = ProcessInfoInSalesForce(lLicenses);
                }

                System.out.println("Reconciler: Total Failed licenses in in the system " + lUnprocessedLicenses.size());

                // This transfers reference, do not use the map after this call but get a fresh copy.
                // Update in the cache, which also serves the UI
                LicenseDB.SetUnprocessedLicenses(lUnprocessedLicenses);

                LicenseDB.RemoveNiprSyncDates(lSuccessDates);

                System.out.println("Reconciler: Sleeping for 24 hrs");
                //sleep(lRetryInterval);
                TimeUnit.HOURS.sleep(24);
            }
            catch (Exception ex)
            {
                System.out.println("Reconciler mainloop threw an exception " + ex.getMessage());
            }
        }
    }

    public void DoNiprSync(
            NiprClient aInClient,
            HashMap<String, GregorianCalendar> aInDaysToSync,
            HashMap<String, LicenseInternal> aInExistingLicenses,
            HashMap<String, LicenseInternal> aInOutLicenses,
            HashMap<String, GregorianCalendar> aOutSuccessDates)
    {
        try {

            System.out.println("Reconciler: Attempting a Nipr Sync for " + aInDaysToSync.size() + " days");
            // Get all information
            AtomicBoolean lFailure = new AtomicBoolean(false);
            if(aInDaysToSync.size() > 0) {
                aInClient.GetNiprReports(aInDaysToSync, aInOutLicenses, aOutSuccessDates);
            }

            System.out.println("Reconciler: Current new licenses " + aInOutLicenses.size() + " Older unprocessed licenses " + aInExistingLicenses.size());
            // Merge the current info with unprocessed
            aInClient.MergeReports(aInExistingLicenses, aInOutLicenses);
            System.out.println("Reconciler: Combined licenses to be processed in Sales Force " + aInOutLicenses.size());

        }
        catch (Exception ex) {
            System.out.println("Reconciler: Exception in calling NiprClient " + ex.getMessage());
        }
    }

    public HashMap<String, LicenseInternal> ProcessInfoInSalesForce(HashMap<String, LicenseInternal> aInLicenses) {

        System.out.println("Reconciler: Ordering licenses");
        List<LicenseInternal> lOrderedLicences = GetOrderLicenses(aInLicenses);

        HashMap<String, LicenseInternal> lFailedRequests = new HashMap<String, LicenseInternal>();

        List<LicenseInternal> lCurrentBatch = new ArrayList<LicenseInternal>();
        int lCurrentBatchSize = 0;
        for(LicenseInternal lLicense : lOrderedLicences) {

            lCurrentBatchSize++;
            lCurrentBatch.add(lLicense);

            if(lCurrentBatchSize >= SALES_FORCE_API_BATCH) {
                // Post to Sales Force
                PostToSalesForce(lCurrentBatch, lFailedRequests, aInLicenses, true);
                lCurrentBatch = new ArrayList<LicenseInternal>();
                lCurrentBatchSize = 0;
            }
        }

        if(lCurrentBatch.size() > 0) {
            PostToSalesForce(lCurrentBatch, lFailedRequests, aInLicenses, true);
        }

        return lFailedRequests;
    }

    public void PostToSalesForce(
            List<LicenseInternal> aInLicenses,
            HashMap<String, LicenseInternal> aInOutFailedRequests,
            HashMap<String, LicenseInternal> aInAllRequests,
            boolean aInRetry) {

        System.out.println("Reconciler: Call to Post to sales force, size " + aInLicenses.size() + " retry " + aInRetry);
        boolean lFailed = false;
        try {
            List<LicenseResponse> lLicenseResponses = SfClient.SendData(aInLicenses, true);

            // Check sales force response and record failed requests.
            for(LicenseResponse lLicenseResponse : lLicenseResponses) {
                String lKey = lLicenseResponse.getKey();
                if(lLicenseResponse.isSuccess()){
                    System.out.println("Reconciler: License Key " + lKey + " successfully updated in SDFC");
                }
                else
                {
                    //System.out.println("Reconciler: License Key " + lKey + " failed to send " + lLicenseResponse.getErrorDescription());
                    if(aInAllRequests.containsKey(lKey)) {

                        LicenseInternal lLicense = aInAllRequests.get(lKey);
                        lLicense.lastErrorCode = lLicenseResponse.getErrorCode();
                        lLicense.lastErrorMessage = lLicenseResponse.getErrorMessage();
                        aInOutFailedRequests.put(lKey, lLicense);
                    }
                    else {
                        System.out.println("Reconciler: License Key " + lKey + " failed as per response but not found in request!! Ignoring ..");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Post to sales force failed due to exception " + ex);
            // TODO: Check the failure
            if (aInRetry) {

                System.out.println("Retyring call to sales force ");
                PostToSalesForce(aInLicenses, aInOutFailedRequests, aInAllRequests, false);
            } else {
                // Add all the requests to Failed ones
                for (LicenseInternal lLicense : aInLicenses) {
                    String lKey = lLicense.GetKey();
                    if (!aInOutFailedRequests.containsKey(lKey)) {
                        System.out.println("Adding License " + lKey + " to failed request dues to sales force api failure");
                        lLicense.lastErrorCode = "SDFC_API_CALL_FAILURE";
                        lLicense.lastErrorMessage = "Failed to call SDFC api, bulk failure";
                        aInOutFailedRequests.put(lKey, lLicense);
                    }
                }
            }
        }
    }

    private List<LicenseInternal>  GetOrderLicenses(HashMap<String, LicenseInternal> aInLicenses) {

        List<LicenseInternal> lResidentLicenses = new ArrayList<LicenseInternal>();
        List<LicenseInternal> lNonResidentLicenses = new ArrayList<LicenseInternal>();
        for(LicenseInternal lLicense : aInLicenses.values()) {
            if(lLicense.isResidentLicense) {
                lResidentLicenses.add(lLicense);
            }
            else {
                lNonResidentLicenses.add(lLicense);
            }
        }

        List<LicenseInternal> lOrderedLicenses = new ArrayList<LicenseInternal>(lResidentLicenses);
        lOrderedLicenses.addAll(lNonResidentLicenses);

        return lOrderedLicenses;
    }

}
