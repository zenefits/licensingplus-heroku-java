package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author shuchun.yang
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NIPRSyncedLicenseResponse {
	@JsonProperty("Number__c")
	private String number;
	
	@JsonProperty("NPN_Number_Formula__c")
	private String npnNumberFormula;
	
	@JsonProperty("NIPR_Update_Date__c")
	private String updateDate;

	private Attributes attributes;
	
	@JsonProperty("State__c")
	private String state;

	@JsonProperty("Effective_Date__c")
	private String effectiveDate;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNpnNumberFormula() {
		return npnNumberFormula;
	}

	public void setNpnNumberFormula(String npnNumberFormula) {
		this.npnNumberFormula = npnNumberFormula;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public String toString() {
		return "ClassPojo [number = " + number
				+ ", npnNumberFormula = " + npnNumberFormula
				+ ", updateDate = " + updateDate + ", attributes = "
				+ attributes + ", state = " + state
				+ ", effectiveDate = " + effectiveDate + "]";
	}
}
