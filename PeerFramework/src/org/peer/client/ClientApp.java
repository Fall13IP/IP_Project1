package org.peer.client;
import org.common.DataKeyConstants;


public class ClientApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientFunction peer = new ClientFunction();
		peer.registerPeer();
		peer.pQueryFunc();
		peer.keepAliveFunc();
		
		peer.leaveFunc();
	}

}
