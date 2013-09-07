/**
 * 
 */
package org.request;

/**
 * @author Rituraj
 *
 */
public class RequestHelper {

	public void createRegisterRequest(String hostName, int portNumber){
		Request request = new Request();
		request.setType(RequestType.REGISTER);
		
	}
	
}
