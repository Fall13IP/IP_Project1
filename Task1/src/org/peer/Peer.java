package org.peer;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.base.peerserver.RFCIndexNode;
import org.base.rsserver.PeerListNode;
import org.peer.client.ClientFunction;
import org.peer.client.ClientHelper;
import org.peer.server.PeerServerApp;
import org.request.RequestHelper;


public class Peer {
	
	private static ClientFunction peer;

	public static void startPeer(int serverPortNumber){		
		peer.registerPeer(serverPortNumber);
		peer.pQueryFunc();
	}
	public static void startPeerTask2(int serverPortNumber){		
		peer.registerPeer(serverPortNumber);
		
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
			System.out.println("RFC no in array: "+RFCCount+" time: "+(TimeTracker[RFCCount]));
		
		}
		
		for(int timeIterator=0;timeIterator<50;timeIterator++)
		{
			cumlativeTime= cumlativeTime+TimeTracker[timeIterator];
		}
		System.out.println("Cumulative time: " + cumlativeTime);
		
	}
	
	public static void task2(int serverPortNumber){
		
		startPeerTask2(serverPortNumber);
	}
	
	public static void startTransferTask2(int serverPortNumber){
		
		long[] TimeTracker;
		TimeTracker = new long[50];
		long cumlativeTime = 0;
		List <PeerListNode> peerList;
		peer.pQueryFunc();
		peerList = peer.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));
		for(int i=0; i < peerList.size(); i++){
			PeerListNode node = peerList.get(0);
			if(node.getHostName().equals(RequestHelper.getIP()) && node.getPortNumber() == serverPortNumber){
				
			}else{
				peer.RFCIndexFunc(node);
			}
		}
		
		List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
		System.out.println("RFC Count:"+rfcList.size());
		Calendar c1 = null,c2=null;
		for(int RFCCount=0;RFCCount<rfcList.size();RFCCount++)
		{	
				
			RFCIndexNode rfcNode = rfcList.get(RFCCount);
			String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
			int rfcNo = rfcList.get(RFCCount).getRfcNumber();
			System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
			if(rfcNode.getRfcHostname() != RequestHelper.getIP()){
				PeerListNode node = getPeer(rfcNode.getRfcHostname(), peerList);
				c1=Calendar.getInstance();	
				byte [] fileData = peer.GetRFCFunc(node, rfcNo, rfcTitle);
				if(fileData != null){
					System.out.println("File size: " + fileData.length);
					//ClientHelper.writeToDisk(fileData, rfcTitle);
				}
				c2=Calendar.getInstance();
				
				TimeTracker[RFCCount]=c2.getTimeInMillis()-c1.getTimeInMillis();
				System.out.println("RFC no in array: "+RFCCount+" time: "+(TimeTracker[RFCCount]));
			}
			
		
		}
		
		for(int timeIterator=0;timeIterator<50;timeIterator++)
		{
			cumlativeTime= cumlativeTime+TimeTracker[timeIterator];
		}
		System.out.println("Cumulative time: " + cumlativeTime);
		
	}
	
	private static PeerListNode getPeer(String ip, List<PeerListNode> peerList){
		
		PeerListNode node = null;
		for(int i=0; i < peerList.size(); i ++){
			node = peerList.get(i);
			if(node.getHostName() == ip){
				return node;
			}
		}
		return null;
	}
	public static void startTest1(int serverPortNumber){
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
	public static void startTest2(int serverPortNumber)
	{
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("Enter 1 to register");
			System.out.println("Enter 2 to begin transfer");
			System.out.println("Enter 3 to leave P2P");
			int input= scanner.nextInt();
			switch(input){
			case 1: Peer.task2(serverPortNumber);
				break;
			case 2: Peer.startTransferTask2(serverPortNumber);;
				break;
			case 3: peer.leaveFunc();
				break;
			}
		}
	}
	public static void main(String[] args) {
		if (args.length == 3){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			String rsServerIP = args[2];
			peer = new ClientFunction(confFileName,rsServerIP);		
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			System.out.println("Peer server started");
			System.out.println("Enter 1 for TestCase1 otherwise 2 for TestCase2");
			Scanner scanner = new Scanner(System.in);
			while(true){
				int UserChoice= scanner.nextInt();
				if(UserChoice==1)
				{
					startTest1(serverPortNumber);
				}
				else
				{
					startTest2(serverPortNumber);
				}
			}
			
		}

		else{
			System.out.println("Please pass config file name , peer server port number , rs server ip as parameters");
		}

	}

}
