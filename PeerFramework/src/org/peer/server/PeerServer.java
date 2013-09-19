package org.peer.server;

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
		Response response;
		
		try{
			objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
			objectOutputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
			request = (Request) objectInputStream.readObject();	
			if(request.getType() == RequestType.RFC_QUERY){
				response = handleRFCQueryRequest(request);
				objectOutputStream.writeObject(response);
			}
		}catch(IOException |ClassNotFoundException ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private Response handleRFCQueryRequest(Request request){
		
		Response response = null;
		List<RFCIndexNode> rfcList = new LinkedList<RFCIndexNode>();
		rfcList.addAll(ClientFunction.getRfcIndexList());
		HashMap<String, Object> data = new HashMap<>();
		data.put(DataKeyConstants.RFC_INDEX_LIST, rfcList);
		response = ResponseHelper.createResponse(ResponseType.GET_RFC_OK, data);
		return response;
	}
	
	private Response handleRFCIndexRequest(Request request){
		Response response= null;
		return response;
	}
	
	private void getRFCFileData(int rfcNumber, String rfcTitle){
		List<RFCIndexNode> rfcIndexList = ClientFunction.getRfcIndexList();
		synchronized (rfcIndexList) {
			for(int i = 0; i < rfcIndexList.size(); i++){
				
				RFCIndexNode rfcNode = rfcIndexList.get(i);
				if(rfcNode.getRfcNumber() == rfcNumber && rfcNode.getRfcTitle() == rfcTitle){
					
				}
			}
		}
	}

}
