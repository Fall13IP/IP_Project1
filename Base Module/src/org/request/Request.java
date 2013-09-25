package org.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Request implements Serializable {
	
	public static final long serialVersionUID = 650L;
	private RequestType type;
	private HashMap<String, Object> data;


	public Request(){
		data = new HashMap();
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
	
	public void printRequest(){
		System.out.println("Request type: " + this.getType());
		Set<String> keySet = this.getData().keySet();
		Iterator<String> keySetIterator =  keySet.iterator();
		System.out.println("BEGIN DATA");
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			System.out.println("Key: " + key);
			System.out.println("Value: " + this.getData().get(key).toString());
		}
		System.out.println("END DATA");
	}


}
