package Core.Nipr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vthiruvengadam on 8/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NiprSyncStatus {

    private String syncDate;

    private boolean status;

    private String errorMsg;

    private List<LicenseInternal>  failedLicenses;

    public NiprSyncStatus() {
        failedLicenses = new ArrayList<LicenseInternal>();
    }

    public String getSyncDate() {
        return syncDate;
    }

    public boolean isStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<LicenseInternal> getFailedLicenses() {
        return failedLicenses;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void AddLicense(LicenseInternal aInLicense) {
        failedLicenses.add(aInLicense);
    }
}
