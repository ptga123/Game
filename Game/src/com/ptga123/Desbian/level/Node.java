package com.ptga123.Desbian.level;

import com.ptga123.Desbian.util.Vector2i;

public class Node {

	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;

	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
	public int getTileX(){
		return this.tile.getX();
	}
	
	public int getTileY(){
		return this.tile.getY();
	}

}
