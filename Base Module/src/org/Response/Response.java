package org.Response;

import java.util.HashMap;

import org.common.ResponseType;

public class Response {
	
	ResponseType type;
	HashMap<String, Object> data;	
	public Response()
	{
		data = new HashMap<>();
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
	

}

