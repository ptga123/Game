package com.ptga123.Desbian.entity.mob.AI_Template;

import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.graphics.AnimatedSprite;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.graphics.SpriteSheet;

public class AI_Mix extends Mob {
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_foward, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_back, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = up;

	private int xa = 0;
	private int ya = 0;

	public AI_Mix(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.player_f;
	}

	private void move() {
		xa = 0;
		ya = 0;

		Player player = level.getClientPlayer();
		double distance = Math
				.sqrt((x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));
		if (x < player.getX())
			xa++;
		if (x > player.getX())
			xa--;
		if (y < player.getY())
			ya++;
		if (y > player.getY())
			ya--;
		if (xa != 0 || ya != 0) {
			if (distance < 8 * 16) {
				move(xa, ya);
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
