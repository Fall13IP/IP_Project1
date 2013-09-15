import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.Response.Response;
import org.base.rsserver.PeerListNode;
import org.common.Constants;
import org.common.DataKeyConstants;
import org.common.ResponseType;
import org.request.RequestHelper;
import org.request.Request;

public class ClientFunction {

	ClientFunction(){
		cookie = 8;
	}
	private int cookie;
	private List <PeerListNode> peerList;
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
		if(response.getType()==ResponseType.KEEP_ALIVE_ERROR)
		{
			HashMap<String, Object> errData = response.getData();
			System.out.println("Error Message: "+errData.get(DataKeyConstants.ERROR_MESSAGE));
		}
		}
		return 0;
		
	}
	public int pQueryFunc() {
		Request pQueryRequest = RequestHelper.createPqueryRequest(cookie);
		int index =0;
		Response response = makeConnectionGetResponse(pQueryRequest);
		HashMap<String, Object> ClientList = response.getData();
		System.out.println(response.getType().toString());
		peerList = (List<PeerListNode>) ClientList.get("peerList");
		/*for(index =0;index<peerList.size();index++)
		{
			System.out.println(peerList.get(index));
		*/
		
		 System.out.println("Printing peer list");
	        for(int i = 0; i < peerList.size(); i ++){
	            PeerListNode node = peerList.get(i);
	            System.out.println(node.getHostName());
	            System.out.println(node.getCookie());
	            System.out.println(node.isAlive());
	            System.out.println(node.getTtl());    
	            System.out.println(node.getPortNumber());
	            System.out.println(node.getActiveTimes());
	            System.out.println(node.getRecentTimestamp());
	        }
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
		if(response.getType()==ResponseType.LEAVE_OK)
		{
			cookie = -1;
		}
		else if(response.getType()==ResponseType.LEAVE_ERROR)
		{
			HashMap<String, Object> errData = response.getData();
			System.out.println("Error Message: "+errData.get(DataKeyConstants.ERROR_MESSAGE));
		}
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

