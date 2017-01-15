package com.ptga123.Desbian.graphics;

import java.util.Random;

import com.ptga123.Desbian.entity.mob.Mob;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Chaser;

public class Screen {
	public int width, height;
	public int[] pixels;
	public int[] Guipixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	public int xOffset, yOffset, xOffset32, yOffset32;

	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; // 50,400 pixels
		Guipixels = new int[width * height];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	// Clear Les Pixels
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int i = 0; i < Guipixels.length; i++) {
			Guipixels[i] = 0;
		}
	}

	// Sprite---------------------------------------------------------------------------------------
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];

			}
		}

	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean Follow_Camera) {
		if (Follow_Camera) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];

			}
		}

	}

	// GUI------------------------------------------------------------------------------------------
	public void renderGui8(int xp, int yp, Sprite sprite, boolean Follow_Camera) {
		if (Follow_Camera) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 8];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderGui16(int xp, int yp, Sprite sprite, boolean Follow_Camera) {
		if (Follow_Camera) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 16];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderGui64(int xp, int yp, Sprite sprite, boolean Follow_Camera) {
		if (Follow_Camera) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 64];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	// Tile------------------------------------------------------------------------------------------
	public void renderTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 16];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
				// pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}

	public void renderTile32(int xp, int yp, Sprite sprite) {
		xp -= xOffset32;
		yp -= yOffset32;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 32];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
				// pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}

	// Entity--------------------------------------------------------------------------------------
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 32];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;

			}
		}
	}

	public void renderMob64(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 64; y++) {
			int ya = y + yp;
			for (int x = 0; x < 64; x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = mob.getSprite().pixels[x + y * 64];
				if ((mob instanceof AI_Chaser) && col == 0xff5A00C4)
					col = 0xffBA0015;
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;

			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = mob.getSprite().pixels[x + y * 32];
				if ((mob instanceof AI_Chaser) && col == 0xff5A00C4)
					col = 0xffBA0015;
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;

			}
		}
	}

	public void renderMob(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 16; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 16];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;

			}
		}
	}

	public void fillRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		for (int y = 0; y < height; y++) {
			int yo = yp + y;
			if (yo < 0 || yo >= this.height)
				continue;
			for (int x = 0; x < width; x++) {
				int xo = xp + x;
				if (xo < 0 || xo >= this.width)
					continue;
				pixels[xo + yo * this.width] = color;
			}
		}
	}

	public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int x = xp; x < xp + width; x++) {
			if (x < 0 | x >= this.width || yp >= this.height)
				continue;
			if (yp > 0)
				pixels[x + yp * this.width] = color;
			if (yp + height >= this.height)
				continue;
			if (yp + height > 0)
				pixels[x + (yp + height) * this.width] = color;
		}
		for (int y = yp; y <= yp + height; y++) {
			if (xp >= this.width || y < 0 || y >= this.height)
				continue;
			if (xp > 0)
				pixels[xp + y * this.width] = color;
			if (xp + width >= this.width)
				continue;
			if (xp + width > 0)
				pixels[(xp + width) + y * this.width] = color;
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void setOffset32(int xOffset, int yOffset) {
		this.xOffset32 = xOffset * 2;
		this.yOffset32 = yOffset * 2;
	}

}
