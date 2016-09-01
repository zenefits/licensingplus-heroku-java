package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicenseResponse {

    private String key;
    private boolean isSuccess;
    private String errorMessage;
    private String errorCode;

    public LicenseResponse() {
        isSuccess = false;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return " ErrorCode: " + getErrorCode() + " Error Message: " + getErrorMessage();
    }

}
