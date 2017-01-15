package com.ptga123.Desbian.entity.mob;

import com.ptga123.Desbian.Game;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Chaser;
import com.ptga123.Desbian.entity.projectile.Fireball;
import com.ptga123.Desbian.entity.projectile.Lightningball;
import com.ptga123.Desbian.entity.projectile.Projectile;
import com.ptga123.Desbian.entity.projectile.Waterball;
import com.ptga123.Desbian.entity.spawner.Blood_ParticleSpawner;
import com.ptga123.Desbian.entity.spawner.Fire_ParticleSpawner;
import com.ptga123.Desbian.graphics.AnimatedSprite;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.graphics.SpriteSheet;
import com.ptga123.Desbian.gui.Gui;
import com.ptga123.Desbian.input.Keyboard;
import com.ptga123.Desbian.input.Mouse;
import com.ptga123.Desbian.level.SpawnLevel;
import com.ptga123.Desbian.level.TileCoordinate;
import com.ptga123.Desbian.level.tile.Tile;
import com.ptga123.Desbian.util.Debug;

public class Player extends Mob {
	private Keyboard input;
	private Sprite sprite;
	private Gui gui;
	private long lastTimehit = 0;
	private long lastTimeTele = 0;
	private int anim = 0;
	public int map = 0;
	public int health = 10;
	public static int mana = 10;
	protected double speed;
	protected double angle;
	protected double nx, ny;
	private String username;
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_foward, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_back, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	private int movingDir = 1;

	private final static byte PACKET_TYPE_MOVE = 0x2;

	private Game game;

	private AnimatedSprite animSprite = down;

	private int fireRate = 0;

	public void takeDamage(Mob mob) {
		if (System.currentTimeMillis() > lastTimehit + 1500) {
			System.out.println("rip");
			this.health -= 1;
			if (health <= 10) {
				level.add(new Blood_ParticleSpawner((int) x, (int) y, 44, 15, level));
			}
			if (health == 0) {
				level.add(new Blood_ParticleSpawner((int) x, (int) y, 44, 50, level));
				this.x = 8 * 16;
				this.y = 8 * 16;
				this.health = 10;
			}
			this.lastTimehit = System.currentTimeMillis();
		}
	}

	public void takeMana(Mob mob) {
		if (System.currentTimeMillis() > lastTimehit + 750) {
			this.mana -= 1;
			this.lastTimehit = System.currentTimeMillis();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHealth() {
		return health;
	}

	public int getMana() {
		return mana;
	}

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_b;
	}

	public Player(int x, int y, String username, Keyboard input, Game game) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.game = game;
		this.username = username;
		sprite = Sprite.player_f;
		fireRate = Fireball.FIRE_RATE;
		fireRate = Waterball.FIRE_RATE;
	}

	public void update() {
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);
		if (fireRate > 0)
			fireRate--;
		double xa = 0, ya = 0;
		double speed = 1;
		double sprint = 2;
		if (anim < 7500)
			anim++;
		else
			anim = 0;
		// Normal
		if (input.help) {

		}
		// Admin Toll's

		if (input.hadd) {
			this.health = 10;
		}

		if (input.hmin) {
			this.mana = 10;
		}
		// Movement
		if (input.wasd) {
			if (input.up) {
				animSprite = up;
				ya -= speed;
				movingDir = 1;
			}
			if (input.down) {
				animSprite = down;
				ya += speed;
				movingDir = 2;
			}
			if (input.left) {
				animSprite = left;
				xa -= speed;
				movingDir = 3;
			}
			if (input.right) {
				animSprite = right;
				xa += speed;
				movingDir = 4;
			}
		}

		// Teleport
		if (System.currentTimeMillis() > lastTimeTele + 250) {
			if (input.teleup) {
				animSprite = up;
				setY((int) getY() - 10 * 16);
			}
			if (input.teledown) {
				animSprite = down;
				setY((int) getY() + 10 * 16);
			}
			if (input.teleleft) {
				animSprite = left;
				setX((int) getX() - 10 * 16);
			}
			if (input.teleright) {
				animSprite = right;
				setX((int) getX() + 10 * 16);
			}
			this.lastTimeTele = System.currentTimeMillis();
		}

		// Sprint
		if (input.sprint) {
			if (input.up) {
				animSprite = up;
				ya -= sprint;
				movingDir = 1;
			}

			if (input.down) {
				animSprite = down;
				ya += sprint;
				movingDir = 2;
			}

			if (input.left) {
				animSprite = left;
				xa -= sprint;
				movingDir = 3;
			}

			if (input.right) {
				animSprite = right;
				xa += sprint;
				movingDir = 4;
			}
		}

		if (input.respawn) {
			setX(8 * 16);
			setY(8 * 16);
		}

		if (input.buildmode) {
			if (input.place1) {
				level.setTile(game.getMouseTile().x() / 16, game.getMouseTile().y() / 16, Tile.col_wall);
			}
			if (input.place2) {
				level.setTile(game.getMouseTile().x() / 16, game.getMouseTile().y() / 16, Tile.col_water);
			}
			if (input.place3) {
				level.add(new AI_Chaser(game.getMouseTile().x() / 16, game.getMouseTile().y() / 16));
			}
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
			game.client.sendMovePacket(PACKET_TYPE_MOVE, walking, movingDir, x, y);
		} else {
			walking = false;
		}

		clear();
		updateShooting();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved())
				level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		// Changer Button I�i
		if (this.mana >= 1) {
			if (Mouse.getButton() == 1 && fireRate <= 0) {
				double dx = Mouse.getX() - Game.getWindowWidth() / 2;
				double dy = Mouse.getY() - Game.getWindowHeight() / 2;
				double dir = Math.atan2(dy, dx);
				shootFire(x, y, dir, this);
				fireRate = Fireball.FIRE_RATE;
			}
		}

		if (this.mana >= 1) {
			if (Mouse.getButton() == 3 && fireRate <= 0) {
				double dx = Mouse.getX() - Game.getWindowWidth() / 2;
				double dy = Mouse.getY() - Game.getWindowHeight() / 2;
				double dir = Math.atan2(dy, dx);
				shootWater(x, y, dir, this);
				fireRate = Waterball.FIRE_RATE;
			}
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderPlayer((int) (x - 16), (int) (y - 16), sprite);
	}

}