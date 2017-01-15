package com.ptga123.Desbian.level.tile;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class WaterTile extends Tile {
	private boolean removed = false;

	public WaterTile(Sprite sprite) {
		super(sprite);
	}

	@SuppressWarnings("static-access")
	public void render(int x, int y, Screen screen) {
		//screen.renderTile32(x * 32, y * 32, sprite.water);
	}
}
