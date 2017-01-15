package com.ptga123.Desbian.entity.particle;

import com.ptga123.Desbian.entity.Entity;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Water_Particle extends Entity
{
	private Sprite sprite;
	
		private int life;
		private int time = 0;
	
		protected double xx, yy, zz;
		protected double xa, ya, za;
	
		//Copier Ceci Pour D'autre Particule...
		public Water_Particle(int x, int y, int life)
		{
			this.x = x;
			this.y = y;
			this.xx = x;
			this.yy = y;
			this.life = life + (random.nextInt(35) - 10);
			sprite = Sprite.water_particle;
		
			this.xa = random.nextGaussian() + 1.0;
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
			if (Collision(x, y)) 
			{
				this.xa *= -0.5;
				this.ya *= -0.6;
				this.za *= -0.6;
			}
			this.xx += xa;
			this.yy += ya;
			this.zz += za;	
		}
	
		public boolean Collision(double x, double y)
		{
			boolean solid = false;
			for (int c = 0; c < 4; c++)
			{
				double xt = (x - c % 2 * 16) / 16;
				double yt = (y - c / 2 * 16) / 16;
				int ix = (int) Math.ceil(xt);
				int iy = (int) Math.ceil(yt);
				if (c % 2 == 0) ix = (int) Math.floor(xt);
				if (c / 2 == 0) iy = (int) Math.floor(yt);
				if (level.getTile(ix, iy).solid()) solid = true;
			}
		
			return solid;
		}

		public void render(Screen screen)
		{
			screen.renderSprite((int) xx, (int) yy - (int) zz - 1, sprite, true);
		}
}
