package org.peer.client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.Response.Response;
import org.base.peerserver.RFCIndexNode;
import org.base.rsserver.PeerListNode;
import org.common.Constants;
import org.common.DataKeyConstants;
import org.common.ResponseType;
import org.request.RequestHelper;
import org.request.Request;

public class ClientFunction {

	
	private int cookie;
	private int i=0;
	//private int PortNumber = Integer.parseInt(Constants.SERVER_PORT_ADDRESS);//takes the server IP from Constants and converts it into int.
	private List <PeerListNode> peerList;
	private static List<RFCIndexNode> rfcIndexList = Collections.synchronizedList(new LinkedList<RFCIndexNode>());;
	private List <RFCIndexNode> rfcList;
	private String configFileName;
	public ClientFunction(String configFileName){
		//cookie = 8;	
		this.configFileName = configFileName;
		populateRFCIndex(configFileName);
	}
	public int registerPeer(int portno) {
	
	String hostName = RequestHelper.getIP();
	//String port = String.valueOf(Constants.PEER_SERVER_PORT_NUMBER);
	
	//int portNo = Integer.parseInt(port);
	String x="";
	int y=0;
	Request clientRegisterData = RequestHelper.createRegisterRequest(hostName,portno);
	System.out.println("Host name :"+hostName);
	
	Response response = makeConnectionGetResponse(clientRegisterData,Constants.SERVER_IP_ADDRESS,Constants.RS_SERVER_PORT_NUMBER);
	System.out.println("Received response: " + response.getType().toString());
	HashMap<String, Object> data = response.getData();
	System.out.println("Value of cookie: " + data.get(DataKeyConstants.COOKIE).toString());
	this.cookie = Integer.valueOf(data.get(DataKeyConstants.COOKIE).toString());
	

	return 0;
}
	public int keepAliveFunc() {
		
		if(cookie==-1)
		{
			System.out.println("Cookie value is -1, some error");
		}
		else{
			
		
		Request keepAliveRequest = RequestHelper.createKeepAliveRequest(cookie);
		Response response = makeConnectionGetResponse(keepAliveRequest,Constants.SERVER_IP_ADDRESS,Constants.RS_SERVER_PORT_NUMBER);
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
		Response response = makeConnectionGetResponse(pQueryRequest,Constants.SERVER_IP_ADDRESS,Constants.RS_SERVER_PORT_NUMBER);
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
	public int RFCIndexFunc(PeerListNode peerNode)
	{	
		int success = 1;
		int iteratorFirst=0;
		int iteratorSecond=0;
		Request RFCIndexRequest = RequestHelper.createRFCQueryRequest(cookie);
		System.out.println();
		Response response = makeConnectionGetResponse(RFCIndexRequest,peerNode.getHostName(),peerNode.getPortNumber());
		if(response.getType() == ResponseType.RFC_QUERY_ERROR){
			
			success = 0;			
			System.out.println("RFC Index error: " + response.getData().get(DataKeyConstants.ERROR_MESSAGE));
		}
		HashMap<String,Object> RFCIndex = response.getData();
		rfcList = (List<RFCIndexNode>) RFCIndex.get(DataKeyConstants.RFC_INDEX_LIST);
		System.out.println("Printing peer list");
        for(int i = 0; i < rfcList.size(); i ++){
            RFCIndexNode node = rfcList.get(i);
            System.out.println(node.getRfcNumber());
        }
        //merges RFC lists
        for(iteratorFirst=0;iteratorFirst<rfcList.size();iteratorFirst++)
        {	
        	for(iteratorSecond=0;iteratorSecond<rfcIndexList.size();iteratorSecond++)
        	{
        		if((rfcList.get(iteratorFirst).getRfcNumber()==rfcIndexList.get(iteratorSecond).getRfcNumber()) && (rfcList.get(iteratorFirst).getRfcHostname().equals(rfcIndexList.get(iteratorSecond).getRfcHostname()))){
        			//compares RFC number and host, if it is there then does not includes it inside the list otherwise adds the thing in the list.
        			System.out.println("RFC number:"+rfcList.get(iteratorFirst).getRfcNumber()+" and Hostname: "+rfcList.get(iteratorFirst).getRfcHostname()+"are same");
        		}
        		else{
        			rfcIndexList.add(rfcList.get(iteratorFirst));
        			addEntryToConfigFile(rfcList.get(iteratorFirst).getRfcNumber(), rfcList.get(iteratorFirst).getRfcTitle());
        		}
      
        	}
        	
        }
             
        
        
        
        
        
		return success;
		
	}
	
	public List<PeerListNode> getPeerList() {
		return peerList;
	}
	public byte[] GetRFCFunc(PeerListNode peerNode, int rfcIndex, String rfcTitle){
		
		byte [] fileData = null;
		Request request = RequestHelper.createRFCRequest(rfcIndex, rfcTitle);
		if (request.getData() == null){
			System.out.println("get rfc request data null");
		}else{
			System.out.println("get rfc request data NOT null");
		}
		Response response = makeConnectionGetResponse(request, peerNode.getHostName(), peerNode.getPortNumber());
		if(response.getType() == ResponseType.GET_RFC_ERROR){
						
			System.out.println("GET RFC error: " + response.getData().get(DataKeyConstants.ERROR_MESSAGE));
		}else{
			fileData = (byte []) response.getData().get(DataKeyConstants.RFC_FILE_DATA);
		}
		return fileData;
	}
	
	public int leaveFunc() {
		
		
		if(cookie==-1)
		{
			System.out.println("Cookie value is -1, some error");
		}
		else{
			
		
		Request leaveRequest = RequestHelper.createLeaveRequest(cookie);
		Response response = makeConnectionGetResponse(leaveRequest,Constants.SERVER_IP_ADDRESS,Constants.RS_SERVER_PORT_NUMBER);
		System.out.println("Type of response  "+response.getType().toString());
		if(response.getType()==ResponseType.LEAVE_OK)
		{
			//cookie = -1;
		}
		else if(response.getType()==ResponseType.LEAVE_ERROR)
		{
			HashMap<String, Object> errData = response.getData();
			System.out.println("Error Message: "+errData.get(DataKeyConstants.ERROR_MESSAGE));
		}
		}
			
		return 0;
		
	}
	

	public Response makeConnectionGetResponse(Request Request,String ServerIP,int ServerPort){//makes socket connection and does request and response., made it a general IP and port address function, now even a client can use it.
		
		try {
			Socket socketConnection = new Socket(ServerIP,ServerPort);			
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
	public static List<RFCIndexNode> getRfcIndexList() {
		return rfcIndexList;
	}
	
	private void populateRFCIndex(String configFileName){
		
		try {
			String line = null;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(configFileName));
			while((line = bufferedReader.readLine()) != null){
				RFCIndexNode rfcNode = new RFCIndexNode();
				rfcNode.setRfcNumber(Integer.parseInt(line));
				
				line = bufferedReader.readLine();
				rfcNode.setRfcTitle(line);
				rfcNode.setTtlValue(Constants.INITIAL_TTL_VALUE);
                rfcNode.setRfcHostname(RequestHelper.getHost());
				rfcIndexList.add(rfcNode);
			}			
		} catch ( IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void addEntryToConfigFile(int rfcIndex, String rfcTitle){
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFileName,true));
			bufferedWriter.newLine();
			bufferedWriter.append(String.valueOf(rfcIndex));
			bufferedWriter.newLine();
			bufferedWriter.append(rfcTitle);
			bufferedWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void printRFCIndex(){
		
		for(int i = 0; i < rfcIndexList.size(); i++){
			RFCIndexNode node = rfcIndexList.get(i);
			System.out.println(node.getRfcNumber());
			System.out.println(node.getRfcTitle());
		}
	}
	
	public static void main(String args[]){
		ClientFunction clientFunction = new ClientFunction("peer1.txt");
		clientFunction.printRFCIndex();		
		clientFunction.addEntryToConfigFile(777, "rfc777");
	}
	
}

