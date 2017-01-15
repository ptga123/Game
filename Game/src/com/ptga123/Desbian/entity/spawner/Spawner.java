package com.ptga123.Desbian.entity.spawner;

import com.ptga123.Desbian.entity.Entity;
import com.ptga123.Desbian.level.Level;

public abstract class Spawner extends Entity
//Garder Ceci Pour Des Sort :)
//level.add(new Spawner(8 * 16, 8 * 16, Spawner.Type.PARTICLE, 50, level));
{
	public enum Type {
		MOB, PARTICLE;
	}
	
	@SuppressWarnings("unused")
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level)
	{
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
}
