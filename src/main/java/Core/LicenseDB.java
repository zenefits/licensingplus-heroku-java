package Core;

import Core.Nipr.LicenseInternal;
import Core.Utils.CalenderUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.Unmarshaller;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by vthiruvengadam on 8/10/16.
 */
public class LicenseDB {

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();
    private static HashMap<String, LicenseInternal> UnprocessedLicenses = new HashMap<String, LicenseInternal>();
    private static HashMap<String, GregorianCalendar> PendingNiprSyncDates = new HashMap<String, GregorianCalendar>();
    private static GregorianCalendar LastSuccessfullSync = null;
    private static UUID ResyncTriggerId = UUID.randomUUID();
    private static Thread ReconcilerThread = null;

    static  {
        PendingNiprSyncDates = CalenderUtils.GetLastNDays(Configuration.GetResyncDaysCount());
    }

    public static void SetReconcilerThread(Thread aInThread) {
        ReconcilerThread = aInThread;
    }

    public static UUID GetResyncTriggerId() {

        readLock.lock();
        try {
            return UUID.fromString(ResyncTriggerId.toString());
        }
        finally {
            readLock.unlock();
        }
    }

    public static void TriggerResync() {
        System.out.println("LicenseDB: Triggering Resync ");
        writeLock.lock();
        try {
            ResyncTriggerId = UUID.randomUUID();
            System.out.println("LicenseDB: Triggered Resync ID " + ResyncTriggerId);
        }
        finally {
            writeLock.unlock();
        }

        ReconcilerThread.interrupt();
    }

    public static HashMap<String, GregorianCalendar> GetPendingNiprSyncDates() {

        HashMap<String, GregorianCalendar> lNiprSyncDates = new HashMap<String, GregorianCalendar>();

        readLock.lock();
        try {

            GregorianCalendar lToday = (GregorianCalendar) GregorianCalendar.getInstance();
            if(!CalenderUtils.IsCalenderDaySame(lToday, LastSuccessfullSync)) {
                System.out.println("Reconciler: Last successful call is not today, adding today's date");
                PendingNiprSyncDates.put(CalenderUtils.GetFormattedDate(lToday), lToday);
            }
            else {
                System.out.println("Reconciler: TodaysSuccessfulSync has been done ...");
            }

            for (GregorianCalendar lCal : PendingNiprSyncDates.values()) {
                GregorianCalendar lCopy = (GregorianCalendar)lCal.clone();
                lNiprSyncDates.put(CalenderUtils.GetFormattedDate(lCopy), lCopy);
            }
        }
        finally {
            readLock.unlock();
        }

        return lNiprSyncDates;
    }

    public static void AddNiprSyncDate(String aInDate) {
        System.out.println("LicenseDB: Adding date " + aInDate + " for Nipr Sync");
        GregorianCalendar lCal = CalenderUtils.GetCalenderTimeFromString(aInDate);
        writeLock.lock();
        try {
            PendingNiprSyncDates.put(CalenderUtils.GetFormattedDate(lCal), lCal);
        }
        finally {
            writeLock.unlock();
        }
        TriggerResync();
    }

    public static void RemoveNiprSyncDate(String aInDate) {

        System.out.println("LicenseDB: Removing Nipr Sync date " + aInDate);
        GregorianCalendar lCal = CalenderUtils.GetCalenderTimeFromString(aInDate);
        HashMap<String, GregorianCalendar> lDates = new HashMap<String, GregorianCalendar>();
        lDates.put(CalenderUtils.GetFormattedDate(lCal), lCal);

        RemoveNiprSyncDates(lDates);
    }

    public static void RemoveNiprSyncDates(HashMap<String, GregorianCalendar> aInSuccessDates) {

        GregorianCalendar lToday = (GregorianCalendar) GregorianCalendar.getInstance();
        writeLock.lock();
        try {
            for(GregorianCalendar lCal : aInSuccessDates.values()) {

                String lKey = CalenderUtils.GetFormattedDate(lCal);
                System.out.println("LicenseDB: Removing date " + lKey + " for Nipr Sync");
                PendingNiprSyncDates.remove(lKey);

                if(CalenderUtils.IsCalenderDaySame(lToday, lCal)) {
                    System.out.println("LicenseDB: Today date " + CalenderUtils.GetFormattedDate(lCal) + " is in the dates to be removed, marking last sync as today");
                    LastSuccessfullSync = lToday;
                }
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    public static HashMap<String, LicenseInternal> GetUnprocessedLicenses() {

        HashMap<String, LicenseInternal> lCopies = new HashMap<String, LicenseInternal>();

        readLock.lock();
        try {
            for (LicenseInternal lLicense : UnprocessedLicenses.values()) {
                LicenseInternal lCopy = lLicense.GetCopy();
                lCopies.put(lCopy.GetKey(), lCopy);
            }
        }
        finally {
            readLock.unlock();
        }

        return lCopies;
    }

    public static List<LicenseInternal> GetFailedLicensesByDate(String aInDate) {

        ArrayList<LicenseInternal> lRetVals = new ArrayList<LicenseInternal>();
        readLock.lock();
        try {
            for(LicenseInternal lLicense : UnprocessedLicenses.values()) {
                if(CalenderUtils.isNullOrWhiteSpace(aInDate)) {
                    // Add all of them
                    lRetVals.add(lLicense.GetCopy());
                }
                else if(Objects.equals(lLicense.niprUpdateDate, aInDate)) {
                    lRetVals.add(lLicense.GetCopy());
                }
            }
        }
        finally {
            readLock.unlock();
        }


        return lRetVals;
    }

    public static void SetUnprocessedLicenses(HashMap<String, LicenseInternal> aInLicenses) {
        writeLock.lock();
        try {
            UnprocessedLicenses = aInLicenses;
        }
        finally {
            writeLock.unlock();
        }
    }
}
