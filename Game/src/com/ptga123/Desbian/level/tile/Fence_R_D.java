package com.ptga123.Desbian.level.tile;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Fence_R_D extends Tile {

	public Fence_R_D(Sprite sprite) 
	{
		super(sprite);
	}
	
	@SuppressWarnings("static-access")
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, sprite.fence_r_d);
	}
	
	public boolean solid()
	{
		return true;
	}
	
	/*public boolean fence_solid()
	{
		return true;
	}*/
}
