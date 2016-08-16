package Core.Utils;

import Core.SalesForce.LicenseResponse;
import Core.SalesForce.OAuthResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
// Do we really need this class? Since spring already provide methods for restTemplate, can you just move the calls into 
// SalesforceRestClient and SalesforceOAuth?
public class WebUtils {
    
	//PLEASE do NOT catch exception here, try move to SalesforceRestClient and SalesforceOAuth
    public static <T> T GetObjectFromJson(String aInData, Class<T> aInClass) {
        T lResponse = null;
        try {
            ObjectMapper lMapper = new ObjectMapper();
            lResponse = lMapper.readValue(aInData, aInClass);
        }
        catch(Exception ex) {
            System.out.println("SalesForceClient: Failed to cast Oauth response");
        }

        return lResponse;
    }
    
    
    //PLEASE do NOT catch exception here, try move to SalesforceRestClient and SalesforceOAuth
    public static List<LicenseResponse> GetResponseFromJson(String aInData) {
        List<LicenseResponse> lResponse = null;
        try {
            ObjectMapper lMapper = new ObjectMapper();
            lResponse = lMapper.readValue(aInData, new TypeReference<List<LicenseResponse>>(){});
        }
        catch(Exception ex) {
            System.out.println("SalesForceClient: Failed to cast Oauth response");
        }

        return lResponse;
    }
    
    //PLEASE do NOT catch exception here, try move to SalesforceRestClient and SalesforceOAuth
    public static <T> ResponseEntity<T>  PostData(String aInUrl, String aInData, HttpHeaders aInHeaders, Class<T> aInType) {

        System.out.println("WebUtils: Posting data to " + aInUrl);
        String lResponseData = "";
        RestTemplate lRestTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>(aInData,aInHeaders);

        ResponseEntity<T> lResponse = lRestTemplate
                .exchange(aInUrl, HttpMethod.POST, entity, aInType);
        
        // you probably want to throw exception here instead of print things out
        if (lResponse.getStatusCode() == HttpStatus.OK) {
            System.out.println("WebUtils: Post was a success");
        } else if (lResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            System.out.println("WebUtils: Post failed");
        }

        return lResponse;
    }

    public static void Appendline(String aInMsg, StringBuilder aInOutData) {
        aInOutData.append(aInMsg);
        aInOutData.append(System.getProperty("line.separator"));
    }
}
