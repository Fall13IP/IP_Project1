package org.base.peerserver;

import java.io.Serializable;

public class RFCIndexNode implements Serializable{
	
	private int rfcNumber;
	private String rfcTitle;
	private String rfcHostname;
	private int ttlValue;
	
	public int getRfcNumber() {
		return rfcNumber;
	}
	public void setRfcNumber(int rfcNumber) {
		this.rfcNumber = rfcNumber;
	}
	public String getRfcTitle() {
		return rfcTitle;
	}
	public void setRfcTitle(String rfcTitle) {
		this.rfcTitle = rfcTitle;
	}
	public String getRfcHostname() {
		return rfcHostname;
	}
	public void setRfcHostname(String rfcHostname) {
		this.rfcHostname = rfcHostname;
	}
	public int getTtlValue() {
		return ttlValue;
	}
	public void setTtlValue(int ttlValue) {
		this.ttlValue = ttlValue;
	}
	

}
