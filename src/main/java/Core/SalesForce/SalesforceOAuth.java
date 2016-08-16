package Core.SalesForce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class SalesforceOAuth {
	
	private String CLIENT_KEY = "";
	private String CLIENT_SECRET = "";
	private String USERNAME = "";
	private String PASSWORD = "";
	
	//Can you add a config called SALESFORCE_SANDBOX and a retry if first login request is not successful?
	private String ENDPOINT = String.format("https://%s.salesforce.com/services/oauth2/token", true ? "logn" : "test");
	
	public OAuthResponse connect() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("grant_type", "password");
		requestBody.put("client_id", CLIENT_KEY);
		requestBody.put("client_secret", CLIENT_SECRET);
		requestBody.put("username", USERNAME);
		requestBody.put("password", PASSWORD);
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(requestBody, requestHeaders);
		
		OAuthResponse oauth = restTemplate.postForObject(ENDPOINT, httpEntity, OAuthResponse.class);
		
		return oauth;
	}
}
