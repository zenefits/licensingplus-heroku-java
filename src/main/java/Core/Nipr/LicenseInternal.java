package core.nipr;

import java.util.*;

import core.utils.CalenderUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vthiruvengadam on 8/7/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicenseInternal {

    public String npnNumber;

    public String state;

    public String licenseNumber;

    public String effectiveDate;

    public String expirationDate;

    public String className;

    public Boolean isResidentLicense;

    public Boolean isActive;

    public List<LineOfAuthorityInternal> linesOfAuthority;

    public String niprUpdateDate;

    public String lastErrorCode;

    public String lastErrorMessage;

    public LicenseInternal() {
        linesOfAuthority = new ArrayList<LineOfAuthorityInternal>();
        isResidentLicense = false;
        isActive = false;
    }

    public LicenseInternal GetCopy() {
        LicenseInternal lClone = new LicenseInternal();
        lClone.npnNumber = this.npnNumber;
        lClone.state = this.state;
        lClone.licenseNumber = this.licenseNumber;
        lClone.effectiveDate = this.effectiveDate;
        lClone.expirationDate = this.expirationDate;
        lClone.className = this.className;
        lClone.isResidentLicense = this.isResidentLicense;
        lClone.isActive = this.isActive;
        lClone.niprUpdateDate = this.niprUpdateDate;
        lClone.lastErrorCode = this.lastErrorCode;
        lClone.lastErrorMessage = this.lastErrorMessage;

        lClone.linesOfAuthority = new ArrayList<LineOfAuthorityInternal>();
        for(LineOfAuthorityInternal loa : this.linesOfAuthority) {
            lClone.linesOfAuthority.add(loa.GetCopy());
        }

        return lClone;
    }

    public String GetKey() {
        return npnNumber + ";" + licenseNumber + ";" + CalenderUtils.getSdfcStateName(state) + ";" + CalenderUtils.getSFDCResponseDateFormat(effectiveDate);
    }

    @Override
    public String toString() {
        return "LicenseInternal{" +
                "npnNumber='" + npnNumber + '\'' +
                ", niprUpdateDate='" + niprUpdateDate + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", state='" + state + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", isActive=" + isActive +
                ", isResidentLicense=" + isResidentLicense +
                ", className='" + className + '\'' +
                ", linesOfAuthority=" + linesOfAuthority +
                '}';
    }
}
