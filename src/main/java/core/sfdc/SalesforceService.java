package core.sfdc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.nipr.LicenseInternal;
import core.sfdc.responses.LicenseResponse;
import core.sfdc.responses.NIPRSyncedLicenseCountResponse;
import core.sfdc.responses.NIPRSyncedLicenseResponse;
import core.sfdc.responses.QueryResponseWrapper;

/**
 * 
 * @author shuchun.yang
 *
 */
public class SalesforceService {
	
    private static final String NIPR_LICENSE_SYNC = "LicensingPlus/nipr/sync/license";
    private SalesforceRestClient restClient;
    
    public SalesforceService(String clientSecret, String clientKey, String username, String password, boolean isSandbox) {
    	this.restClient = new SalesforceRestClient(clientSecret, clientKey, username, password, isSandbox);
    }
    
    public List<NIPRSyncedLicenseCountResponse> getNIPRSyncedLicenseCountResponse(int days) throws Exception {
        String queryStr = "SELECT licensingplus__nipr_update_date__c, count(id) FROM licensingplus__License__c WHERE licensingplus__nipr_update_date__c = LAST_N_DAYS:" + days +
       					"GROUP BY licensingplus__nipr_update_date__c ORDER BY licensingplus__nipr_update_date__c DESC";
        QueryResponseWrapper<NIPRSyncedLicenseCountResponse> response = this.restClient.queryAll(queryStr, NIPRSyncedLicenseCountResponse.class);
       
        List<NIPRSyncedLicenseCountResponse> records = response.getRecords();
        return records;
    }
    
    public List<NIPRSyncedLicenseResponse> getNIPRSyncedLicenseResponseByDate(String date) throws Exception {    	
    	String queryStr = "SELECT licensingplus__nipr_update_date__c, licensingplus__npn_number_formula__c, licensingplus__state__c, licensingplus__effective_date__c, licensingplus__number__c "
				+ "FROM licensingplus__License__c "
				+ "WHERE licensingplus__nipr_update_date__c=" + date;
    	QueryResponseWrapper<NIPRSyncedLicenseResponse> response = this.restClient.queryAll(queryStr, NIPRSyncedLicenseResponse.class);
				
    	List<NIPRSyncedLicenseResponse> records = response.getRecords();
    	return records;
    }
    
    public List<LicenseResponse> syncNIPRLicenses(List<LicenseInternal> licenses) throws Exception {
    	List<LicenseResponse> licenseResponses = new ArrayList<LicenseResponse>();

        if(licenses.size() == 0) {
            return licenseResponses;
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(licenses);
        LicenseResponse[] responses = this.restClient.apexRest(NIPR_LICENSE_SYNC, HttpMethod.POST, requestBody, LicenseResponse[].class);
        licenseResponses = Arrays.asList(responses); 
        
        return licenseResponses;
    }
}
