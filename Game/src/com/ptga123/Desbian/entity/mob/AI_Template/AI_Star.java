package com.ptga123.Desbian.entity.mob.AI_Template;

import com.ptga123.Desbian.entity.mob.Player;

import java.util.List;

import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.graphics.AnimatedSprite;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.graphics.SpriteSheet;
import com.ptga123.Desbian.level.Node;
import com.ptga123.Desbian.level.tile.Tile;
import com.ptga123.Desbian.util.Debug;
import com.ptga123.Desbian.util.Vector2i;

public class AI_Star extends Mob {
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.gost_foward, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.gost_back, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.gost_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.gost_right, 32, 32, 3);

	private AnimatedSprite animSprite = up;

	private double xa = 0;
	private double ya = 0;
	private List<Node> path = null;
	private double speed = 1.5;
	private int time = 0;
	public int nextX, nextY; // here
	private int ox, oy;

	public AI_Star(int x, int y) {
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
		int px = level.getPlayerAt(0).getX();
		int py = level.getPlayerAt(0).getY() + 5;
		Vector2i start = new Vector2i(getX() >> 4, (getY() + 5) >> 4);
		Vector2i destination = new Vector2i(px >> 4, py >> 4);
		this.ox = (destination.getX() * 16) - 16;
		this.oy = destination.getY() * 16;
		Vector2i newDest = new Vector2i(destination.getX() + 1, destination.getY());
		Tile pos = level.getTile(ox / 16, oy / 16);
		if (distance <= 16 * 16) {
			if (time % 3 == 0) {
				if (!pos.solid()) {
					path = level.findPath(start, destination);
				}
				if (pos.solid()) {
					path = level.findPath(start, newDest);
				}
			}
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					this.nextX = vec.getX();
					this.nextY = vec.getY();
					if (x < vec.getX() << 4)
						xa++;
					if (x > vec.getX() << 4)
						xa--;
					if (y < vec.getY() << 4)
						ya++;
					if (y > vec.getY() << 4)
						ya--;
				}
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;

		} else {
			walking = false;
		}
	}

	public void update() {
		time++;
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
		Debug.drawRect(screen, nextX << 4, nextY << 4, 16, 16, 0xffff00, true);
		Debug.drawRect(screen, (nextX << 4) - 16, (nextY << 4), 16, 16, 0xffff00, true);
		Debug.drawRect(screen, x - 16, y - 16, 32, 32, true);
		Debug.drawRect(screen, ox, oy, 16, 16, 0xffff00, true);
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
