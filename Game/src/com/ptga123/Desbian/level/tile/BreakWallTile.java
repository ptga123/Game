package com.ptga123.Desbian.level.tile;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class BreakWallTile extends Tile {

	public BreakWallTile(Sprite sprite) 
	{
		super(sprite);
	}
	
	@SuppressWarnings("static-access")
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, sprite.breakwall);
	}
	
	public boolean solid()
	{
		return true;
	}
	
	public boolean breakable()
	{
		return true;
	}
}
