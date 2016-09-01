package Core.SalesForce;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import Core.Nipr.LicenseInternal;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
public class SalesforceService {
	
    private static final String NIPR_LICENSE_SYNC = "LicensingPlus/nipr/sync/license";
    private SalesforceRestClient restClient;
    
    public SalesforceService(String clientSecret, String clientKey, String username, String password, boolean isSandbox) {
    	this.restClient = new SalesforceRestClient(clientSecret, clientKey, username, password, isSandbox);
    }
    
    public LastNDaysSyncedLicenseResponse getLastNDaysSyncedLicenseResponse(String date) throws Exception {
    	LastNDaysSyncedLicenseResponse response = null;
    	
    	try { 
    		String queryStr = "SELECT licensingplus__nipr_update_date__c, licensingplus__npn_number_formula__c, licensingplus__state__c, licensingplus__effective_date__c, licensingplus__number__c "
    				+ "FROM licensingplus__License__c "
    				+ "WHERE licensingplus__nipr_update_date__c=" + date;
    		response = this.restClient.query(queryStr, LastNDaysSyncedLicenseResponse.class);
    	
    	} catch (Exception e) {
    		System.out.println("[SalesforceService] - getLastNDaysSyncedLicenseResponse - " + e.getMessage());
    		throw e;
    	}
				
    	return response;
    }
    
    public List<LicenseResponse> syncNIPRLicenses(List<LicenseInternal> licenses) throws Exception {
    	List<LicenseResponse> licenseResponses = new ArrayList<LicenseResponse>();

        if(licenses.size() == 0) {
            return licenseResponses;
        }
        
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(licenses);
            LicenseResponse[] responses = this.restClient.apexRest(NIPR_LICENSE_SYNC, HttpMethod.POST, requestBody, LicenseResponse[].class);
            licenseResponses = Arrays.asList(responses);          
        } catch (Exception e) {        	
        	System.out.println("[SalesforceService] - syncNIPRLicenses - exception: " + e.getMessage());
        	throw e;
        }
        
        return licenseResponses;
    }
}
