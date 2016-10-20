package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NIPRSyncedLicenseCountResponse {
	private int expr0;
	
	@JsonProperty("NIPR_Update_Date__c")
	private String updateDate;

	private Attributes attributes;

	public int getExpr0() {
		return expr0;
	}

	public void setExpr0(int expr0) {
		this.expr0 = expr0;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "ClassPojo [expr0 = " + expr0 + ", updateDate = " + updateDate + ", attributes = "
				+ attributes + "]";
	}
}
