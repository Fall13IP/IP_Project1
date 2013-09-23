import java.util.List;

import org.base.peerserver.RFCIndexNode;
import org.base.rsserver.PeerListNode;
import org.peer.client.ClientApp1;
import org.peer.client.ClientFunction;
import org.peer.client.ClientHelper;

import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Peer4 {
	private static ClientFunction peer4;
	public static void startPeer(){
		peer4 = new ClientFunction("peer4.txt");
		peer4.registerPeer(1400);
		peer4.pQueryFunc();
	}
	
	public static void startTransfer(){
		long[] TimeTracker =null;
		TimeTracker = new long[50];
		long cumlativeTime = 0;
		List <PeerListNode> peerList;
		peerList = peer4.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));
		PeerListNode node = peerList.get(0);
		peer4.RFCIndexFunc(node);
		List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
		Calendar c1 = null,c2=null;
		for(int RFCCount=0;RFCCount<50;RFCCount++)
		{	
			c1=Calendar.getInstance();
			
		String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
		int rfcNo = rfcList.get(RFCCount).getRfcNumber();
		System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
		byte [] fileData = peer4.GetRFCFunc(node, rfcNo, rfcTitle);
		if(fileData != null){
			System.out.println("File size: " + fileData.length);
			ClientHelper.writeToDisk(fileData, rfcTitle);
		}
		c2=Calendar.getInstance();
		
		TimeTracker[RFCCount]=c2.getTimeInMillis()-c1.getTimeInMillis();
		System.out.println("time"+RFCCount+(TimeTracker[RFCCount]));
		
		}
		
		for(int timeIterator=0;timeIterator<50;timeIterator++)
		{
			cumlativeTime= cumlativeTime+TimeTracker[timeIterator];
		}
		peer4.leaveFunc();
	}
	
	}

