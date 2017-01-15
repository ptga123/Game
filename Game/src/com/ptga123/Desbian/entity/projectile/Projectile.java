package com.ptga123.Desbian.entity.projectile;

import java.util.Random;

import com.ptga123.Desbian.entity.Entity;
import com.ptga123.Desbian.graphics.Sprite;

public abstract class Projectile extends Entity 
{
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;

	protected final Random ramdom = new Random();
	
	public Projectile(double x, double y, double dir)
	{
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	protected void move()
	{
		
	}
	
}
