package com.ptga123.server;

import java.net.InetAddress;

public class ServerClient {
	
	public int userID;
	public InetAddress address;
	public int port;
	public boolean status = false;
	public String username;
	public int x, y;
	public boolean walking;
	public int direction;

	private static int userIDCounter = 1;
	
	public ServerClient(InetAddress address, int port, String username, int x, int y, boolean walking, int direction){
		userID = userIDCounter++;
		this.address = address;
		this.port = port;
		this.username = username;
		this.x = x;
		this.y = y;
		this.walking = walking;
		this.direction = direction;
		status = true;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public int hashCode(){
		return userID; 
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setWalking(boolean walking){
		this.walking = walking;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}

	public boolean isWalking() {
		return walking;
	}

	public int getDirection() {
		return direction;
	}
}
