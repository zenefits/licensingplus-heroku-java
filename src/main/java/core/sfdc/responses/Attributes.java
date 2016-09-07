package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author shuchun.yang
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {
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