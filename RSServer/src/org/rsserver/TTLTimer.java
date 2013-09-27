package org.rsserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.base.rsserver.PeerListNode;

public class TTLTimer {

	private Timer timer;
	private static int interval;
	public TTLTimer(int seconds){
		timer = new Timer();
		interval = seconds;
		
	}
	
	public void startTTLTimer(){
		timer.scheduleAtFixedRate(new TTLTask(), interval * 1000, interval * 1000);
	}
	public void stopTTLTimer(){
		timer.cancel();
	}
	
	class TTLTask extends TimerTask{
		
		private List<PeerListNode> peerList = RegistrationServer.getPeerList();		
		
		@Override
		public void run() {
			
			//System.out.println("TTL timer run invoked");
			
			for(int i = 0; i <peerList.size(); i ++){
				PeerListNode peerNode = peerList.get(i);
				int ttlValue = peerNode.getTtl();
				ttlValue = ttlValue - TTLTimer.interval;
				if(ttlValue <= 0){
					peerNode.setAlive(false);
					peerNode.setTtl(0);
				}else{
					peerNode.setTtl(ttlValue);
				}
			}
			
	
		}
	
	}	


}
