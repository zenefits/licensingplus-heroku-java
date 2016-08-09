package Core.Nipr;

import java.util.*;
/**
 * Created by vthiruvengadam on 8/7/16.
 */
public class LicenseInternal {

    public String NpnNumber;

    public String CreationDate;

    public String EffectiveDate;

    public String ExpirationDate;

    public String State;

    public String LicenseNumber;

    public Boolean IsActive;

    public Boolean IsResidentState;

    public String ClassName;

    public List<LineOfAuthorityInternal> Loas;

    public LicenseInternal() {
        Loas = new ArrayList<LineOfAuthorityInternal>();
    }

    public String GetKey() {
        return NpnNumber + ";" + LicenseNumber + ";" + State + ";" + EffectiveDate;
    }

    @Override
    public String toString() {
        return "LicenseInternal{" +
                "NpnNumber='" + NpnNumber + '\'' +
                ", CreationDate='" + CreationDate + '\'' +
                ", EffectiveDate='" + EffectiveDate + '\'' +
                ", ExpirationDate='" + ExpirationDate + '\'' +
                ", State='" + State + '\'' +
                ", LicenseNumber='" + LicenseNumber + '\'' +
                ", IsActive=" + IsActive +
                ", IsResidentState=" + IsResidentState +
                ", ClassName='" + ClassName + '\'' +
                ", Loas=" + Loas +
                '}';
    }
}
