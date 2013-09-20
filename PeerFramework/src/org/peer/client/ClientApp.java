package org.peer.client;
import org.common.DataKeyConstants;
import org.peer.server.PeerServerApp;


public class ClientApp {

	public static void main(String[] args) {
		PeerServerApp peerServerApp = new PeerServerApp();
		peerServerApp.start();
		System.out.println("After calling start");
		ClientFunction peer = new ClientFunction("peer1.txt");
		peer.registerPeer();
		peer.pQueryFunc();
		peer.keepAliveFunc();
		
		peer.leaveFunc();
	}

}
