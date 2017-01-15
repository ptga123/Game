package com.ptga123.Desbian.gui;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Health 
{
	int x, y;
	Sprite sprite;

	public Health(int x, int y, Sprite sprite)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public boolean Clicked(int mx, int my, int MouseButton)
	{
		if (MouseButton == 1)
		{
			if (x < mx && x + sprite.getWidth() > mx &&	y < my && y + sprite.getHeight() > my)
			{
			System.out.println("Hello World!!!");
				return true;
			}
		}
		return false;
	}
	
	public void render(Screen screen)
	{
		screen.renderGui16(x, y, sprite, false);
	}
}
