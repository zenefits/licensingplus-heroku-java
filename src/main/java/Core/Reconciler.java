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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by vthiruvengadam on 8/5/16.
 */
public class Reconciler extends Thread {

    static HashMap<String, LicenseInternal> UnprocessedLicenses = new HashMap<String, LicenseInternal>();
    static int MAX_DAYS_RECONCILE = 2;
    static int SALES_FORCE_API_BATCH = 30;
    SalesForceClient SfClient = null;
    public void run(){

        System.out.println("Reconciler: Started the reconciler thread");
        SfClient = new SalesForceClient("https://test.salesforce.com/services/oauth2/token");
        SfClient.Login();

        boolean lFullNiprSync = true;
        NiprClient lClient = NiprClientConfiguration.GetNiprClient();
        GregorianCalendar lLastSuccessCall = null;
        while(true) {

            try
            {
                HashMap<String, LicenseInternal> lLicenses = new HashMap<String, LicenseInternal>();
                if(lFullNiprSync) {
                    System.out.println("Reconciler: Attempting a full Nipr Sync");
                    // Get all information
                    AtomicBoolean lFailure = new AtomicBoolean(false);
                    lLicenses = lClient.GetAllNiprReports(MAX_DAYS_RECONCILE, lFailure);
                    if(lFailure.get()) {
                        System.out.println("Reconciler: There were failures in getting a full sync on GetAllNiprReport");
                    }
                    else {
                        lFullNiprSync = false;
                        lLastSuccessCall = (GregorianCalendar) GregorianCalendar.getInstance();
                        System.out.println("Reconciler: Success getting a full sync on GetAllNiprReport");
                    }
                }
                else {
                    GregorianCalendar lCal = (GregorianCalendar) GregorianCalendar.getInstance();

                    // No need to update the licenses as we already sync'd today
                    if(!CalenderUtils.IsCalenderDaySame(lLastSuccessCall, lCal)) {

                        if (CalenderUtils.IsWeekEnd(lCal)) {

                            System.out.println("Reconciler: Skipping NIPR Report for " + CalenderUtils.GetFormattedDate(lCal));
                            continue;
                        }

                        System.out.println("Reconciler: NIPR Report daily sync for " + CalenderUtils.GetFormattedDate(lCal));

                        AtomicBoolean lFailure = new AtomicBoolean(false);
                        lLicenses = lClient.GetSpecificReport(lCal, lFailure);
                        if(!lFailure.get()) {
                            lLastSuccessCall = lCal;
                            System.out.println("Reconciler: NIPR Report succeeded for " + CalenderUtils.GetFormattedDate(lCal));
                        }
                        else {
                            System.out.println("Reconciler: NIPR Report failed for " + CalenderUtils.GetFormattedDate(lCal));
                        }
                    }
                    else {
                        System.out.println("Reconciler: Nipr Sync for today " + CalenderUtils.GetFormattedDate(lCal) + " is done");
                    }

                }

                System.out.println("Reconciler: Current new licenses " + lLicenses.size() + " Older unprocessed licenses " + UnprocessedLicenses.size());
                // Merge the current info with unprocessed
                lClient.MergeReports(UnprocessedLicenses, lLicenses);
                System.out.println("Reconciler: Combined licenses to be processed in Sales Force " + lLicenses.size());

                if(lLicenses.size() > 0) {

                    // Process information in sales force, save the remaining for next run
                    UnprocessedLicenses = ProcessInfoInSalesForce(lLicenses);
                }
                else {
                    System.out.println("Reconciler: No new licenses to be processed in Sales Force ");

                }
                System.out.println("Reconciler: Sleeping");
                sleep(10000);
            }
            catch (Exception ex)
            {
                // Force Sync if something goes wrong
                lFullNiprSync = false;
                System.out.println("Reconciler mainloop threw an exception " + ex.getMessage());
            }
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

        System.out.println("Total  Failed license in Sales Force " + lFailedRequests.size());
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
                    System.out.println("Reconciler: License Key " + lKey + " failed to send " + lLicenseResponse.getErrorDescription());
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
