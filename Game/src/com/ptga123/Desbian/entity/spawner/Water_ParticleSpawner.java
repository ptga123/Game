package com.ptga123.Desbian.entity.spawner;

import com.ptga123.Desbian.entity.particle.Water2_Particle;
import com.ptga123.Desbian.entity.particle.Water_Particle;
import com.ptga123.Desbian.level.Level;

public class Water_ParticleSpawner extends Spawner
{
	@SuppressWarnings("unused")
	private int life;
	
	public Water_ParticleSpawner(int x, int y, int life, int amount, Level level)
	{
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++)
		{
				//Ici Pour RajoutŽe Des Couleurs
				level.add(new Water_Particle(x, y, life));
				level.add(new Water2_Particle(x, y, life));
		}
	}
}
