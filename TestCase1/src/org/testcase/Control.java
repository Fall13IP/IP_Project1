package org.testcase;
import java.util.Scanner;


public class Control {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 0");
		int input= scanner.nextInt();
		switch(input){
		case 0: Peer1.startPeer();
				
		}
		System.out.println("Enter 1");
		int input1= scanner.nextInt();
		switch(input1){
		case 1: Peer2.startPeer();
				Peer3.startPeer();
				Peer4.startPeer();
				Peer5.startPeer();
				Peer6.startPeer();
		
			
		}
		System.out.println("Enter 2");
		int input2 = scanner.nextInt();
		switch(input2){
		case 2: Peer2.startTransfer();
				Peer3.startTransfer();
				Peer4.startTransfer();
				Peer5.startTransfer();
				Peer6.startTransfer();
		}
	}

}
