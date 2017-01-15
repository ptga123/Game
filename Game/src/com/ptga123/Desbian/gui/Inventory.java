package com.ptga123.Desbian.gui;

import java.util.ArrayList;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.graphics.SpriteSheet;
import com.ptga123.Desbian.input.Keyboard;

public class Inventory 
{
	int x, y;
	Sprite sprite;
	Keyboard keyboard;
	public ArrayList<Inventoryp> inventoryp = new ArrayList<Inventoryp>();

	public Inventory(int x, int y, Sprite sprite, Keyboard keyboard)
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
			keyboard.inventory = !keyboard.inventory;
			inventoryp.add(new Inventoryp(0, 0, new Sprite(64, 0, 0, SpriteSheet.Inventory), keyboard));
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
