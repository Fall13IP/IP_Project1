package org.peer.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.Response.Response;
import org.Response.ResponseHelper;
import org.base.peerserver.RFCIndexNode;
import org.common.Constants;
import org.common.DataKeyConstants;
import org.common.ResponseType;
import org.peer.client.ClientFunction;
import org.request.Request;
import org.request.RequestType;

public class PeerServer extends Thread {
	
	private Socket clientSocket;
	
	
	public PeerServer(Socket clientSocket){
		this.clientSocket = clientSocket;
		
	}

	@Override
	public void run() {
		
		ObjectInputStream objectInputStream;
		ObjectOutputStream objectOutputStream;
		Request request;
		Response response = null;
		
		try{
			objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
			objectOutputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
			request = (Request) objectInputStream.readObject();	
			System.out.println("Peer server: Request received");
			request.printRequest();
			if(request.getType() == RequestType.RFC_QUERY){
				response = handleRFCQueryRequest(request);
				objectOutputStream.writeObject(response);
			}else if(request.getType() == RequestType.RFC_REQUEST){
				response = handleRFCIndexRequest(request);
				objectOutputStream.writeObject(response);
			}
			if(response != null){
				System.out.println("Peer server: sending response");
				response.printResponse();
			}
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}
	
	private Response handleRFCQueryRequest(Request request){
		
		Response response = null;
		List<RFCIndexNode> rfcList = new LinkedList<RFCIndexNode>();
		rfcList.addAll(ClientFunction.getRfcIndexList());
		HashMap<String, Object> data = new HashMap();
		data.put(DataKeyConstants.RFC_INDEX_LIST, rfcList);
		response = ResponseHelper.createResponse(ResponseType.RFC_QUERY_OK, data);
		return response;
	}
	
	private Response handleRFCIndexRequest(Request request){
		Response response= null;
		HashMap<String, Object> requestData = request.getData();
		int rfcNumber = Integer.valueOf(requestData.get(DataKeyConstants.RFC_INDEX).toString());
		String rfcTitle = (String) requestData.get(DataKeyConstants.RFC_TITLE);
		HashMap<String, Object> responseData = new HashMap<String, Object>();
		byte [] fileData = getRFCFileData(rfcNumber, rfcTitle);
		if(fileData != null){
			responseData.put(DataKeyConstants.RFC_FILE_DATA, fileData);
			response = ResponseHelper.createResponse(ResponseType.GET_RFC_OK, responseData);
		}else{
			responseData.put(DataKeyConstants.ERROR_MESSAGE, Constants.RFC_NOT_FOUND);
			response = ResponseHelper.createResponse(ResponseType.GET_RFC_ERROR, responseData);
		}
		
		return response;
	}
	
	private byte[] getRFCFileData(int rfcNumber, String rfcTitle){
		
		List<RFCIndexNode> rfcIndexList = ClientFunction.getRfcIndexList();
		File file = new File(rfcTitle);
		long length = file.length();
		
		byte [] fileData = new byte [(int)length];
		//synchronized (rfcIndexList) {
			for(int i = 0; i < rfcIndexList.size(); i++){
				
				RFCIndexNode rfcNode = rfcIndexList.get(i);				
				if(rfcNode.getRfcNumber() == rfcNumber && rfcNode.getRfcTitle().equals(rfcTitle)){
					
					try {
						FileInputStream fileInputStream = new FileInputStream(file);						
						fileInputStream.read(fileData);
						fileInputStream.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
				}
			}
		//}
			
		return fileData;
	}
	

	public static void main(String args[]){
		
	}
}
