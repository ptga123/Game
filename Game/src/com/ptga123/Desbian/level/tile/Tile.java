package com.ptga123.Desbian.level.tile;

import com.ptga123.Desbian.graphics.Screen;
import com.ptga123.Desbian.graphics.Sprite;

public class Tile 
{
	public int x, y;
	public Sprite sprite;
	//Create Tiles Here
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile fence_v = new Fence_H(Sprite.fence_v);
	public static Tile fence_h = new Fence_V(Sprite.fence_h);
	public static Tile fence_l_u = new Fence_L_U(Sprite.fence_l_u);
	public static Tile fence_r_u = new Fence_R_U(Sprite.fence_r_u);
	public static Tile fence_l_d = new Fence_L_D(Sprite.fence_l_d);
	public static Tile fence_r_d = new Fence_R_D(Sprite.fence_r_d);
	public static Tile target = new TargetTile(Sprite.target);
	public static Tile wall = new WallTile(Sprite.wall);
	public static Tile breakwall = new BreakWallTile(Sprite.breakwall);
	public static Tile gravel = new GravelTile(Sprite.gravel);
	public static Tile wood = new WoodTile(Sprite.wood);
	//public static Tile water = new WaterTile(Sprite.water);
	public static Tile voidTile = new voidTile(Sprite.voidSprite); 
	
	//CrŽation des HexdŽcimal i�i
	public final static int col_grass = 0xFFFFFFFF;
	public final static int col_flower = 0xFFB71546;
	public final static int col_rock = 0xFF745D5D;
	public final static int col_fence_v = 0xFF6148DD;
	public final static int col_fence_h = 0xFF6148CC;
	public final static int col_fence_l_u = 0xFF6148DC;
	public final static int col_fence_r_u = 0xFF6148CD;
	public final static int col_fence_l_d = 0xFF6148AA;
	public final static int col_fence_r_d = 0xFF6148AD;
	public final static int col_target = 0xFFDB0000;
	public final static int col_wall = 0xFF000000;
	public final static int col_breakwall = 0xFF726F3F;
	public final static int col_gravel = 0xFF00E400;
	public final static int col_wood = 0xFF00A100;
	public final static int col_water = 1;
	
	
	public Tile(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen)
	{
		
	}
	
	public boolean solid()
	{
		return false;
	}
	
	public boolean fence_solid()
	{
		return false;
	}

	public boolean breakable() 
	{
		return false;
	}
}