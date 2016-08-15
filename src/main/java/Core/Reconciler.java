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
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by vthiruvengadam on 8/5/16.
 */
public class Reconciler extends Thread {

    static int SALES_FORCE_API_BATCH = 200;
    static long MIN_SLEEP_INTERVAL = 120000; // 2 Mins
    static long SALES_FORCE_API_RETRY_INTERVAL = 1800000; // 30 mins
    static int SALES_FORCE_API_MAX_COUNT = 5;

    SalesForceClient SfClient = null;

    public void run() {

        System.out.println("Reconciler: Started the reconciler thread");
        SfClient = new SalesForceClient("https://test.salesforce.com/services/oauth2/token", Configuration.GetSalesForceAuthInfo());

        SendGridClient.Init(Configuration.GetSendGridKey(), Configuration.GetAlertEmailRecipient(), Configuration.GetAlertEmailSender());
       // String ltest = SendGridClient.GetSendGridEmailBody("foo", "bar");
        NiprClient lClient = NiprClientConfiguration.GetNiprClient(Configuration.GetNiprAuthToken());
        AtomicLong lRetryInterval = null;
        UUID lResyncTriggerId = LicenseDB.GetResyncTriggerId();
        while(true) {

            try
            {
                lRetryInterval = new AtomicLong(Configuration.GetRetryInterval());
                lResyncTriggerId = LicenseDB.GetResyncTriggerId();
                System.out.println("Reconciler: Current triggered Resync ID " + lResyncTriggerId);
                // Get the latest copy. This is a Deep Copy
                HashMap<String, LicenseInternal> lUnprocessedLicenses = LicenseDB.GetUnprocessedLicenses();
                HashMap<String, GregorianCalendar> lDaysToSync = LicenseDB.GetPendingNiprSyncDates();

                HashMap<String, LicenseInternal> lLicenses = new HashMap<String, LicenseInternal>();
                HashMap<String, GregorianCalendar> lSuccessDates = new HashMap<String, GregorianCalendar>();

                DoNiprSync(lClient, lDaysToSync, lUnprocessedLicenses, lLicenses, lSuccessDates);

                System.out.println("Reconciler: " + lLicenses.size() + " new licenses to be processed in Sales Force ");
                if(lLicenses.size() > 0) {

                    // Process information in sales force, save the remaining for next run
                    lUnprocessedLicenses = ProcessInfoInSalesForce(lLicenses, lRetryInterval);
                }

                System.out.println("Reconciler: Total Failed licenses in in the system " + lUnprocessedLicenses.size());

                // This transfers reference, do not use the map after this call but get a fresh copy.
                // Update in the cache, which also serves the UI
                LicenseDB.SetUnprocessedLicenses(lUnprocessedLicenses);

                LicenseDB.RemoveNiprSyncDates(lSuccessDates);

                UUID lLatestTriggerId = LicenseDB.GetResyncTriggerId();
                if(lLatestTriggerId.compareTo(lResyncTriggerId) != 0) {
                    System.out.println("Reconciler: Reconciler retrying with minimum sleep as resync triggered by user");
                    Thread.sleep(MIN_SLEEP_INTERVAL);
                    continue;
                }

                System.out.println("Reconciler: Sleeping for " + lRetryInterval + "ms");
                try {
                    Thread.sleep(lRetryInterval.get());
                }
                catch (InterruptedException lIntrEx) {
                    System.out.println("Reconciler: interrupted");
                }
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
        StringBuilder lErrors = new StringBuilder();
        try {

            System.out.println("Reconciler: Attempting a Nipr Sync for " + aInDaysToSync.size() + " days");
            // Get all information
            AtomicBoolean lFailure = new AtomicBoolean(false);
            if(aInDaysToSync.size() > 0) {
                aInClient.GetNiprReports(aInDaysToSync, aInOutLicenses, aOutSuccessDates, lErrors);
            }

            System.out.println("Reconciler: Current new licenses " + aInOutLicenses.size() + " Older unprocessed licenses " + aInExistingLicenses.size());
            // Merge the current info with unprocessed
            aInClient.MergeReports(aInExistingLicenses, aInOutLicenses);
            System.out.println("Reconciler: Combined licenses to be processed in Sales Force " + aInOutLicenses.size());

        }
        catch (Exception ex) {
            String lMsg = "Reconciler: Exception in calling NiprClient " + ex.getMessage();
            WebUtils.Appendline(lMsg, lErrors);
            System.out.println(lMsg);
        }

        if(lErrors.length() > 0) {
            // Send an email with the alert
            SendGridClient.SendEmail("Nipr Sync Errors", lErrors.toString());
        }
    }

    public HashMap<String, LicenseInternal> ProcessInfoInSalesForce(HashMap<String, LicenseInternal> aInLicenses, AtomicLong aInOutRetryInterval) {

        StringBuilder lErrors = new StringBuilder();

        // Initialize retry interval
        aInOutRetryInterval.set(Configuration.GetRetryInterval());

        System.out.println("Reconciler: Ordering licenses");
        List<LicenseInternal> lOrderedLicences = GetOrderLicenses(aInLicenses);

        HashMap<String, LicenseInternal> lFailedRequests = new HashMap<String, LicenseInternal>();

        List<LicenseInternal> lCurrentBatch = new ArrayList<LicenseInternal>();
        int lCurrentBatchSize = 0;
        int lCurrentApiCalls = 0;
        for(LicenseInternal lLicense : lOrderedLicences) {

            if(lCurrentApiCalls >= SALES_FORCE_API_MAX_COUNT) {

                System.out.println("Reconciler: Already called SFDC 5 times together, saving the licence " + lLicense.GetKey() + " for next retry");
                // Don't make more than 5 calls in 30 mins
                lFailedRequests.put(lLicense.GetKey(), lLicense);
                aInOutRetryInterval.set(SALES_FORCE_API_RETRY_INTERVAL);
                continue;
            }

            lCurrentBatchSize++;
            lCurrentBatch.add(lLicense);
            //System.out.println("Reconciler: Adding licence " + lLicense.GetKey() + " for posting to SFDC");

            if(lCurrentBatchSize >= SALES_FORCE_API_BATCH) {
                // Post to Sales Force
                PostToSalesForce(lCurrentBatch, lFailedRequests, aInLicenses, true, lErrors);
                lCurrentApiCalls++;
                lCurrentBatch = new ArrayList<LicenseInternal>();
                lCurrentBatchSize = 0;
            }
        }

        if((lCurrentApiCalls < SALES_FORCE_API_MAX_COUNT)
            && (lCurrentBatch.size() > 0)) {

            PostToSalesForce(lCurrentBatch, lFailedRequests, aInLicenses, true, lErrors);
        }

        if(lErrors.length() > 0) {
            // Send an email with the alert
            SendGridClient.SendEmail("Nipr Sync Errors", lErrors.toString());
        }

        return lFailedRequests;
    }

    public void PostToSalesForce(
            List<LicenseInternal> aInLicenses,
            HashMap<String, LicenseInternal> aInOutFailedRequests,
            HashMap<String, LicenseInternal> aInAllRequests,
            boolean aInRetry,
            StringBuilder aInOutErrors) {

        String lMsg = "";
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
                    lMsg = "Reconciler: License Key " + lKey + " failed to send " + lLicenseResponse.getErrorDescription();
                    WebUtils.Appendline(lMsg, aInOutErrors);
                    //System.out.println(lMsg);
                    if(aInAllRequests.containsKey(lKey)) {

                        LicenseInternal lLicense = aInAllRequests.get(lKey);
                        lLicense.lastErrorCode = lLicenseResponse.getErrorCode();
                        lLicense.lastErrorMessage = lLicenseResponse.getErrorMessage();
                        aInOutFailedRequests.put(lKey, lLicense);
                    }
                    else {
                        lMsg = "Reconciler: License Key " + lKey + " failed as per response but not found in request!! Ignoring ..";
                        WebUtils.Appendline(lMsg, aInOutErrors);
                        System.out.println(lMsg);
                    }
                }
            }
        } catch (Exception ex) {
            lMsg = "Post to sales force failed due to exception " + ex;
            WebUtils.Appendline(lMsg, aInOutErrors);
            System.out.println(lMsg);
            // TODO: Check the failure
            if (aInRetry) {

                System.out.println("Retyring call to sales force ");
                PostToSalesForce(aInLicenses, aInOutFailedRequests, aInAllRequests, false, aInOutErrors);
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
