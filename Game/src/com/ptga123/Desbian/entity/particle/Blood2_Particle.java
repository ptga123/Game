package com.ptga123.Desbian.entity.particle;

import com.ptga123.Desbian.entity.Entity;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Blood2_Particle extends Entity
{
	private Sprite sprite;
	
	private int life;
	private int time = 0;
	
	protected double xx, yy, zz;
	protected double xa, ya, za;
	
	//Copier Ceci Pour D'autre Particule...
	public Blood2_Particle(int x, int y, int life)
	{
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(35) - 10);
		sprite = Sprite.blood2_particle;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	public void update()
	{
		time++;
		if (time >= 7400) time = 0;
		if (time > life) remove();
		za -= 0.1;
		
		if (zz < 0) 
		{
			zz = 0;
			za *= -0.6;
			xa *= 0.4;
			ya *= 0.6;
		}
		move(xx + xa, (yy + ya) + (zz + za));	
	}
	
	private void move(double x, double y) 
	{
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	public void render(Screen screen)
	{
		screen.renderSprite((int) xx, (int) yy - (int) zz - 1, sprite, true);
	}
}