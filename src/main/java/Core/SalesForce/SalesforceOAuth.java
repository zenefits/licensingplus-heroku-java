package Core.SalesForce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class SalesforceOAuth {
	
	private String CLIENT_KEY = "";
	private String CLIENT_SECRET = "";
	private String USERNAME = "";
	private String PASSWORD = "";
	
	private String ENDPOINT = String.format("https://%s.salesforce.com/services/oauth2/token", true ? "logn" : "test");
	
	public OAuthResponse connect() {
		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("grant_type", "password");
		queryParameters.put("client_id", CLIENT_KEY);
		queryParameters.put("client_secret", CLIENT_SECRET);
		queryParameters.put("username", USERNAME);
		queryParameters.put("password", PASSWORD);
		
		OAuthResponse oauth = restTemplate.getForObject(ENDPOINT, OAuthResponse.class, queryParameters);
		
		return oauth;
	}
}
