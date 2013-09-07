package org.Response;

import java.util.HashMap;

import org.common.ResponseType;

public class ResponseHelper {
	
	private static Response createResponse(ResponseType type, HashMap<String, Object> data){
		
		Response response = new Response();
		response.setType(type);
		response.setData(data);
		
		return response;
	}
	

}
