/**
 * 
 */
package org.request;

import java.util.HashMap;

import org.common.DataKeyConstants;

/**
 * @author Rituraj
 *
 */
public class RequestHelper {

	public static Request createRegisterRequest(String hostName, int portNumber){
		Request request = new Request();
		request.setType(RequestType.REGISTER);
		HashMap<String,Object> data = request.getData();
		data.put(DataKeyConstants.HOST_NAME, hostName);
		data.put(DataKeyConstants.IP_PORT, portNumber);
		return request;
	}
	public static Request createLeaveRequest(int cookie)//should we supply cookie here
	{
		   Request request = new Request();
		   request.setType(RequestType.LEAVE);
		   HashMap<String,Object> data = request.getData();
		   data.put(DataKeyConstants.COOKIE, cookie);
		   return request;
	}
	public static Request createPqueryRequest(int cookie){
		Request request = new Request();
		request.setType(RequestType.P_QUERY);
		HashMap<String,Object> data = request.getData();
		data.put(DataKeyConstants.COOKIE, cookie);
		return request;
	}
	public static Request createKeepAliveRequest(int cookie){
		Request request = new Request();
		request.setType(RequestType.KEEP_ALIVE);
		HashMap<String,Object> data = request.getData();
		data.put(DataKeyConstants.COOKIE, cookie);
		return request;
	}
	public static Request createRFCQueryRequest(int cookie){ //check is we need to send cookie
		Request request = new Request();
		request.setType(RequestType.RFC_QUERY);
		return request;
	}
	public static Request createRFCRequest(int rfcindex){
		Request request = new Request();
		request.setType(RequestType.RFC_REQUEST);
		HashMap<String,Object> data = request.getData();
		data.put(DataKeyConstants.RFC_INDEX, rfcindex);
		return request;
	}
	
}
