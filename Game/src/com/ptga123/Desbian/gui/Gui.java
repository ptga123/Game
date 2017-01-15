package com.ptga123.Desbian.gui;

import java.util.ArrayList;

import com.ptga123.Desbian.Game;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;
import com.ptga123.Desbian.graphics.SpriteSheet;
import com.ptga123.Desbian.input.Keyboard;
import com.ptga123.Desbian.input.Mouse;
import com.ptga123.Desbian.level.TileCoordinate;

public class Gui {
	@SuppressWarnings("unused")
	private Player player;
	private Game game;
	public Keyboard keyboard;
	@SuppressWarnings("unused")
	private SpriteSheet spritesheet;

	public ArrayList<Inventory> inventory = new ArrayList<Inventory>();
	public ArrayList<Pvp> pvp = new ArrayList<Pvp>();
	public ArrayList<Inventoryp> inventoryp = new ArrayList<Inventoryp>();
	public ArrayList<Health> health = new ArrayList<Health>();
	public ArrayList<Mana> mana = new ArrayList<Mana>();
	public ArrayList<Box> box = new ArrayList<Box>();

	public Gui(Game game, Player player, Keyboard keyboard) {
		this.game = game;

		inventory.add(new Inventory(25, 225, new Sprite(16, 1, 0, SpriteSheet.Gui), keyboard));
		// box.add(new Box((int) player.getX(), (int) player.getY(), new
		// Sprite(16, 1, 2, SpriteSheet.Gui), keyboard));
		inventoryp.add(new Inventoryp(100, 100, new Sprite(64, 0, 0, SpriteSheet.Inventory), keyboard));

		pvp.add(new Pvp(45, 225, new Sprite(16, 3, 0, SpriteSheet.Gui)));
		// Health---------------------------------------------------------------------------------------------
		// health.add(new Health(150, 225, new Sprite(16, 0, 1,
		// SpriteSheet.Gui)));
		// health.add(new Health(116, 225, new Sprite(16, 1, 1,
		// SpriteSheet.Gui)));
		// Mana-----------------------------------------------------------------------------------------------
		// mana.add(new Mana(100, 233, new Sprite(8, 0, 1, SpriteSheet.Gui)));
		// mana.add(new Mana(108, 233, new Sprite(8, 1, 1, SpriteSheet.Gui)));
		this.keyboard = keyboard;
		this.player = player;
		spritesheet = SpriteSheet.Gui;
	}

	public void render(Screen screen) {
		int playerHealth = player.getHealth();
		int playerMana = player.getMana();
		int xScroll = (int) (player.getX() - screen.width / 2);
		int boxX = ((Mouse.getX() / 2) + ((int) xScroll));
		int yScroll = (int) (player.getY() - screen.height / 2);
		int boxY = ((Mouse.getY() / 2) + ((int) yScroll));
		TileCoordinate Box = new TileCoordinate(boxX / 16, boxY / 16);
		Sprite heartSprite = new Sprite(16, 0, 1, SpriteSheet.Gui);
		Sprite manaSprite = new Sprite(8, 0, 1, SpriteSheet.Gui);
		Sprite boxSprite = new Sprite(16, 1, 2, SpriteSheet.Gui);

		if (keyboard.buildmode) {
			screen.renderGui16(game.getMouseTile().x(), game.getMouseTile().y(), boxSprite, true);
		}

		for (int i = 0; i < playerHealth; i++) {
			screen.renderGui16(150 + i * 8, 220, heartSprite, false);
		}

		for (int i = 0; i < playerMana; i++) {
			screen.renderGui8(150 + i * 8, 225, manaSprite, false);
		}

		for (Inventoryp i : inventoryp) {
			i.render(screen);
		}

		for (Box i : box) {
			i.render(screen);
		}

		for (Inventory i : inventory) {
			i.render(screen);
		}

		for (Pvp i : pvp) {
			i.render(screen);
		}

		for (Health i : health) {
			i.render(screen);
		}

		for (Mana i : mana) {
			i.render(screen);
		}
	}
}
