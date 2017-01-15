package com.ptga123.Desbian.graphics;

public class Sprite 
{

	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.Tile);
	public static Sprite flower = new Sprite(16, 0, 1, SpriteSheet.Tile);
	public static Sprite rock = new Sprite(16, 0, 2, SpriteSheet.Tile);
	public static Sprite fence_v = new Sprite(16, 0, 3, SpriteSheet.Tile);
	public static Sprite fence_h = new Sprite(16, 0, 4, SpriteSheet.Tile);
	public static Sprite fence_l_u = new Sprite(16, 0, 5, SpriteSheet.Tile);
	public static Sprite fence_r_u = new Sprite(16, 0, 6, SpriteSheet.Tile);
	public static Sprite fence_l_d = new Sprite(16, 0, 7, SpriteSheet.Tile);
	public static Sprite fence_r_d = new Sprite(16, 0, 8, SpriteSheet.Tile);
	public static Sprite target = new Sprite(16, 0, 9, SpriteSheet.Tile);
	public static Sprite wall = new Sprite(16, 1, 0, SpriteSheet.Tile);
	public static Sprite breakwall = new Sprite(16, 1, 1, SpriteSheet.Tile);
	public static Sprite gravel = new Sprite(16, 2, 0, SpriteSheet.Tile);
	public static Sprite wood = new Sprite(16, 2, 1, SpriteSheet.Tile);
	//public static Sprite water = new Sprite(32, 0, 5, SpriteSheet.Water);
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);
	
	//Sprite De GUI
	
	//Sprite De Projectile
	public static Sprite lightning = new Sprite(16, 2, 0, SpriteSheet.Projectile);
	public static Sprite fireball = new Sprite(16, 0, 0, SpriteSheet.Projectile);
	public static Sprite waterball = new Sprite(16, 1, 0, SpriteSheet.Projectile);
	
	//Sprite De Particule
	public static Sprite fire_particle = new Sprite(3, 0xC80A0A);
	public static Sprite fire2_particle = new Sprite(3, 0xFF0000);
	public static Sprite water_particle = new Sprite(3, 0x1B32E0);
	public static Sprite water2_particle = new Sprite(3, 0x091EB8);
	public static Sprite blood_particle = new Sprite(3, 0xA71A1A);
	public static Sprite blood2_particle = new Sprite(3, 0x8A0707);
	
	//Sprite De Mob
	public static Sprite Princess_f = new Sprite(16, 1, 0, SpriteSheet.Mob);
	public static Sprite vestal = new Sprite(64, 0, 0, SpriteSheet.Vestal);
	
	//Sprite De Joueur
	public static Sprite player_f = new Sprite(32, 0, 0, SpriteSheet.Character);
	public static Sprite player_b = new Sprite(32, 2, 0, SpriteSheet.Character);
	public static Sprite player_l = new Sprite(32, 1, 0, SpriteSheet.Character);
	public static Sprite player_r = new Sprite(32, 3, 0, SpriteSheet.Character);
	
	protected Sprite(SpriteSheet sheet, int width, int height)
	{
	    SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int size, int x, int y, SpriteSheet sheet)
	{
		SIZE = size;
	 	this.width = width;
	 	this.height = height;
	 	pixels = new int[width * height];
	 	this.x = x * size;
	 	this.y = y * size;
	 	this.sheet = sheet;
	 	load();
	}
	
	public Sprite(int width, int height, int coulor)
	{
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColour(coulor);
	}
	
	public Sprite(int size, int colour) 
	{
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}


	public Sprite(int[] Pixels, int width, int height) 
	{
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = Pixels;
	}

	private void setColour(int colour) 
	{
		for (int i = 0; i < width * height; i++)
		{
			pixels[i] = colour;
		}
	}
	
	public int getWidth()
	{
		return width;
		
	}
	
	public int getHeight()
	{
		return height;
	}

	private void load() 
	{
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}	
}
