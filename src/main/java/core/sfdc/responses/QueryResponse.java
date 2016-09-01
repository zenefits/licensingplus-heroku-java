package core.sfdc.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author shuchun.yang
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResponse <T>{
	private boolean done;

	private T[] records;
	
	private String nextRecordsUrl;
	
	private int totalSize;

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public String getNextRecordsUrl() {
		return this.nextRecordsUrl;
	}
	
	public void setNextRecordsUrl(String nextRecordsUrl) {
		this.nextRecordsUrl = nextRecordsUrl;
	}

	public T[] getRecords() {
		return records;
	}

	public void setRecords(T[] records) {
		this.records = records;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public String toString() {
		return "ClassPojo [done = " + done + ", nextRecordsUrl=" + nextRecordsUrl + ", totalSize = " + totalSize + ", records = " + records + "]";
	}

}