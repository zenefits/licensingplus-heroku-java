package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author shuchun.yang
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NIPRSyncedLicenseResponse {
	
	private String LicensingPlus__Number__c;

	private String LicensingPlus__NPN_Number_Formula__c;

	private String LicensingPlus__NIPR_Update_Date__c;

	private Attributes attributes;

	private String LicensingPlus__State__c;

	private String LicensingPlus__Effective_Date__c;

	public String getLicensingPlus__Number__c() {
		return LicensingPlus__Number__c;
	}

	public void setLicensingPlus__Number__c(String LicensingPlus__Number__c) {
		this.LicensingPlus__Number__c = LicensingPlus__Number__c;
	}

	public String getLicensingPlus__NPN_Number_Formula__c() {
		return LicensingPlus__NPN_Number_Formula__c;
	}

	public void setLicensingPlus__NPN_Number_Formula__c(String LicensingPlus__NPN_Number_Formula__c) {
		this.LicensingPlus__NPN_Number_Formula__c = LicensingPlus__NPN_Number_Formula__c;
	}

	public String getLicensingPlus__NIPR_Update_Date__c() {
		return LicensingPlus__NIPR_Update_Date__c;
	}

	public void setLicensingPlus__NIPR_Update_Date__c(String LicensingPlus__NIPR_Update_Date__c) {
		this.LicensingPlus__NIPR_Update_Date__c = LicensingPlus__NIPR_Update_Date__c;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public String getLicensingPlus__State__c() {
		return LicensingPlus__State__c;
	}

	public void setLicensingPlus__State__c(String LicensingPlus__State__c) {
		this.LicensingPlus__State__c = LicensingPlus__State__c;
	}

	public String getLicensingPlus__Effective_Date__c() {
		return LicensingPlus__Effective_Date__c;
	}

	public void setLicensingPlus__Effective_Date__c(String LicensingPlus__Effective_Date__c) {
		this.LicensingPlus__Effective_Date__c = LicensingPlus__Effective_Date__c;
	}

	@Override
	public String toString() {
		return "ClassPojo [LicensingPlus__Number__c = " + LicensingPlus__Number__c
				+ ", LicensingPlus__NPN_Number_Formula__c = " + LicensingPlus__NPN_Number_Formula__c
				+ ", LicensingPlus__NIPR_Update_Date__c = " + LicensingPlus__NIPR_Update_Date__c + ", attributes = "
				+ attributes + ", LicensingPlus__State__c = " + LicensingPlus__State__c
				+ ", LicensingPlus__Effective_Date__c = " + LicensingPlus__Effective_Date__c + "]";
	}
	
	public static class Attributes {
		private String type;

		private String url;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return "ClassPojo [type = " + type + ", url = " + url + "]";
		}
	}
}
