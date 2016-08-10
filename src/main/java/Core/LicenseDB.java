package Core;

import Core.Nipr.LicenseInternal;
import Core.Utils.CalenderUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
