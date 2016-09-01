package Core.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * Created by vthiruvengadam on 8/9/16.
 */
public class WebUtils {

    public static <T> ResponseEntity<T>  postData(String aInUrl, String aInData, HttpHeaders aInHeaders, Class<T> aInType) {

        System.out.println("WebUtils: Posting data to " + aInUrl);
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
    
    public static String convertMapToQueryString(Map<String, String> queryParameters) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		  for(HashMap.Entry<String, String> e : queryParameters.entrySet()){
		      if(sb.length() > 0){
		          sb.append('&');
		      }
		      sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(e.getValue(), "UTF-8"));
		  }
		 return sb.toString();
    }
}
