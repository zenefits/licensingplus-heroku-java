package Core.Nipr;

import java.util.*;

import Core.Utils.CalenderUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public String lastErrorCode;

    @JsonIgnore
    public String lastErrorMessage;

    public LicenseInternal() {
        linesOfAuthority = new ArrayList<LineOfAuthorityInternal>();
        isResidentLicense = false;
        isActive = false;
    }

    public String GetKey() {
        return npnNumber + ";" + licenseNumber + ";" + CalenderUtils.GetSdfcStateName(state) + ";" + CalenderUtils.GetSFDCResponseDateFormat(effectiveDate);
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
