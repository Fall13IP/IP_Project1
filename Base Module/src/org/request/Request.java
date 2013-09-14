package org.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;

public class Request implements Serializable {
	
	public static final long serialVersionUID = 650L;
	private RequestType type;
	private HashMap<String, Object> data;


	public Request(){
		data = new HashMap<>();
	}


	public RequestType getType() {
		return type;
	}


	public void setType(RequestType type) {
		this.type = type;
	}


	public HashMap<String, Object> getData() {
		return data;
	}


	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}


}
