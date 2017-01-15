package com.ptga123.Desbian.entity.mob.AI_Template;

import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class AI_Shooter extends Mob {

	public AI_Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.vestal;
		this.health = 5000;
	}

	public void update() {
		Player p = level.getClientPlayer();
		double dx = p.getX() - x;
		double dy = p.getY() - y;
		double dir = Math.atan2(dy, dx);
		shootFire(x + 16, y + 16, dir, this);
	}

	public void render(Screen screen) {
		screen.renderMob64(x, y, this);
	}

}
