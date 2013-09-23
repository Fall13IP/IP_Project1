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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PeerServerApp peerServerApp = new PeerServerApp(1200);
		peerServerApp.start();
		System.out.println("After calling start");
		ClientFunction peer = new ClientFunction("peer1.txt");
		peer.registerPeer(1200);
		
		
	
		//peerList = peer.getPeerList();
		//peer.RFCIndexFunc(peerList.get(rand(peerList.size())));
		
		//peer.leaveFunc()

	}

}
