package com.ptga123.Desbian.entity.projectile;

import com.ptga123.Desbian.entity.spawner.Water_ParticleSpawner;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Lightningball extends Projectile 
{
public static int FIRE_RATE = 1;
	
	public Lightningball(double x, double y, double dir) 
	{
		super(x, y, dir);
		range = 200;
		speed = 3;
		damage = random.nextInt(10) + 20;
		sprite = Sprite.lightning;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update()
	{
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 7, 4, 4)) 
		{
			level.add(new Water_ParticleSpawner ((int) x, (int) y, 44, 50, level));//50
			remove();
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
