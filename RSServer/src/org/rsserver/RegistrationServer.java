package org.rsserver;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.Response.Response;
import org.Response.ResponseHelper;
import org.base.rsserver.PeerListNode;
import org.common.Constants;
import org.common.DataKeyConstants;
import org.common.ResponseType;
import org.request.Request;
import org.request.RequestType;

public class RegistrationServer extends Thread{
	
	private static int cookie;
	private Socket clientSocket;
	private static List<PeerListNode> peerList = Collections.synchronizedList(new LinkedList<PeerListNode>());;
	
	public RegistrationServer(Socket clientSocket)
	{
		this.clientSocket = clientSocket;		
		
	}
	
	
	
	@Override
	public void run() {
		
		ObjectInputStream objectInputStream;
		ObjectOutputStream objectOutputStream;
		Request request;
		Response response = null;
		System.out.println("Run method called");
		try {
			 objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
			 objectOutputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
			 request = (Request) objectInputStream.readObject();			
			 System.out.println("RS Server: Received Request");
			 request.printRequest();			 
			 if(request.getType() == RequestType.REGISTER){				 
				 response = handleRegisterRequest(request);
				 objectOutputStream.writeObject(response);				 
								 
			 }
			 else if(request.getType() == RequestType.LEAVE){
				 response = handleLeaveRequest(request);
				 objectOutputStream.writeObject(response);				 
				 
			 }else if (request.getType() == RequestType.P_QUERY){
				 response = handlePQuery(request);
				 objectOutputStream.writeObject(response);				
				 
			 }else if( request.getType() == RequestType.KEEP_ALIVE){
				 response = handleKeepAlive(request);
				 objectOutputStream.writeObject(response);			
				
			 }
			 if(response != null){
				 System.out.println("RS Server: Sending response");
				 response.printResponse();
			 }
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	
	}

	private Response handleLeaveRequest(Request request)
	{
		Response response = null;
		int cookie = getCookieFromRequest(request);
		
		if (cookie != -1){
			PeerListNode peerNode = getPeer(cookie);
			synchronized (peerList) {
				if (peerNode != null){
					
					peerNode.setAlive(false);
					response = ResponseHelper.createResponse(ResponseType.LEAVE_OK, new HashMap<String, Object>());
				}
				else{
					
					response = createPeerNotFoundResponse(ResponseType.LEAVE_ERROR);
				}
			}			
		}else{
			response = createInvalidCookieResponse(ResponseType.LEAVE_ERROR);
		}
		
		return response;
	}
	
	private Response handlePQuery(Request request){
		
		Response response = null;
		int cookie = getCookieFromRequest(request);
		if(cookie != -1){
			PeerListNode peerNode = getPeer(cookie);
			synchronized (peerList) {
				if(peerNode != null){
					List<PeerListNode> peerListToSend = Collections.synchronizedList(new LinkedList<PeerListNode>());
					for(int i = 0; i < peerList.size(); i++){
						PeerListNode node = peerList.get(i);
						if(node.isAlive()){
							peerListToSend.add(node);
						}
					}
					if(peerListToSend.size() == 1 && peerListToSend.get(0).getHostName().equals(peerNode.getHostName()) && peerListToSend.get(0).getPortNumber() == peerNode.getPortNumber()){
						HashMap<String, Object> responseData = new HashMap<String, Object>();
						responseData.put(DataKeyConstants.ERROR_MESSAGE, "No active peers");
						response = ResponseHelper.createResponse(ResponseType.PQUERY_ERROR, responseData);
					}else{						
						
						HashMap<String, Object> responseData = new HashMap<String, Object>();
						responseData.put(DataKeyConstants.PEER_LIST, peerListToSend);
						response = ResponseHelper.createResponse(ResponseType.PQUERY_OK, responseData);
					}
				}
				else{
					response = createPeerNotFoundResponse(ResponseType.PQUERY_ERROR);
				}
			}
		}else{
			response = createInvalidCookieResponse(ResponseType.PQUERY_ERROR);
		}
		return response;
	}
	
	private Response handleKeepAlive(Request request){
		
		Response response = null;
		int cookie = getCookieFromRequest(request);
		if(cookie != -1){
			PeerListNode peerNode = getPeer(cookie);
			if(peerNode != null){
				peerNode.setTtl(Constants.INITIAL_TTL_VALUE);
				response = ResponseHelper.createResponse(ResponseType.KEEP_ALIVE_OK, new HashMap<String,Object>());
			}else{
				response = createPeerNotFoundResponse(ResponseType.KEEP_ALIVE_ERROR);
			}
		}else{
			response = createInvalidCookieResponse(ResponseType.KEEP_ALIVE_ERROR);
		}
		return response;
	}
	private Response createPeerNotFoundResponse(ResponseType type){
		Response response;
		HashMap<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(DataKeyConstants.ERROR_MESSAGE, Constants.PEER_NOT_FOUND);
		response = ResponseHelper.createResponse(type, responseData);
		return response;
	}
	private Response createInvalidCookieResponse(ResponseType type){
		Response response;
		HashMap<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(DataKeyConstants.ERROR_MESSAGE, Constants.INVALID_COOKIE);
		response = ResponseHelper.createResponse(type, responseData);
		return response;
	}
	private int getCookieFromRequest(Request request){
		HashMap<String, Object> data = request.getData();
		int cookie = Integer.valueOf(data.get(DataKeyConstants.COOKIE).toString());
		return cookie;
	}
	private PeerListNode getPeer(int cookie){
		
		
		PeerListNode reqNode = null;
		synchronized (peerList) {
			
			Iterator<PeerListNode> iterator = peerList.iterator();
			while(iterator.hasNext()){
				PeerListNode node = iterator.next();
				
				if(node.getCookie() == cookie){
					reqNode = node;
					break;
				}
			}
		}	
		
		return reqNode;
	}
	private void printPeerList(){
	
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
	}
	private Response handleRegisterRequest(Request request){
		
		boolean success = false;
		Response response = null;
		String hostName = request.getData().get(DataKeyConstants.HOST_NAME).toString();
		PeerListNode node = peerExists(hostName);
		if(node != null){
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put(DataKeyConstants.COOKIE, node.getCookie());
			response = ResponseHelper.createResponse(ResponseType.REGISTER_OK, data);
		}else{
			int newCookie = generateCookie();
			success = addToPeerList(newCookie, request);
			if(success == true){
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put(DataKeyConstants.COOKIE, newCookie);
				response = ResponseHelper.createResponse(ResponseType.REGISTER_OK, data);
			}else{
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put(DataKeyConstants.ERROR_MESSAGE, Constants.REGISTER_ERROR);
				response = ResponseHelper.createResponse(ResponseType.REGISTER_ERROR, data);
			}
		}
		return response;
	}
	
	private PeerListNode peerExists(String hostName){
		PeerListNode peerFound = null;
		synchronized (peerList) {
			
			for(int i = 0; i < peerList.size(); i ++){
				PeerListNode node = peerList.get(i);
				if(node.getHostName() == hostName){
					peerFound = node;
					break;
				}
			}
			
		}
		return peerFound;
	}
	private boolean addToPeerList(int cookie, Request request){
		
		boolean success = false;
		try{
		HashMap<String, Object> requestData = request.getData();
		PeerListNode peerListNode = new PeerListNode();
		peerListNode.setHostName(requestData.get(DataKeyConstants.HOST_NAME).toString());
		peerListNode.setCookie(cookie);
		peerListNode.setAlive(true);
		peerListNode.setTtl(Constants.INITIAL_TTL_VALUE);
		peerListNode.setPortNumber(Integer.parseInt(requestData.get(DataKeyConstants.IP_PORT).toString()));
		int noOfRegs = peerListNode.getActiveTimes();
		if(noOfRegs == 0)
			peerListNode.setActiveTimes(1);
		else{
			Calendar timeStamp = peerListNode.getRecentTimestamp();
			Calendar currentTimeStamp = Calendar.getInstance();
			currentTimeStamp.add(Calendar.DAY_OF_MONTH, 30);
			if(timeStamp.before(currentTimeStamp)){
				noOfRegs = peerListNode.getActiveTimes();
				peerListNode.setActiveTimes(++noOfRegs);
			}
			else
				peerListNode.setActiveTimes(1);
			
		}			
		peerListNode.setRecentTimestamp(Calendar.getInstance());		
		peerList.add(peerListNode);
		success = true;
		}catch(Exception ex){
			System.out.print(ex.getMessage());
		}
		return success;
	}
	private int generateCookie()
	{
		int temp = cookie;
		cookie++;
		return temp;
	}
	public static int getCookie() {
		return cookie;
	}
	public static void setCookie(int cookie) {
		RegistrationServer.cookie = cookie;
	}



	public static List<PeerListNode> getPeerList() {
		return peerList;
	}




	

}
