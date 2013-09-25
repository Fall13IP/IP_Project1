package org.peer;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.base.peerserver.RFCIndexNode;
import org.base.rsserver.PeerListNode;
import org.peer.client.ClientFunction;
import org.peer.client.ClientHelper;
import org.peer.server.PeerServerApp;


public class Peer {
	
	private static ClientFunction peer;

	public static void startPeer(int serverPortNumber){		
		peer.registerPeer(serverPortNumber);
		peer.pQueryFunc();
	}
	public static void startTransfer(){
		long[] TimeTracker;
		TimeTracker = new long[50];
		long cumlativeTime = 0;
		List <PeerListNode> peerList;
		peerList = peer.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));
		PeerListNode node = peerList.get(0);
		peer.RFCIndexFunc(node);
		List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
		System.out.println("RFC Count:"+rfcList.size());
		Calendar c1 = null,c2=null;
		for(int RFCCount=0;RFCCount<rfcList.size();RFCCount++)
		{	
			c1=Calendar.getInstance();			
			String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
			int rfcNo = rfcList.get(RFCCount).getRfcNumber();
			System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
			byte [] fileData = peer.GetRFCFunc(node, rfcNo, rfcTitle);
			if(fileData != null){
				System.out.println("File size: " + fileData.length);
				//ClientHelper.writeToDisk(fileData, rfcTitle);
			}
			c2=Calendar.getInstance();
			
			TimeTracker[RFCCount]=c2.getTimeInMillis()-c1.getTimeInMillis();
			System.out.println("time"+RFCCount+(TimeTracker[RFCCount]));
		
		}
		
		for(int timeIterator=0;timeIterator<50;timeIterator++)
		{
			cumlativeTime= cumlativeTime+TimeTracker[timeIterator];
		}
		System.out.println("Cumulative time: " + cumlativeTime);
		
	}
	public static void main(String[] args) {
		if (args.length > 0){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			peer = new ClientFunction(confFileName);		
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			System.out.println("Peer server started");
			Scanner scanner = new Scanner(System.in);
			while(true){
				System.out.println("Enter 1 to register");
				System.out.println("Enter 2 to begin transfer");
				System.out.println("Enter 3 to leave P2P");
				int input= scanner.nextInt();
				switch(input){
				case 1: Peer.startPeer(serverPortNumber);
					break;
				case 2: Peer.startTransfer();
					break;
				case 3: peer.leaveFunc();
					break;
				}
			}
		}

	}

}
