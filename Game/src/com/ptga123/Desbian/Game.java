package com.ptga123.Desbian;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Star;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.gui.Gui;
import com.ptga123.Desbian.gui.Menu;
import com.ptga123.Desbian.input.Keyboard;
import com.ptga123.Desbian.input.Mouse;
import com.ptga123.Desbian.level.Level;
import com.ptga123.Desbian.level.Node;
import com.ptga123.Desbian.level.TileCoordinate;
import com.ptga123.Desbian.net.Client;
import com.ptga123.Desbian.sound.Music;
import com.ptga123.Desbian.util.Vector2i;

import core.RCArray;
import core.RCDatabase;
import core.RCField;
import core.RCObject;
import core.RCString;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	// Public Variable
	public int W = 420;
	public int Y = 250;
	private static final int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;// 420
	private static final int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;// 250
	private final static byte PACKET_TYPE_DISCONNECT = 0x1;
	private static int scale = 2;
	public static String title = "Pre-Alpha 0.1";
	private Thread thread;
	JFrame frame;
	public Client client;
	private Keyboard key;
	public WindowHandler windowHandler;
	public Level level;
	private Menu menu;
	public Player player;
	public AI_Star star; // here
	private Gui gui;
	private Boolean running = false;
	private int seed;
	public Screen screen;

	Random rand = new Random();
	int playerSpawnRand = rand.nextInt(250);

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	// Main Method
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();

		// RCDatabase db = RCDatabase.DeserializeFromFile("res/data/save.bin");
		// client.send(db);

		windowHandler = new WindowHandler(this);
		level = Level.spawn;
		menu = Menu.MainMenu;
		TileCoordinate playerSpawn = new TileCoordinate(120, 115);// 8,8
		player = new Player(playerSpawn.x(), playerSpawn.y(), JOptionPane.showInputDialog(null, "Enter Username"), key,
				this);
		System.out.println(player.getUsername());
		load();
		level.add(star = new AI_Star(40, 25));
		level.add(player);
		menu.init(menu);
		gui = new Gui(this, player, key);
		// new Music("Sounds/Second Music.wav", true);

		addKeyListener(key);

		Mouse mouse = new Mouse(gui);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		client = new Client(this, "localhost", 8192);
		if (!client.connect()) {

		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				client.disconnect();
				save();
			}
		});
	}

	private void save() {
		RCDatabase db = new RCDatabase("Save");

		RCObject pla = new RCObject("Player");
		pla.addField(RCField.Integer("x", player.getX()));
		pla.addField(RCField.Integer("y", player.getY()));
		pla.addString(RCString.Create("username", player.getUsername()));
		RCObject lvl = new RCObject("Level");
		lvl.addField(RCField.Integer("seed", level.getSeed()));
		db.addObject(pla);
		db.addObject(lvl);

		db.serializeToFile("res/data/save.bin");
	}

	private void load() {
		RCDatabase db = RCDatabase.DeserializeFromFile("res/data/save.bin");
		if (db != null) {
			RCObject pla = db.findObjects("Player");
			int x = pla.findField("x").getInt();
			int y = pla.findField("y").getInt();
			String username = pla.findString("username").getString();
			player.setX(x);
			player.setY(y);
			// player.setUsername(username);
		}
	}

	public void disconnect() {
		client.sendPacket(PACKET_TYPE_DISCONNECT);
	}

	public int getSeed() {
		return seed;
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public int getXScroll() {
		int xScroll = (int) player.getX() - screen.width / 2;
		return xScroll;
	}

	public int getYScroll() {
		int yScroll = (int) player.getY() - screen.height / 2;
		return yScroll;
	}

	public double xScroll() {
		double xScroll = player.getX() - screen.width / 2;
		xScroll = ((Mouse.getX() / 32) + ((int) Math.floor(xScroll / 16)));
		return xScroll;
	}

	public double yScroll() {
		double yScroll = player.getY() - screen.height / 2;
		yScroll = ((Mouse.getY() / 32) + ((int) Math.floor(yScroll / 16)));
		return yScroll;
	}

	public TileCoordinate getMouseTile() {
		double xScroll = player.getX() - screen.width / 2;
		xScroll = ((Mouse.getX() / 2) + ((int) xScroll));
		double yScroll = player.getY() - screen.height / 2;
		yScroll = ((Mouse.getY() / 2) + ((int) yScroll));
		TileCoordinate tile = new TileCoordinate((int) xScroll / 16, (int) yScroll / 16);
		return tile;
	}

	// Start Method
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	// Stop Method
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Run Method
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
		System.out.println("Crashed D:");
	}

	// Update
	public void update() {
		key.update();
		level.update();
	}

	// Render
	public void render() {
		// Buffer
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);
		menu.render((int) xScroll, (int) yScroll, screen);
		gui.render(screen);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		// Graphics
		Graphics g = bs.getDrawGraphics();
		// C'est Ceci Qui Render Tout
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.drawString("seed:" + level.getSeed(), 32, 32);
		g.drawString("X:" + getMouseTile().x() / 16 + "Y:" + getMouseTile().y() / 16, 32, 64);
		g.drawString("Player position X: " + ((int) player.getX() / 16) + " Y: " + ((int) player.getY() / 16), 32, 96);
		g.drawString("Star position X: " + ((int) star.getX() / 16) + " Y: " + ((int) star.getY() / 16), 32, 140);
		g.dispose();
		bs.show();
	}

	static class SubMainMenu {
		JFrame menuframe;
		Game game;

		public SubMainMenu() {
			ImageIcon img = new ImageIcon(Game.class.getResource("/textures/Sheets/MainMenu.png"));
			JLabel picLabel = new JLabel(img);
			menuframe = new JFrame();
			menuframe.getContentPane().add(picLabel);
			menuframe.getContentPane().setPreferredSize(new Dimension(1260, 750));
			menuframe.getContentPane().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					/*
					 * if (e.getX() > 840 && e.getX() < 1170 && e.getY() > 180
					 * && e.getY() < 260) { start(); }
					 */

					if (e.getX() > 1 && e.getX() < 630 && e.getY() > 1 && e.getY() < 750) {
						start();
						// loadNew = false;
					}
					if (e.getX() > 630 && e.getX() < 1260 && e.getY() > 1 && e.getY() < 750) {
						start();
						// loadNew = true;
					}
				}
			});
			menuframe.pack();
			System.out.println(img);
			menuframe.setVisible(true);
		}

		public void start() {
			menuframe.setVisible(false);
			Game game = new Game();
			game.frame.setResizable(true);
			game.frame.setTitle("game.title");
			game.frame.setUndecorated(true);
			// game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			game.frame.add(game);
			game.frame.pack();
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setLocationRelativeTo(null);
			game.frame.setVisible(true);
			game.start();
		}
	}

	public static void main(String[] args) {
		// SubMainMenu Menu = new SubMainMenu();

		Game game = new Game();
		game.frame.setResizable(true);
		game.frame.setTitle("game.title");
		game.frame.setUndecorated(true);
		// game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();

	}
}
