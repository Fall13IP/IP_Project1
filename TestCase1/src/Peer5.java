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
public class Peer5 {
	private static ClientFunction peer5;
	public static void startPeer(){
		peer5 = new ClientFunction("peer5.txt");
		peer5.registerPeer(1510);
		peer5.pQueryFunc();
	}
	
	public static void startTransfer(){
		long[] TimeTracker=null;
		TimeTracker = new long[50];
		long cumlativeTime = 0;
		List <PeerListNode> peerList;
		peerList = peer5.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));
		PeerListNode node = peerList.get(0);
		peer5.RFCIndexFunc(node);
		List<RFCIndexNode> rfcList = ClientFunction.getRfcIndexList();
		Calendar c1 = null,c2=null;
		for(int RFCCount=0;RFCCount<50;RFCCount++)
		{	
			c1=Calendar.getInstance();
			
		String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
		int rfcNo = rfcList.get(RFCCount).getRfcNumber();
		System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
		byte [] fileData = peer5.GetRFCFunc(node, rfcNo, rfcTitle);
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
		peer5.leaveFunc();
	}
		}


