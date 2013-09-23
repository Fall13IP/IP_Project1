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
public class Peer6 {
	private static ClientFunction peer6;
	public static void startPeer(){
		peer6 = new ClientFunction("peer6.txt");
		peer6.registerPeer(1600);
		peer6.pQueryFunc();
	}
	
	public static void startTransfer(){
		long[] TimeTracker=null;
		TimeTracker = new long[50];
		long cumlativeTime = 0;
		List <PeerListNode> peerList;
		peerList = peer6.getPeerList();
		//PeerListNode node = peerList.get(rand(peerList.size()));
		PeerListNode node = peerList.get(0);
		peer6.RFCIndexFunc(node);
		List<RFCIndexNode> rfcList = peer6.getRfcIndexList();
		Calendar c1 = null,c2=null;
		for(int RFCCount=0;RFCCount<50;RFCCount++)
		{	
			c1.getInstance();
			
		String rfcTitle = rfcList.get(RFCCount).getRfcTitle();
		int rfcNo = rfcList.get(RFCCount).getRfcNumber();
		System.out.println("RFC title " + rfcTitle + "  rfc no " + rfcNo);
		byte [] fileData = peer6.GetRFCFunc(node, rfcNo, rfcTitle);
		if(fileData != null){
			System.out.println("File size: " + fileData.length);
			ClientHelper.writeToDisk(fileData, rfcTitle);
		}
		c2.getInstance();
		
		TimeTracker[RFCCount]=c2.getTimeInMillis()-c1.getTimeInMillis();
		System.out.println("time"+RFCCount+(TimeTracker[RFCCount]));
		
		}
		
		for(int timeIterator=0;timeIterator<50;timeIterator++)
		{
			cumlativeTime= cumlativeTime+TimeTracker[timeIterator];
		}
		peer6.leaveFunc();
	}
	
	}


