package Core.SalesForce;
import Core.Nipr.LicenseInternal;
import Core.Utils.*;
import Core.Utils.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.web.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
public class SalesForceClient {

    private String AuthUrl;
    private String AuthToken;
    private String SyncUrl;
    private String SyncUrlSuffix = "services/apexrest/nipr/license";
    private String FormEncodedAuthInfo;

    public SalesForceClient(String aInAuthUrl, String aInAuthInfo) {
        AuthUrl = aInAuthUrl;
        AuthToken = "";
        FormEncodedAuthInfo = aInAuthInfo;
    }

    private boolean Login() {

        boolean lSuccess = false;
        OAuthResponse lResponse = null;
        String lFormEncoded = FormEncodedAuthInfo;
        HttpHeaders lHeaders = new HttpHeaders();
        lHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // send request and parse result
        ResponseEntity<String> loginResponse = WebUtils.PostData(
                AuthUrl, lFormEncoded, lHeaders, String.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            System.out.println("SalesForceClient: Auth was a success");
            lResponse = WebUtils.GetObjectFromJson(loginResponse.getBody(), OAuthResponse.class);
            if(lResponse != null) {
                AuthToken = lResponse.getAccess_token();
                SyncUrl = lResponse.getInstance_url() + "/services/apexrest/nipr/license";
                lSuccess = true;
            }
        } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            System.out.println("SalesForceClient: Auth failed");
        }
        else {
            System.out.println("SalesForceClient: Login failed " + loginResponse.getStatusCode());
        }

        return lSuccess;
    }

    public List<LicenseResponse> SendData(List<LicenseInternal> aInLicenses, boolean aInRetry) throws Exception {
        List<LicenseResponse> lLicenseResponses = new ArrayList<LicenseResponse>();

        if(aInLicenses.size() == 0) {
            return lLicenseResponses;
        }
        if(CalenderUtils.isNullOrWhiteSpace(AuthToken)) {
            boolean lSuccess = Login();
        }

        try {
            ObjectMapper lMapper = new ObjectMapper();
            String lJsonData = lMapper.writeValueAsString(aInLicenses);

            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.setContentType(MediaType.APPLICATION_JSON);
            String lToken = "Bearer " + AuthToken;
            lHeaders.set("Authorization", lToken);
            // send request and parse result
            ResponseEntity<String> lResponse = WebUtils.PostData(
                    SyncUrl, lJsonData, lHeaders, String.class);

            if (lResponse.getStatusCode() == HttpStatus.OK) {
                String lResponseData = lResponse.getBody();
                lLicenseResponses = WebUtils.GetResponseFromJson(lResponseData);
                System.out.println("SalesForceClient: Response count " + lLicenseResponses.size());

            } else if (lResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                System.out.println("SalesForceClient: SendData failed UnAuthorized");
                if(aInRetry) {
                    System.out.println("SalesForceClient: Retry Send Data");
                    AuthToken = "";
                    SendData(aInLicenses, false);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("SalesForceClient: Failed to send data " + ex.getMessage());
            throw ex;
        }

        return lLicenseResponses;
    }
}
