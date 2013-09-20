package org.rsserver.app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import org.common.Constants;
import org.rsserver.RegistrationServer;
import org.rsserver.TTLTimer;

public class ServerApp {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try{
			System.out.println("host name: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println("Starting RS Server");
			serverSocket = new ServerSocket(Constants.RS_SERVER_PORT_NUMBER);
		}catch(IOException ex){
			System.out.println("Error opening port: " + Constants.RS_SERVER_PORT_NUMBER + " on RS server");
			System.exit(-1);
		}
		while(true){
			try{
				TTLTimer ttlTimer = new TTLTimer(60);
				ttlTimer.startTTLTimer();
				clientSocket = serverSocket.accept();
				System.out.println("Connection accepted");
			}catch(IOException ex){
				System.out.println("Error accepting connection on RS Server");
			}
			RegistrationServer registrationServer = new RegistrationServer(clientSocket);
			registrationServer.start();
			System.out.println("After calling run");
		
			
		}
	}

}
