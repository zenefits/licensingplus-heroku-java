package Core.SalesForce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import Core.Nipr.LicenseInternal;
import Core.Nipr.LineOfAuthorityInternal;

public class SalesforceRestClient {
	
	private static final String API_VERSION = "v37.0";
    private static final String SALESFORCE_ENDPOINT = "/services/data/%s/%s";
	
	private String instanceUrl;
	private String sessionId;
	private boolean connected = false;
	
	public SalesforceRestClient(String clientSecret, String clientKey, String username, String password, boolean isSandbox) {
		connect(clientSecret, clientKey, username, password, isSandbox);
	}
	
	private void connect(String clientSecret, String clientKey, String username, String password, boolean isSandbox) {
		SalesforceOAuth oauth = new SalesforceOAuth(clientSecret, clientKey, username, password, isSandbox);
		OAuthResponse oauthResponse = oauth.connect(true);
		if (oauthResponse!=null) {
			this.connected = true;
			this.sessionId = oauthResponse.getAccess_token();
			this.instanceUrl = oauthResponse.getInstance_url();	
		}	
	}
	
	private HttpEntity<String> getRequestEntity(String requestBody, MediaType contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		headers.set("Authorization", "Bearer " + this.sessionId);
		HttpEntity<String> requestEntity = null;
		if (requestBody!=null) {
			requestEntity = new HttpEntity<String>(requestBody, headers);
		} else {
			requestEntity = new HttpEntity<String>(headers);
		}
		
		return requestEntity;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public <T> T apexRest(String endpoint, HttpMethod method, String requestBody, Class<T> responseType) throws Exception {
		T response = null;
		if (connected) {
			RestTemplate restTemplate = new RestTemplate();
			endpoint = this.instanceUrl + "/services/apexrest/" + endpoint;
		    ResponseEntity<T> responseEntity = restTemplate.exchange(endpoint, method, getRequestEntity(requestBody, MediaType.APPLICATION_JSON), responseType);
		    response = responseEntity.getBody();
		} else {
			System.out.println("[SalesforceRestClient] - apexRest - no connection to Salesforce");
			throw new Exception("Failed to connect to salesforce");
		}
		
	    return response;
	}
	
	public <T> T query(String queryStr, Class<T> responseType) throws Exception {
		T response = null;
		if (connected) {
			RestTemplate restTemplate = new RestTemplate();		
			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("q", queryStr);		
			String endpoint = this.instanceUrl + String.format(SALESFORCE_ENDPOINT+"/?q={q}", API_VERSION, "query");	
			ResponseEntity<T> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, getRequestEntity(null, MediaType.APPLICATION_FORM_URLENCODED), responseType, queryParameters);	
			response = responseEntity.getBody();
		} else {
			System.out.println("[SalesforceRestClient] - query - no connection to Salesforce");		
			throw new Exception("Failed to connect to salesforce");
		}
		return response;
	}
	
	/*public static void main(String[] args) throws Exception {
		String CLIENT_SECRET = "";
		String CLIENT_KEY = "";
		String USERNAME = "";
		String PASSWORD = "";
		boolean IS_SANDBOX = true;	

		SalesforceService sfdcService = new SalesforceService(CLIENT_SECRET, CLIENT_KEY, USERNAME, PASSWORD, IS_SANDBOX);
		
		LicenseInternal li = new LicenseInternal();
		li.className = "NON RESIDENT PRODUCER";
		li.effectiveDate = "2016-8-24";
		li.expirationDate = "2019-4-30";
		li.isResidentLicense = false;
		li.isActive = true;
		li.licenseNumber = "002521388";
		li.state = "HI";
		li.npnNumber = "17359172";
		li.niprUpdateDate = "2016-08-31";
		LineOfAuthorityInternal loa1 = new LineOfAuthorityInternal();
		loa1.isActive = true;
		loa1.name = "Life";
		
		LineOfAuthorityInternal loa = new LineOfAuthorityInternal();
		loa.isActive = true;
		loa.name = "DISABILITY (HEALTH)";
		
		li.linesOfAuthority.add(loa);
		li.linesOfAuthority.add(loa1);
		
		List<LicenseInternal> licenses = Arrays.asList(li);
				
		System.out.println(sfdcService.getLastNDaysSyncedLicenseResponse("2016-08-31"));
		
		//System.out.println(sfdcService.syncNIPRLicenses(licenses));
	}*/
}
