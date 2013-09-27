import java.util.Scanner;


import org.peer.client.ClientFunction;
import org.peer.server.PeerServerApp;


public class PeerB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientFunction peerB;
		if (args.length == 4){
			String confFileName = args[0];
			int serverPortNumber = Integer.valueOf(args[1]);
			String rsServerIP = args[2];
			String peerFileValue=args[3];
			peerB = new ClientFunction(confFileName,rsServerIP);		
			PeerServerApp peerServerApp = new PeerServerApp(serverPortNumber);
			peerServerApp.start();
			
			while(true){

				System.out.println("Enter 1 to register");				
				System.out.println("Enter 2 to leave P2P");
				Scanner scanner = new Scanner(System.in);
				int input= scanner.nextInt();
				switch(input){
				case 1: peerB.registerPeer(serverPortNumber,peerFileValue);
					break;
				case 2: peerB.leaveFunc();
					break;				
				}
			}
			
			//implementation of Leave Function after 1 RFC download.
		}
		else{
			System.out.println("Please pass config file name , peer server port number , rs server ip as parameters");
		}
	}

}
