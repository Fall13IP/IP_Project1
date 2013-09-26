import org.peer.client.ClientFunction;
import org.peer.server.PeerServerApp;


public class PeerB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientFunction peerB;
		if (args.length == 3){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			String rsServerIP = args[2];
			peerB = new ClientFunction(confFileName,rsServerIP);		
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			peerB.registerPeer(serverPortNumber);
			//implementation of Leave Function after 1 RFC download.
		}
		else{
			System.out.println("Please pass config file name , peer server port number , rs server ip as parameters");
		}
	}

}
