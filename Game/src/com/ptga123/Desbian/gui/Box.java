package com.ptga123.Desbian.gui;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.input.Keyboard;

public class Box 
{
	int x, y;
	Sprite sprite;
	public Keyboard keyboard;

	public Box(int x, int y, Sprite sprite, Keyboard keyboard)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.keyboard = keyboard;
	}
	
	public void render(Screen screen)
	{
		if (keyboard.buildmode)
		{
			screen.renderGui16(x, y, sprite, true);
		}
	}
}
