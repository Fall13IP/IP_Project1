import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.base.peerserver.RFCIndexNode;
import org.base.rsserver.PeerListNode;
import org.peer.client.ClientFunction;
import org.peer.server.PeerServerApp;


public class PeerA {

	public static void main(String[] args) {
		// TODO  Auto-generated method stub
		ClientFunction peerA;
		if (args.length == 3){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			String rsServerIP = args[2];
			peerA = new ClientFunction(confFileName,rsServerIP);		
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			peerA.registerPeer(serverPortNumber);
			peerA.pQueryFunc();
			List <PeerListNode> peerList;
			peerList = peerA.getPeerList();
			//PeerListNode node = peerList.get(rand(peerList.size()));
			PeerListNode node = peerList.get(0);
			peerA.RFCIndexFunc(node);
			List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
			System.out.println("RFC Count:"+rfcList.size());
			Calendar c1 = null,c2=null;
			for(int RFCCount=0;RFCCount<rfcList.size();RFCCount++)
			{	
				c1=Calendar.getInstance();			
				String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
				int rfcNo = rfcList.get(RFCCount).getRfcNumber();
				System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
				byte [] fileData = peerA.GetRFCFunc(node, rfcNo, rfcTitle);
				if(fileData != null){
					System.out.println("File size: " + fileData.length);
					//ClientHelper.writeToDisk(fileData, rfcTitle);
				}
				c2=Calendar.getInstance();
				
				
			}
			
			
			
			
		}

		else{
			System.out.println("Please pass config file name , peer server port number , rs server ip as parameters");
		}
		
		
		
	}

}
 