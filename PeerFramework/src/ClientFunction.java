import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import org.Response.Response;
import org.common.Constants;
import org.common.DataKeyConstants;
import org.request.RequestHelper;
import org.request.Request;

public class ClientFunction {

	ClientFunction(){
		cookie = -1;
	}
	private int cookie;
	public int registerPeer() {
	
	String hostName = RequestHelper.getHost();
	String port = RequestHelper.getIP();
	//int portNo = Integer.parseInt(port);
	String x="";
	int y=0;
	Request clientRegisterData = RequestHelper.createRegisterRequest(hostName,1000);
	System.out.println("Host name :"+hostName);
	
	Response response = makeConnectionGetResponse(clientRegisterData);
	System.out.println("Received response: " + response.getType().toString());
	HashMap<String, Object> data = response.getData();
	System.out.println("Value of cookie: " + data.get(DataKeyConstants.COOKIE).toString());
	this.cookie = (int) data.get(DataKeyConstants.COOKIE);
	
	//System.out.println("port number"+Constants.RS_SERVER_PORT_NUMBER);
	/*try {
		Socket socketConnection = new Socket("10.139.62.151",1000);
		//Socket s = new Socket("PanchaLenovo",1000);
		System.out.println("Connected to server");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(socketConnection.getInputStream());
		objectOutputStream.writeObject(clientRegisterData);
		
	}
	catch (Exception e) {
		e.printStackTrace();
	}*/
	return 0;
}
	public int keepAliveFunc() {
		
		if(cookie==-1)
		{
			System.out.println("Cookie value is -1, some error");
		}
		else{
			
		
		Request keepAliveRequest = RequestHelper.createKeepAliveRequest(cookie);
		Response response = makeConnectionGetResponse(keepAliveRequest);
		System.out.println("Type of response  "+response.getType().toString());
		
		}
		return 0;
		
	}
	public int pQueryFunc() {
		Request pQueryRequest = RequestHelper.createPqueryRequest(cookie);
		
		
		
		return 0;
		
	}
	public int leaveFunc() {
		
		
		if(cookie==-1)
		{
			System.out.println("Cookie value is -1, some error");
		}
		else{
			
		
		Request leaveRequest = RequestHelper.createLeaveRequest(cookie);
		Response response = makeConnectionGetResponse(leaveRequest);
		System.out.println("Type of response  "+response.getType().toString());
		
		}
			
		return 0;
		
	}
	public Response makeConnectionGetResponse(Request Request){
		
		try {
			Socket socketConnection = new Socket("10.139.62.151",1000);			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
			objectOutputStream.writeObject(Request);			
			ObjectInputStream objectInputStream = new ObjectInputStream(socketConnection.getInputStream());
			System.out.println("Request from Client"+Request.getType().toString());
			Response response = (Response) objectInputStream.readObject();
			return response;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
		
	}
}
