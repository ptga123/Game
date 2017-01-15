package com.ptga123.Desbian.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	private boolean[] toggle_keys = new boolean[120];
	public boolean up, down, left, right, wasd;
	public boolean toggle_map;
	public boolean respawn;
	public boolean sprint;
	public boolean f_ex, w_ex;
	public boolean chat;
	public boolean inventory;
	public boolean teleup, teledown, teleleft, teleright;
	// Admin Tools
	public boolean admin;
	public boolean hadd, hmin;
	public boolean help;

	public boolean place1, place2, place3;
	public boolean buildmode;

	public void update() {
		// Adder Constructor
		sprint = keys[KeyEvent.VK_SHIFT];
		admin = keys[KeyEvent.VK_TAB];
		wasd = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_A] || keys[KeyEvent.VK_S] || keys[KeyEvent.VK_D]
				|| keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_RIGHT];
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		teleup = keys[KeyEvent.VK_UP];
		teledown = keys[KeyEvent.VK_DOWN];
		teleleft = keys[KeyEvent.VK_LEFT];
		teleright = keys[KeyEvent.VK_RIGHT];
		chat = keys[KeyEvent.VK_T];
		hadd = keys[KeyEvent.VK_Z];
		hmin = keys[KeyEvent.VK_X];
		help = keys[KeyEvent.VK_H];
		respawn = keys[KeyEvent.VK_R];

		place1 = keys[KeyEvent.VK_1];
		place2 = keys[KeyEvent.VK_2];
		place3 = keys[KeyEvent.VK_3];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		toggle_keys[e.getKeyCode()] = true;
		if (e.getKeyCode() == KeyEvent.VK_I) {
			inventory = !inventory;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			buildmode = !buildmode;
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		toggle_keys[e.getKeyCode()] = true;
	}

	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		toggle_keys[e.getKeyCode()] = true;
	}

}
