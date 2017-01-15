package com.ptga123.Desbian.net.player;

import java.net.InetAddress;

import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class NetPlayer extends Mob {

	Sprite sprite;
	public InetAddress ipAdress;
	private String username;
	public int port;
	private boolean moving = false;
	private int direction = 0;

	public NetPlayer(InetAddress ipAdress, int port, String username, int x, int y) {
		this.x = x;
		this.y = y;
		this.port = port;
		this.ipAdress = ipAdress;
		this.username = username;
		sprite = Sprite.player_b;
	}

	public String getUsername() {
		return this.username;
	}

	public void update() {

	}

	public void render(Screen screen) {
		screen.renderPlayer(x, y, sprite);
	}
	
	public void setMoving(boolean moving){
		this.moving = moving;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}

}
