package org.peer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private static String peerName;
	public static void startPeer(int serverPortNumber){		
		peer.registerPeer(serverPortNumber,peerName);
		peer.pQueryFunc();
	}
	public static void startPeerTask2(int serverPortNumber){		
		peer.registerPeer(serverPortNumber,peerName);
		
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
		
		BufferedWriter bufferedWriter;
		
		try {
			 bufferedWriter = new BufferedWriter(new FileWriter("Timing_" + peerName + ".txt"));
	
		
		long[] TimeTracker;
		
		long cumlativeTime = 0;
		List <PeerListNode> peerList;
		peer.pQueryFunc();
		peerList = peer.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));
		for(int i=0; i < peerList.size(); i++){
			PeerListNode node = peerList.get(i);
			if(node.getHostName().equals(RequestHelper.getIP()) && node.getPortNumber() == serverPortNumber){
				
			}else{
				peer.RFCIndexFunc(node);
			}
		}
		
		System.out.println("Got RFC index from all peers, type any number to start transfer");
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		scanner.close();
		List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
		TimeTracker = new long[rfcList.size()];
		System.out.println("RFC Count:"+rfcList.size());
		Calendar c1 = null,c2=null;
		long accTime = 0;
		for(int RFCCount=0;RFCCount<rfcList.size();RFCCount++)
		{	
				
			RFCIndexNode rfcNode = rfcList.get(RFCCount);
			String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
			int rfcNo = rfcList.get(RFCCount).getRfcNumber();
			System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
			if(!(rfcNode.getRfcHostname().equals(RequestHelper.getIP()))){
				System.out.println("rfcNode.getRfcHostname() " + rfcNode.getRfcHostname());
				PeerListNode node = getPeer(rfcNode.getRfcHostname(), peerList);
				c1=Calendar.getInstance();	
				byte [] fileData = peer.GetRFCFunc(node, rfcNo, rfcTitle);
				if(fileData != null){
					System.out.println("File size: " + fileData.length);
					//ClientHelper.writeToDisk(fileData, rfcTitle);
				}
				c2=Calendar.getInstance();
				
				TimeTracker[RFCCount]=c2.getTimeInMillis()-c1.getTimeInMillis();
				accTime = accTime + TimeTracker[RFCCount];
				String printValue = "RFC no in array: "+RFCCount+" time: "+(TimeTracker[RFCCount]);
				System.out.println(printValue);
				bufferedWriter.append(printValue);
				bufferedWriter.newLine();
				bufferedWriter.append(String.valueOf(accTime));
				bufferedWriter.newLine();
			}
			
		
		}
		
		for(int timeIterator=0;timeIterator<50;timeIterator++)
		{
			cumlativeTime= cumlativeTime+TimeTracker[timeIterator];
		}
		System.out.println("Cumulative time: " + cumlativeTime);
		bufferedWriter.close();
	} catch (IOException e) {
			
			e.printStackTrace();
	}
		
	}
	
	private static void startTransferBestCase(int serverPortNumber){
		
		BufferedWriter bufferedWriter;
		
		try {
			 bufferedWriter = new BufferedWriter(new FileWriter("Timing_best" + peerName + ".txt"));
	
		
		long[] TimeTracker;
		
		long cumlativeTime = 0;
		List <PeerListNode> peerList;		
		peerList = peer.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));		
		
		List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
		TimeTracker = new long[rfcList.size()];
		System.out.println("RFC Count:"+rfcList.size());
		Calendar c1 = null,c2=null;
		long accTime = 0;
		for(int RFCCount=0;RFCCount<rfcList.size();RFCCount++)
		{	
				
			RFCIndexNode rfcNode = rfcList.get(RFCCount);
			String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
			int rfcNo = rfcList.get(RFCCount).getRfcNumber();
			System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
			if(!(rfcNode.getRfcHostname().equals(RequestHelper.getIP()))){
				System.out.println("rfcNode.getRfcHostname() " + rfcNode.getRfcHostname());
				PeerListNode node = getPeer(rfcNode.getRfcHostname(), peerList);
				c1=Calendar.getInstance();	
				byte [] fileData = peer.GetRFCFunc(node, rfcNo, rfcTitle);
				if(fileData != null){
					System.out.println("File size: " + fileData.length);
					//ClientHelper.writeToDisk(fileData, rfcTitle);
				}
				c2=Calendar.getInstance();
				
				TimeTracker[RFCCount]=c2.getTimeInMillis()-c1.getTimeInMillis();
				accTime = accTime + TimeTracker[RFCCount];
				String printValue = "RFC no in array: "+RFCCount+" time: "+(TimeTracker[RFCCount]);
				System.out.println(printValue);
				bufferedWriter.append(printValue);
				bufferedWriter.newLine();
				bufferedWriter.append(String.valueOf(accTime));
				bufferedWriter.newLine();
			}
			
		
		}
		
		
		bufferedWriter.close();
	} catch (IOException e) {
			
			e.printStackTrace();
	}
		
	}
	
	private static void bestCase(int serverPortNumber){
		
		Scanner scanner = new Scanner(System.in);
		while(true){		
		
			System.out.println("Enter 1 to register");
			System.out.println("Enter 2 to Pquery");
			System.out.println("Enter 3 to get rfc index");
			System.out.println("Enter 4 to begin transfer");
			int input= scanner.nextInt();
			switch(input){
			case 1: 
				Peer.startPeerTask2(serverPortNumber);
				break;
			case 2: {
				peer.pQueryFunc();
				break;
			}
				
			case 3:{
				System.out.println("Enter index in peerList to get rfc index from");
				int peerNo = scanner.nextInt();
				List<PeerListNode> peerList = peer.getPeerList();
				PeerListNode peerNode = peerList.get(peerNo);
				peer.RFCIndexFunc(peerNode);
				break;
			}
			case 4:{
				startTransferBestCase(serverPortNumber);
				break;
			}
				
			}
		}
	}
	
	private static PeerListNode getPeer(String ip, List<PeerListNode> peerList){
		
		PeerListNode node = null;
		for(int i=0; i < peerList.size(); i ++){
			node = peerList.get(i);
			if(node.getHostName().equals(ip)){
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
			case 1: 
				Peer.task2(serverPortNumber);
					break;
			case 2: 
				Peer.startTransferTask2(serverPortNumber);
					break;
			case 3: 
				peer.leaveFunc();
					break;
			}
		}
	}
	public static void main(String[] args) {
		if (args.length == 4){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			String rsServerIP = args[2];
			peerName = args[3];
			peer = new ClientFunction(confFileName,rsServerIP);		
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			System.out.println("Peer server started");
			System.out.println("Enter 1 for TestCase1 , 2 for TestCase2, 3 for bestcase");
			Scanner scanner = new Scanner(System.in);
			while(true){
				int UserChoice= scanner.nextInt();
				if(UserChoice==1)
				{
					startTest1(serverPortNumber);
				}
				else if(UserChoice == 2)
				{
					startTest2(serverPortNumber);
				}
				else if(UserChoice == 3){
					bestCase(serverPortNumber);
				}
			}
			
		}

		else{
			System.out.println("Please pass config file name , peer server port number , rs server ip as parameters");
		}

	}

}
