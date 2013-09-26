package org.Response;


import java.util.List;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.base.peerserver.RFCIndexNode;
import org.base.rsserver.PeerListNode;
import org.common.DataKeyConstants;
import org.common.ResponseType;
import org.base.rsserver.PeerListNode;
public class Response implements Serializable {
	
	public static final long serialVersionUID = 750L;
	private List <PeerListNode> peerLists;
	private List <RFCIndexNode> rfcList;
	ResponseType type;
	HashMap<String, Object> data;	
	public Response()
	{
		data = new HashMap();
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
	
	public void printResponse(){
		
		int iterator;
		PeerListNode node;
		 RFCIndexNode rfcNode;
		System.out.println("Response type: " + this.getType());
		Set<String> keySet = this.getData().keySet();
		Iterator<String> keySetIterator =  keySet.iterator();
		System.out.println("BEGIN DATA");
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			System.out.println("Key: " + key);
			
			if(key.equals(DataKeyConstants.PEER_LIST))
			{
				peerLists= (List<PeerListNode>) this.getData().get(key);
				System.out.println("Peer List is:");
				for(iterator = 0; iterator < peerLists.size(); iterator ++){
		            node = peerLists.get(iterator);
		            System.out.println("PeerListnode:"+node.getHostName());
				}
				
			}
			else if(key.equals(DataKeyConstants.RFC_INDEX_LIST)){
				rfcList=(List<RFCIndexNode>) this.getData().get(key);
				for(iterator = 0; iterator < rfcList.size(); iterator ++){
		           rfcNode = rfcList.get(iterator);
		            System.out.println("RFC No: "+rfcNode.getRfcNumber());
		            System.out.println("RFC Title: "+rfcNode.getRfcTitle());
		        }
			}
			else{
				System.out.println("Value: " + this.getData().get(key).toString());
			}
			}
		System.out.println("END DATA");
	}
	

}

