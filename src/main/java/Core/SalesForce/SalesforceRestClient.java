package Core.SalesForce;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SalesforceRestClient {

	private String instanceUrl;
	private String sessionId;
	
	public SalesforceRestClient() {
		connect();
	}
	
	private void connect() {
		SalesforceOAuth oauth = new SalesforceOAuth();
		OAuthResponse oauthResponse = oauth.connect();
		this.sessionId = oauthResponse.getAccess_token();
		this.instanceUrl = oauthResponse.getInstance_url();		
	}
	
	public <T> ResponseEntity<T> apexRest(String endpoint, HttpMethod method, String requestBody, Class<T> responseType) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + this.sessionId);
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, headers);
		endpoint = this.instanceUrl + "/services/apexrest/" + endpoint;
	    ResponseEntity<T> response = restTemplate.exchange(endpoint, method, requestEntity, responseType);
	    
	    return response;
	}
}
