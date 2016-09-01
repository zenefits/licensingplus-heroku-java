package Core.SalesForce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import Core.Utils.WebUtils;

public class SalesforceOAuth {
	
	private final String CLIENT_SECRET;
	private final String CLIENT_KEY;
	private final String USERNAME;
	private final String PASSWORD;
	private final boolean IS_SANDBOX;	
	private String ENDPOINT = "https://%s.salesforce.com/services/oauth2/token";
	
	public SalesforceOAuth(String clientSecret, String clientKey, String username, String password, boolean isSandbox) {
		this.CLIENT_KEY = clientKey;
		this.CLIENT_SECRET = clientSecret;
		this.USERNAME = username;
		this.PASSWORD = password;
		this.IS_SANDBOX = isSandbox;
		this.ENDPOINT = String.format(ENDPOINT, this.IS_SANDBOX ? "test" : "login");
	}
	
	public OAuthResponse connect(boolean retry) {
		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("grant_type", "password");
		queryParameters.put("client_id", CLIENT_KEY);
		queryParameters.put("client_secret", CLIENT_SECRET);
		queryParameters.put("username", USERNAME);
		queryParameters.put("password", PASSWORD);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		
		OAuthResponse response = null;	
		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>(WebUtils.convertMapToQueryString(queryParameters), headers);
			ResponseEntity<OAuthResponse> oauth = restTemplate.postForEntity(ENDPOINT, requestEntity, OAuthResponse.class);
			response = oauth.getBody();
		} catch (HttpClientErrorException httpException) {
            if(httpException.getStatusCode() != HttpStatus.OK) {
                System.out.println("[SalesforceService] - connect - Failed to connect to Saleforce - \n\n " + httpException.getResponseBodyAsString());
                if (retry) {
                	connect(false);
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
