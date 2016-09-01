package core.sfdc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import core.sfdc.responses.OAuthResponse;
import core.utils.WebUtils;

public class SalesforceOAuth {		
	private static final String ENDPOINT = "https://%s.salesforce.com/services/oauth2/token";
	
	public SalesforceOAuth() {}
	
	public OAuthResponse connect(String clientSecret, String clientKey, String username, String password, boolean isSandbox, boolean retry) {
		RestTemplate restTemplate = new RestTemplate();
		String oAuthEndpoint = String.format(ENDPOINT, isSandbox ? "test" : "login");
		
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("grant_type", "password");
		queryParameters.put("client_id", clientKey);
		queryParameters.put("client_secret", clientSecret);
		queryParameters.put("username", username);
		queryParameters.put("password", password);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		
		OAuthResponse response = null;	
		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>(WebUtils.convertMapToQueryString(queryParameters), headers);
			ResponseEntity<OAuthResponse> oauth = restTemplate.postForEntity(oAuthEndpoint, requestEntity, OAuthResponse.class);
			response = oauth.getBody();
		} catch (HttpClientErrorException httpException) {
            if(httpException.getStatusCode() != HttpStatus.OK) {
                System.out.println("[SalesforceService] - connect - Failed to connect to Saleforce - \n\n " + httpException.getResponseBodyAsString());
                if (retry) {
                	connect(clientSecret, clientKey, username, password, isSandbox, false);
                } else {
                	System.out.println("[SalesforceService] - connect - Failed to connect to Saleforce, please check your heroku SALESFORCE_USERNAME, SALESFORCE_PASSWORD, SALESFORCE_COSUMER_SECRET, SALESFORCE_COSUMER_KEY");
                }
            }
        } catch (Exception e) {
        	System.out.println("[SalesforceService] - connect - exception: " + e);
        }

		return response;
	}
}
