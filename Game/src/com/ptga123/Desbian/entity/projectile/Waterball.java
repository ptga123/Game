package com.ptga123.Desbian.entity.projectile;

import java.awt.Point;

import com.ptga123.Desbian.entity.spawner.Blood_ParticleSpawner;
import com.ptga123.Desbian.entity.spawner.Water_ParticleSpawner;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.level.tile.Tile;

public class Waterball extends Projectile 
{
	public static int FIRE_RATE = 1;
	
	public Waterball(double x, double y, double dir) 
	{
		super(x, y, dir);
		range = 200;
		speed = 1 * 3;
		damage = random.nextInt(10) + 20;
		sprite = Sprite.waterball;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update()
	{
		if (level.mobCollision((int) x,(int) y))
		{
			level.add(new Blood_ParticleSpawner ((int) x, (int) y, 44, 50, level));
			remove();
		}
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 7, 4, 4)) 
		{
			level.add(new Water_ParticleSpawner ((int) x, (int) y, 44, 50, level));//50
			remove();
		}
		
		Point P = new Point();
		
			if (level.tileBreakable((int) (x + nx), (int) (y + ny), 7, 4, 4, P)) 
			{
				//Besoin d'une fa�ons de change le sprite:(
				if (x < 0 || y < 0 || x >= level.width * 16 || y >= level.height * 16)
				{
				
					
				}else 
				{
					level.setTile((int) P.x, (int) P.y, Tile.col_grass);
				}
			}
		move();
	}
	
	protected void move()
	{
		x += nx;
		y += ny;		
		if (distance() > range) remove();
	}
	
	private double distance()
	{
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen)
	{
		screen.renderTile((int) x - 12, (int) y - 3, sprite);
	}
}
