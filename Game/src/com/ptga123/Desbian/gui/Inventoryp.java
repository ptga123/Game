package com.ptga123.Desbian.gui;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.input.Keyboard;

public class Inventoryp 
{
	int x, y;
	Sprite sprite;
	public Keyboard keyboard;

	public Inventoryp(int x, int y, Sprite sprite, Keyboard keyboard)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.keyboard = keyboard;
	}
	
	public boolean Clicked(int mx, int my, int MouseButton)
	{
		if (MouseButton == 1)
		{
			if (x < mx && x + sprite.getWidth() > mx &&	y < my && y + sprite.getHeight() > my)
			{
			System.out.println("Inventory :D");
			return true;
			}
		}
		return false;
	}
	
	public void render(Screen screen)
	{
		if (keyboard.inventory)
		{
			screen.renderGui64(x, y, sprite, false);
		}
	}
}
