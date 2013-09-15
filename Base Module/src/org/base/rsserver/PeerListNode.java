package org.base.rsserver;

import java.io.Serializable;
import java.util.Calendar;

public class PeerListNode implements Serializable{
	
	private String hostName;
	private int cookie;
	private boolean alive;
	private int ttl;
	private int portNumber;
	private int activeTimes;
	private Calendar recentTimestamp;
	
	public PeerListNode(){
		this.ttl = 7200;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public int getCookie() {
		return cookie;
	}
	public void setCookie(int cookie) {
		this.cookie = cookie;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public int getActiveTimes() {
		return activeTimes;
	}
	public void setActiveTimes(int activeTimes) {
		this.activeTimes = activeTimes;
	}
	public Calendar getRecentTimestamp() {
		return recentTimestamp;
	}
	public void setRecentTimestamp(Calendar recentTimestamp) {
		this.recentTimestamp = recentTimestamp;
	}
	
	
	

}
