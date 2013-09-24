package org.server;

import org.peer.client.ClientFunction;
import org.peer.server.PeerServerApp;

public class Server {

	public static void main(String[] args) {
		if(args.length > 0){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			System.out.println("Peer server (P0) Started");
			ClientFunction peer = new ClientFunction(confFileName);
			peer.registerPeer(serverPortNumber);
			while(true){
				try {
					Thread.sleep(1800000);
					peer.keepAliveFunc();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		
	
		}
	}

}

