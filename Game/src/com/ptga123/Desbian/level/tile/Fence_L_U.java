package com.ptga123.Desbian.level.tile;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Fence_L_U extends Tile {

	public Fence_L_U(Sprite sprite) 
	{
		super(sprite);
	}
	
	@SuppressWarnings("static-access")
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, sprite.fence_l_u);
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
