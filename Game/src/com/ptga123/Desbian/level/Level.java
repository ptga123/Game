package com.ptga123.Desbian.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.ptga123.Desbian.Game;
import com.ptga123.Desbian.entity.Entity;
import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.entity.particle.Fire_Particle;
import com.ptga123.Desbian.entity.projectile.Projectile;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.level.tile.Tile;
import com.ptga123.Desbian.net.player.NetPlayer;
import com.ptga123.Desbian.util.OpenSimplexNoise;
import com.ptga123.Desbian.util.Vector2i;

import core.RCDatabase;
import core.RCObject;

public class Level {
	public int width;
	public int height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;
	Game game;

	Random rand = new Random();
	int seed = rand.nextInt(1000000);;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Fire_Particle> fire_Particles = new ArrayList<Fire_Particle>();
	public List<Mob> players = new ArrayList<Mob>();
	private List<Mob> shooters = new ArrayList<Mob>();
	private List<Entity> entShooters = new ArrayList<Entity>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return +1;
			if (n1.fCost > n0.fCost)
				return -1;
			return 0;
		}
	};

	// private HashMap<PositionVector3i, Chunk> chunks;
	// chunks.get(Math.floor(x/20.0) -> null;

	public Entity entityColision(int x, int y) {
		for (Entity o : getEntities()) {

		}
		return null;
	}

	public void setTile(int x, int y, int tile) {
		// Chunk chunk = chunks.get(Math.flor..getClass().)
		// chunk.setTile(x % 20, y % 20);
		tiles[x + y * width] = tile;
	}

	public int getSeed() {
		return seed;
	}

	protected int generateInt(int max) {
		int random = rand.nextInt(max);
		return random;
	}

	private void load() {
		RCDatabase db = RCDatabase.DeserializeFromFile("res/data/save.bin");
		if (db != null) {
			RCObject obj = db.findObjects("Level");
			int seed = obj.findField("seed").getInt();
			this.seed = seed;
		}
	}

	protected void generateRandom() {
		OpenSimplexNoise noise = new OpenSimplexNoise(seed);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double value = noise.eval(x / 24.0, y / 24.0, 0.5);
				if (value < 0) {
					tiles[x + y * width] = Tile.col_gravel;
				} else if (value > 0) {
					tiles[x + y * width] = Tile.col_grass;
					if (generateInt(50) == 1 && value > 0) {
						tiles[x + y * width] = Tile.col_flower;
					} else if (generateInt(50) == 1 && value > 0) {
						tiles[x + y * width] = Tile.col_rock;
					}
				}
			}
		}
	}

	// public static Level SpawnLevel = new SpawnLevel
	// ("/Levels/SpawnLevel.png");
	public static Level spawn = new SpawnLevel("/Levels/Untitled.png");
	// public static Level generated = new GeneratedLevel(16, 16);

	public Level(int width, int height) {
		// tilesInt = new int[width * height];
		generateRandom();
		// generateLevel();
		System.out.println(seed);
		System.out.println(height);
	}

	// Level Method
	public Level(String path) {
		// load();
		loadLevel(path);
		generateRandom();
		System.out.println(seed);
		System.out.println(height);
		setTile(0, 0, Tile.col_wall);
	}

	// Auto Generate Level
	/*
	 * protected void generateLevel() { for (int y = 0; y < 64; y++) { for (int
	 * x = 0; x < 64; x++) { getTile(x, y); } } tile_size = 16; }
	 */

	// Load Level Method
	protected void loadLevel(String path) {
	}

	public synchronized List<Entity> getEntities() {
		return this.entities;
	}

	// Update Method
	public void update() {
		for (int i = 0; i < getEntities().size(); i++) {
			getEntities().get(i).update();
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}

		for (int i = 0; i < fire_Particles.size(); i++) {
			fire_Particles.get(i).update();
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}

		remove();
	}

	private void remove() {
		for (int i = 0; i < getEntities().size(); i++) {
			if (getEntities().get(i).isRemoved())
				getEntities().remove(i);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}

		for (int i = 0; i < fire_Particles.size(); i++) {
			if (fire_Particles.get(i).isRemoved())
				fire_Particles.remove(i);
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;

	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid())
				solid = true;
		}

		return solid;
	}

	public boolean tileBreakable(int x, int y, int size, int xOffset, int yOffset, Point P) {
		boolean breakable = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).breakable()) {
				breakable = true;
				P.x = xt;
				P.y = yt;
			}
		}

		return breakable;
	}

	// Render Method
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		screen.setOffset32(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

		for (int i = 0; i < getEntities().size(); i++) {
			getEntities().get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		for (int i = 0; i < fire_Particles.size(); i++) {
			fire_Particles.get(i).render(screen);
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	// Copy Des Truc Pour + De Particule
	public void add(Entity e) {
		e.init(this);
		int i = 0;
		if (e instanceof Fire_Particle) {
			fire_Particles.add((Fire_Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			i++;
			System.out.println(i);
			getEntities().add(e);
		}
	}

	public void addShooter(Mob mob) {
		mob.init(this);
		if (mob instanceof Player) {
			shooters.add(mob);
		} else {
			entShooters.add(mob);
		}
	}

	public void removeShooter(Mob mob) {
		mob.init(this);
		if (mob instanceof Player) {
			shooters.remove(mob);
		} else {
			entShooters.remove(mob);
		}
	}

	public void addPlayer(Mob player) {
		player.init(this);
		players.add(player);
	}

	public List<Mob> getPlayers() {
		return players;
	}

	public Mob getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return (Player) players.get(0);
	}

	public boolean mobCollision(int x, int y) {
		for (Entity e : getEntities()) {
			if (!entShooters.contains(e)) {
				if (e instanceof Mob) {
					if (e.ColideProjectile(x, y)) {
						return true;
					}
				}
			}
		}
		for (Mob m : players) {
			if (m instanceof Mob) {
				if (!shooters.contains(m)) {
					if (m.PlayerColideProjectile(x, y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 12; i++) {
				if (i == 6)
					continue;
				if (i == 7)
					continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i / 4) - 1;
				int yi = (i % 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				Tile al = getTile((x - 1) + xi, y + yi);
				if (at == null)
					continue;
				if (at.solid() || al.solid()) {
					continue;
				}
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost)
					continue;
				if (!vecInList(openList, a) || gCost < node.gCost)
					openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector))
				return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public List<Entity> getEntitiesRadius(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < getEntities().size(); i++) {
			Entity entity = getEntities().get(i);
			int x = entity.getX();
			int y = entity.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}

	public List<Mob> getPlayers(Entity e, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < players.size(); i++) {
			Mob mob = players.get(i);
			int x = mob.getX();
			int y = mob.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(mob);
		}
		return result;
	}

	public void removeNetPlayer(String username) {
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof NetPlayer && ((NetPlayer) e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		this.getEntities().remove(index);
	}

	private int getNetPlayerIndex(String username) {
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof NetPlayer && ((NetPlayer) e).getUsername().equals(username)) {
				break;
			}
		index++;
		}
		return index;
	}

	public void movePlayer(int x, int y, String username, boolean moving, int direction) {
		int index = getNetPlayerIndex(username);
		NetPlayer player = (NetPlayer) this.getEntities().get(index);
		player.x = x;
		player.y = y;
		player.setMoving(moving);
		player.setDirection(direction);
		System.out.println(username + " Moved to: " + x + ", " + y);
	}

	// GetTile Method
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		// Exemple Nouv Fa�ons: if (tiles[x + y * width] == Tile.col_grass)
		// return Tile.grass
		if (tiles[x + y * width] == Tile.col_grass)
			return Tile.grass;
		if (tiles[x + y * width] == Tile.col_flower)
			return Tile.flower;
		if (tiles[x + y * width] == Tile.col_rock)
			return Tile.rock;
		if (tiles[x + y * width] == Tile.col_fence_h)
			return Tile.fence_h;
		if (tiles[x + y * width] == Tile.col_fence_v)
			return Tile.fence_v;
		if (tiles[x + y * width] == Tile.col_fence_l_u)
			return Tile.fence_l_u;
		if (tiles[x + y * width] == Tile.col_fence_r_u)
			return Tile.fence_r_u;
		if (tiles[x + y * width] == Tile.col_fence_l_d)
			return Tile.fence_l_d;
		if (tiles[x + y * width] == Tile.col_fence_r_d)
			return Tile.fence_r_d;
		if (tiles[x + y * width] == Tile.col_target)
			return Tile.target;
		if (tiles[x + y * width] == Tile.col_wall)
			return Tile.wall;
		if (tiles[x + y * width] == Tile.col_breakwall)
			return Tile.breakwall;
		if (tiles[x + y * width] == Tile.col_gravel)
			return Tile.gravel;
		if (tiles[x + y * width] == Tile.col_wood)
			return Tile.wood;
		if (tiles[x + y * width] == Tile.col_water)
			return Tile.voidTile;
		return Tile.voidTile;
	}

}
