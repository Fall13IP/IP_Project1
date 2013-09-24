import java.util.List;

import org.base.rsserver.PeerListNode;
import org.peer.client.ClientFunction;
import org.peer.server.PeerServerApp;

/**
 * 
 */

/**
 * @author Rituraj
 *
 */
public class Peer1 {

	/**
	 * @param args
	 */
	public static void startPeer() {
		
		PeerServerApp peerServerApp = new PeerServerApp(1200);
		peerServerApp.start();
		System.out.println("After calling start");
		ClientFunction peer = new ClientFunction("peer1.txt");
		peer.registerPeer(1200);
		while(true){
			try {
				Thread.sleep(1800000);
				peer.keepAliveFunc();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
		//peerList = peer.getPeerList();
		//peer.RFCIndexFunc(peerList.get(rand(peerList.size())));
		
		//peer.leaveFunc()

	}

}
