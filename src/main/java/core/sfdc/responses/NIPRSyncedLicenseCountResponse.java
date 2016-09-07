package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NIPRSyncedLicenseCountResponse {
	private int expr0;

	private String NIPR_Update_Date__c;

	private Attributes attributes;

	public int getExpr0() {
		return expr0;
	}

	public void setExpr0(int expr0) {
		this.expr0 = expr0;
	}

	public String getNIPR_Update_Date__c() {
		return NIPR_Update_Date__c;
	}

	public void setNIPR_Update_Date__c(String NIPR_Update_Date__c) {
		this.NIPR_Update_Date__c = NIPR_Update_Date__c;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "ClassPojo [expr0 = " + expr0 + ", NIPR_Update_Date__c = " + NIPR_Update_Date__c + ", attributes = "
				+ attributes + "]";
	}
}
