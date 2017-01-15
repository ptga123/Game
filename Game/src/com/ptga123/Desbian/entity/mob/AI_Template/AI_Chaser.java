package com.ptga123.Desbian.entity.mob.AI_Template;

import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.graphics.AnimatedSprite;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.graphics.SpriteSheet;

public class AI_Chaser extends Mob {
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.gost_foward, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.gost_back, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.gost_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.gost_right, 32, 32, 3);

	private AnimatedSprite animSprite = up;

	private double xa = 0;
	private double ya = 0;
	private double speed = 1.5;

	public AI_Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.player_f;
		this.health = 20;
	}

	private void move() {
		xa = 0;
		ya = 0;
		Player player = level.getClientPlayer();
		double distance = Math
				.sqrt((x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));
		if (x < player.getX() + 2)
			xa++;
		if (x > player.getX() - 2)
			xa--;
		if (y < player.getY() + 2)
			ya++;
		if (y > player.getY() - 2)
			ya--;
		if (distance < 16 * 16) {
			if (xa != 0 || ya != 0) {
				move(xa * speed, ya * speed);
				walking = true;
			}
		} else {
			walking = false;
		}
	}

	public void update() {
		super.update();
		move();
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		}

		if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}

		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		}

		if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
