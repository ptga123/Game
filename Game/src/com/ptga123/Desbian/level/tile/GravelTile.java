package com.ptga123.Desbian.level.tile;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class GravelTile extends Tile{

	public GravelTile(Sprite sprite) {
		super(sprite);
	}
	
	@SuppressWarnings("static-access")
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, sprite.gravel);
	}

}
