package Core.SalesForce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthResponse {


    private String access_token;
    private String instance_url;
    private String id;
    private String token_type;
    private String issued_at;
    private String signature;

    public OAuthResponse() {
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setInstance_url(String instance_url) {
        this.instance_url = instance_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setIssued_at(String issued_at) {
        this.issued_at = issued_at;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccess_token() {

        return access_token;
    }

    public String getInstance_url() {
        return instance_url;
    }

    public String getId() {
        return id;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getIssued_at() {
        return issued_at;
    }

    public String getSignature() {
        return signature;
    }
}
