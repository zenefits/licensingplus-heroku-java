package Core;

import Core.Nipr.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by vthiruvengadam on 8/5/16.
 */
public class Reconciler extends Thread {

    static HashMap<String, LicenseInternal> UnprocessedLicenses = new HashMap<String, LicenseInternal>();
    static int MAX_DAYS_RECONCILE = 5;
    static int SALES_FORCE_API_BATCH = 30;

    public void run(){

        System.out.println("Reconciler: Started the reconciler thread");
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
                    if(!IsCalenderDaySame(lLastSuccessCall, lCal)) {

                        if (lClient.IsWeekEnd(lCal)) {

                            System.out.println("Reconciler: Skipping NIPR Report for " + lClient.GetFormattedDate(lCal));
                            continue;
                        }

                        System.out.println("Reconciler: NIPR Report daily sync for " + lClient.GetFormattedDate(lCal));

                        AtomicBoolean lFailure = new AtomicBoolean(false);
                        lLicenses = lClient.GetSpecificReport(lCal, lFailure);
                        if(!lFailure.get()) {
                            lLastSuccessCall = lCal;
                            System.out.println("Reconciler: NIPR Report succeeded for " + lClient.GetFormattedDate(lCal));
                        }
                        else {
                            System.out.println("Reconciler: NIPR Report failed for " + lClient.GetFormattedDate(lCal));
                        }
                    }
                    else {
                        System.out.println("Reconciler: Nipr Sync for today " + lClient.GetFormattedDate(lCal) + " is done");
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

    public static HashMap<String, LicenseInternal> ProcessInfoInSalesForce(HashMap<String, LicenseInternal> aInLicenses) {

        HashMap<String, LicenseInternal> lFailedRequests = new HashMap<String, LicenseInternal>();

        List<LicenseInternal> lCurrentBatch = new ArrayList<LicenseInternal>();
        int lCurrentBatchSize = 0;
        for(LicenseInternal lLicense : aInLicenses.values()) {

            lCurrentBatchSize++;
            lCurrentBatch.add(lLicense);

            if(lCurrentBatchSize >= SALES_FORCE_API_BATCH) {
                // Post to Sales Force
                PostToSalesForce(lCurrentBatch, lFailedRequests, true);
                lCurrentBatch = new ArrayList<LicenseInternal>();
                lCurrentBatchSize = 0;
            }
        }

        if(lCurrentBatch.size() > 0) {
            PostToSalesForce(lCurrentBatch, lFailedRequests, true);
        }

        // Log the failed requests
        for(LicenseInternal lLicense : lFailedRequests.values()) {
            System.out.println("Failed to Update license in Sales Force " + lLicense.toString());
        }

        return lFailedRequests;
    }

    public static void PostToSalesForce(List<LicenseInternal> aInLicenses, HashMap<String, LicenseInternal> aInOutFailedRequests, boolean aInRetry) {

        System.out.println("Call to Post to sales force, size " + aInLicenses.size() + " retry " + aInRetry);
        boolean lFailed = false;
        try {

            // Check sales force response and record failed requests.
        } catch (Exception ex) {
            System.out.println("Post to sales force failed due to exception " + ex);
            // TODO: Check the failure
            if (aInRetry) {

                System.out.println("Retyring call to sales force ");
                PostToSalesForce(aInLicenses, aInOutFailedRequests, false);
            } else {
                // Add all the requests to Failed ones
                for (LicenseInternal lLicense : aInLicenses) {
                    if (!aInOutFailedRequests.containsKey(lLicense.GetKey())) {
                        System.out.println("Adding License " + lLicense.GetKey() + " to failed request dues to sales force api failure");
                        aInOutFailedRequests.put(lLicense.GetKey(), lLicense);
                    }
                }
            }

        }
    }

    private boolean IsCalenderDaySame(GregorianCalendar aInCal1, GregorianCalendar aInCal2) {
        if((aInCal1 == null)
            && (aInCal2 != null)) {
            return false;
        }

        if((aInCal2 == null)
                && (aInCal1 != null)) {
            return false;
        }

        if((aInCal2 == null)
                && (aInCal1 == null)) {
            return true;
        }

        if(aInCal1.get(Calendar.DAY_OF_MONTH) != aInCal2.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }

        if(aInCal1.get(Calendar.MONTH) != aInCal2.get(Calendar.MONTH)) {
            return false;
        }

        if(aInCal1.get(Calendar.YEAR) != aInCal2.get(Calendar.YEAR)) {
            return false;
        }

        return true;
    }

}
