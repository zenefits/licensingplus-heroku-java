package Core.SalesForce;
import Core.Nipr.LicenseInternal;
import Core.Utils.*;
import Core.Utils.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.web.util.*;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
public class SalesForceClient {

    private String authUrl;
    private String authToken;
    private String syncUrl;
    private String syncUrlSuffix = "/services/apexrest/nipr/license";
    private String formEncodedAuthInfo;

    public SalesForceClient(String aInAuthUrl, String aInConsumerKey, String aInConsumerSecret, String aInUsername, String aInPassword) {
        authUrl = aInAuthUrl;
        authToken = "";
        formEncodedAuthInfo = getSalesForceAuthInfo(aInConsumerKey, aInConsumerSecret, aInUsername, aInPassword);
    }

    private boolean login() {

        boolean lSuccess = false;
        OAuthResponse lResponse = null;
        String lFormEncoded = formEncodedAuthInfo;
        HttpHeaders lHeaders = new HttpHeaders();
        lHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // send request and parse result
        ResponseEntity<String> loginResponse = WebUtils.postData(
                authUrl, lFormEncoded, lHeaders, String.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            System.out.println("SalesForceClient: Auth was a success");
            lResponse = WebUtils.getObjectFromJson(loginResponse.getBody(), OAuthResponse.class);
            if(lResponse != null) {
                authToken = lResponse.getAccess_token();
                syncUrl = lResponse.getInstance_url() + syncUrlSuffix;
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

    private String getSalesForceAuthInfo(String aInConsumerKey, String aInConsumerSecret, String aInUsername, String aInPassword) {
        String lInfo = "";
        try {
            // grant_type=password&client_id=<SALESFORCE_CONSUMER_KEY>&client_secret=<SALESFORCE_CONSUMER_SECRET>&username=<SALESFORCE_USERNAME>&password=<SALESFORCE_PASSWORD>
            lInfo = "grant_type=password&client_id=" + aInConsumerKey + "&client_secret=" +
                    aInConsumerSecret + "&username=" + aInUsername + "&password=" + URLEncoder.encode(aInPassword, "UTF-8");
            return lInfo;
        }
        catch(Exception ex) {
            System.out.println("Failed to url encode sales force auth info");
        }
        return lInfo;
    }

    public List<LicenseResponse> sendData(List<LicenseInternal> aInLicenses, boolean aInRetry) throws Exception {
        List<LicenseResponse> lLicenseResponses = new ArrayList<LicenseResponse>();

        if(aInLicenses.size() == 0) {
            return lLicenseResponses;
        }
        if(CalenderUtils.isNullOrWhiteSpace(authToken)) {
            boolean lSuccess = login();
        }
        String lJsonData = "";
        String lToken = "Bearer " + authToken;
        try {
            ObjectMapper lMapper = new ObjectMapper();
            lJsonData = lMapper.writeValueAsString(aInLicenses);

            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.setContentType(MediaType.APPLICATION_JSON);

            lHeaders.set("Authorization", lToken);
            // send request and parse result
            ResponseEntity<String> lResponse = WebUtils.postData(
                    syncUrl, lJsonData, lHeaders, String.class);

            if (lResponse.getStatusCode() == HttpStatus.OK) {
                String lResponseData = lResponse.getBody();
                lLicenseResponses = WebUtils.getResponseFromJson(lResponseData);
                System.out.println("SalesForceClient: Response count " + lLicenseResponses.size());

            } else if (lResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                System.out.println("SalesForceClient: SendData failed UnAuthorized");
                if(aInRetry) {
                    System.out.println("SalesForceClient: Retry Send Data");
                    authToken = "";
                    sendData(aInLicenses, false);
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
