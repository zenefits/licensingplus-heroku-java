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
public class WebUtils {

    public static <T> T getObjectFromJson(String aInData, Class<T> aInClass) {
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

    public static List<LicenseResponse> getResponseFromJson(String aInData) {
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

    public static <T> ResponseEntity<T>  postData(String aInUrl, String aInData, HttpHeaders aInHeaders, Class<T> aInType) {

        System.out.println("WebUtils: Posting data to " + aInUrl);
        String lResponseData = "";
        RestTemplate lRestTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>(aInData,aInHeaders);

        ResponseEntity<T> lResponse = lRestTemplate
                .exchange(aInUrl, HttpMethod.POST, entity, aInType);

        if (lResponse.getStatusCode() == HttpStatus.OK) {
            System.out.println("WebUtils: Post was a success");
        } else if (lResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            System.out.println("WebUtils: Post failed");
        }

        return lResponse;
    }

    public static void appendline(String aInMsg, StringBuilder aInOutData) {
        aInOutData.append(aInMsg);
        aInOutData.append(System.getProperty("line.separator"));
    }
}
