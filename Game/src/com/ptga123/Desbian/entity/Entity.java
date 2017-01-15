package com.ptga123.Desbian.entity;

import java.util.Random;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.gui.Menu;
import com.ptga123.Desbian.level.Level;

public class Entity {
	public int x, y;
	private boolean removed = false;
	protected Sprite sprite;
	protected Level level;
	protected Menu menu;
	protected final Random random = new Random();

	public boolean ColideProjectile(int cx, int cy) {
		cx = cx + 16;
		cy = cy + 16;
		if ((x <= cx && x + 32 >= cx) && (y <= cy && y + 32 >= cy)) {
			return true;
		}
		return false;
	}

	public Entity() {

	}

	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void update() {

	}

	public void render(Screen screen) {
		if (sprite != null)
			screen.renderSprite((int) x, (int) y, sprite, true);
	}

	public void remove() {
		removed = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public boolean isRemoved() {
		// Remove from level
		return removed;
	}

	public void init(Level level) {
		this.level = level;
	}
}
