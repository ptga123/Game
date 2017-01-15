package com.ptga123.Desbian.entity.spawner;

import com.ptga123.Desbian.entity.particle.Fire2_Particle;
import com.ptga123.Desbian.entity.particle.Fire_Particle;
import com.ptga123.Desbian.level.Level;

public class Fire_ParticleSpawner extends Spawner
{
	@SuppressWarnings("unused")
	private int life;
	
	public Fire_ParticleSpawner(int x, int y, int life, int amount, Level level)
	{
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++)
		{
//Ici Pour RajoutŽ Des Couleurs
				level.add(new Fire_Particle(x, y, life));
				level.add(new Fire2_Particle(x, y, life));
		}
	}

}
