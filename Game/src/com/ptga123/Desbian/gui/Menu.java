package com.ptga123.Desbian.gui;

import java.util.ArrayList;
import java.util.List;

import com.ptga123.Desbian.graphics.Screen;

public class Menu 
{
	@SuppressWarnings("unused")
	private Menu menu;
	public int[] pixels;
	private int height;
	private int width;
	
	private List<Menu> MenuList = new ArrayList<Menu>();
	
	public static Menu MainMenu = new Menu ("/Menu/MainMenu.png");
	
	public Menu(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public Menu(String path)
	{
		loadMenu(path);
	}
	
	private void loadMenu(String path) 
	{
		
	}
	
	public void update() 
	{
		menu = Menu.MainMenu;
		for (int i = 0 ; i < MenuList.size(); i++)
		{
			MenuList.get(i).update();
		}
	}
	
	public void render(int xScroll, int yScroll, Screen screen)
	{
		//screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++)
		{
			for (int x = x0; x < x1; x++)
			{
				
			}
		}
		menu = Menu.MainMenu;
		for (int i = 0 ; i < MenuList.size(); i++)
		{
			MenuList.get(i).render(xScroll, yScroll, screen);
		}
	}
	
	public Menu getMenu(int x, int y)
	{
		if (x < 0 || y < 0 || x >= width || y >= height) return Menu.MainMenu;
		return Menu.MainMenu;
	}
	
	public void init(Menu menu)
	{
		this.menu = menu;
	}
}
