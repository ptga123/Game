package com.ptga123.Desbian.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.ptga123.Desbian.entity.Entity;
import com.ptga123.Desbian.entity.projectile.Fireball;
import com.ptga123.Desbian.entity.projectile.Lightningball;
import com.ptga123.Desbian.entity.projectile.Projectile;
import com.ptga123.Desbian.entity.projectile.Waterball;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.input.Mouse;

public abstract class Mob extends Entity {
	private boolean removed = false;
	protected boolean walking = false;
	public int health = 1;
	private int time = 0;

	protected List<Projectile> projectiles = new ArrayList<Projectile>();

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	protected Direction dir;

	public boolean ColideProjectile(int x, int y) {
		boolean colideProjectile = super.ColideProjectile(x, y);
		if (colideProjectile) {
			this.health = health - 1;
			if (health <= 0) {
				remove();
			}
		}
		return colideProjectile;
	}

	public boolean PlayerColideProjectile(int x, int y) {
		Player player;
		player = level.getClientPlayer();
		boolean colideProjectile = super.ColideProjectile(x, y);
		if (colideProjectile) {
			player.takeDamage(this);
		}
		return colideProjectile;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		// Remove from level
		return removed;
	}

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0)
			dir = Direction.RIGHT;
		if (xa < 0)
			dir = Direction.LEFT;
		if (ya > 0)
			dir = Direction.DOWN;
		if (ya < 0)
			dir = Direction.UP;

		for (int x = 0; x < Math.abs(xa); x++) {
			if (!collision(abs(xa), ya)) {
				this.x += abs(xa);
			}
		}
		for (int y = 0; y < Math.abs(ya); y++) {
			if (!collision(xa, abs(ya))) {
				this.y += abs(ya);
			}
		}
	}

	private int abs(double value) {
		if (value < 0)
			return -1;
		return 1;
	}

	public void Break(int xa, int ya) {
		if (breakable1(ya, ya)) {
			// remove;
		}

		if (!breakable1(xa, ya)) {
			x += xa;
			y += ya;
		}
	}

	// Update Method
	public void update() {
		time++;
		Player player;
		player = level.getClientPlayer();
		double px = player.getX();
		double py = player.getY();
		if ((x <= px + 32 && x >= px) && (y <= py + 32 && y >= py)) {
			player.takeDamage(this);
		} else if (x <= px + 32 && x >= px && y + 32 <= py + 32 && y + 32 >= py) {
			player.takeDamage(this);
		} else if (x + 32 <= px + 32 && x + 32 >= px && y <= py + 32 && y >= py) {
			player.takeDamage(this);
		} else if (x + 32 <= px + 32 && x + 32 >= px && y + 32 <= py + 32 && y + 32 >= py) {
			player.takeDamage(this);
		}
		if (Mouse.getButton() == 3) {
			player.takeMana(this);
		}

		if (Mouse.getButton() == 1) {
			player.takeMana(this);
		}
	}

	public abstract void render(Screen screen);

	protected void shootFire(double x, double y, double dir, Mob mob) {
		level.addShooter(mob);
		Projectile p = new Fireball(x, y, dir);
		level.add(p);
		//if (time % 30 == 0) {
		//	level.removeShooter(mob);
		//}
	}

	protected void shootWater(double x, double y, double dir, Mob mob) {
		level.addShooter(mob);
		Projectile p = new Waterball(x, y, dir);
		level.add(p);
	}

	protected void shootLightning(double x, double y, double dir) {
		// dir *= 180 / Math.PI;
		Projectile p = new Lightningball(x, y, dir);
		level.add(p);
	}

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 4 - 4) / 16;
			double yt = ((y + ya) - c / 2 * 4 + 4) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0)
				ix = (int) Math.floor(xt);
			if (c / 2 == 0)
				iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid())
				solid = true;
		}
		return solid;
	}

	/*
	 * private boolean fence_collision(int xa, int ya) { boolean fence_solid =
	 * false; for (int c = 0; c < 4; c++) { int xt = ((x + xa) + c % 2 * 3) /
	 * 16; int yt = ((y + ya) + c / 2 * 8) / 16; if (level.getTile(xt,
	 * yt).fence_solid()) fence_solid = true; }
	 * 
	 * return fence_solid; }
	 */

	private boolean breakable1(int xa, int ya) {
		boolean breakable = false;
		for (int c = 0; c < 4; c++) {
			int xt = (int) (((x + xa) + c % 2 * 12 - 6) / 16);
			int yt = (int) (((y + ya) + c / 2 * 14 + 2) / 16);
			if (level.getTile(xt, yt).breakable())
				breakable = true;
		}

		return breakable;
	}

}
