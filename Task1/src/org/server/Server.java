package org.server;

import org.peer.client.ClientFunction;
import org.peer.server.PeerServerApp;

public class Server {

	public static void main(String[] args) {
		if(args.length == 3){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			String rsServerIP = args[2];
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			System.out.println("Peer server (P0) Started");
			ClientFunction peer = new ClientFunction(confFileName,rsServerIP);
			//peer.registerPeer(serverPortNumber);
			while(true){
				try {
					Thread.sleep(1800000);
					peer.keepAliveFunc();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		
	
		}else{
			System.out.println("Please pass config file name , peer server port number , rs server ip as parameters");
		}
	}

}

