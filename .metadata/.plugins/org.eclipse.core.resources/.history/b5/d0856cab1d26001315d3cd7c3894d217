package org.Response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.common.ResponseType;

public class Response implements Serializable {
	
	public static final long serialVersionUID = 750L;
	
	ResponseType type;
	HashMap<String, Object> data;	
	public Response()
	{
		data = new HashMap();
	}
	public ResponseType getType() {
		return type;
	}
	public void setType(ResponseType type) {
		this.type = type;
	}
	public HashMap<String, Object> getData() {
		return data;
	}
	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	
	public void printResponse(){
		System.out.println("Response type: " + this.getType());
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

