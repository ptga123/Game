package com.ptga123.Desbian.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.ptga123.Desbian.gui.Gui;
import com.ptga123.Desbian.gui.Inventory;
import com.ptga123.Desbian.gui.Pvp;

public class Mouse implements MouseListener, MouseMotionListener
{
	public int W = 420 * 3;
	public int Y = 250 * 3;
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	private Gui gui;
	
	public Mouse(Gui gui)
	{
		this.gui = gui;
	}
	
	public static int getX()
	{
		return mouseX;
	}
	
	public static int getY()
	{
		return mouseY;
	}
	
	public static int getButton()
	{
		return mouseB;
	}
	
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent arg0) 
	{
		
	}

	public void mouseExited(MouseEvent arg0) 
	{
		
	}
	
	public void mousePressed(MouseEvent e) 
	{
		mouseB = e.getButton();
		for (Inventory b: gui.inventory)
		{
			boolean hasBeenClicked = b.Clicked(mouseX * 420 / W, mouseY * 250 / Y, mouseB);
			if (hasBeenClicked)
			{
				mouseB = -1;
				break;
			}
		}
		
		for (Pvp b: gui.pvp)
		{
			boolean hasBeenClicked = b.Clicked(mouseX * 420 / W, mouseY * 250 / Y, mouseB);
			if (hasBeenClicked)
			{
				mouseB = -1;
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		mouseB = -1;
	}

}
