package org.peer.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.common.Constants;


public class PeerServerApp extends Thread {
	private int portno;
public PeerServerApp(int portno){
	this.portno=portno;
}
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;		
		try {
			System.out.println("host name: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println("Starting peer Server");
			serverSocket = new ServerSocket(portno);
		} catch (IOException e) {
			
			System.out.println("Error opening port: " + portno + " on RS server");
			System.exit(-1);
		}
		while(true){
			try{				
				
				clientSocket = serverSocket.accept();
				System.out.println("Connection accepted on peer server");
			}catch(IOException ex){
				System.out.println("Error accepting connection on peer Server");
			}
			PeerServer peerServer = new PeerServer(clientSocket);
			peerServer.start();
			
			/*try {
				clientSocket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}*/
			
		}
	
	}
}
			
