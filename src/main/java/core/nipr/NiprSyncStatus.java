package core.nipr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import core.sfdc.responses.NIPRSyncedLicenseResponse;

import java.util.AbstractList;
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

    private List<NIPRSyncedLicenseResponse> syncedLicenses;

    private Integer syncedLicensesCount;

    public NiprSyncStatus() {
        syncedLicensesCount = 0;
        failedLicenses = new ArrayList<LicenseInternal>();
        syncedLicenses = new ArrayList<NIPRSyncedLicenseResponse>();
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

    public List<NIPRSyncedLicenseResponse> getSyncedLicenses() {
        return syncedLicenses;
    }

    public Integer getSyncedLicensesCount() {
        return syncedLicensesCount;
    }

    public void setSyncedLicenses(List<NIPRSyncedLicenseResponse> syncedLicenses) {
        this.syncedLicenses = syncedLicenses;
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

    public void setSyncedLicensesCount(Integer syncedLicensesCount) {
        this.syncedLicensesCount = syncedLicensesCount;
    }

    public void AddLicense(LicenseInternal aInLicense) {
        failedLicenses.add(aInLicense);
    }
}
