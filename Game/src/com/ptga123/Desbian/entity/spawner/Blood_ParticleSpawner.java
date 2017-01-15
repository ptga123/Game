package com.ptga123.Desbian.entity.spawner;

import com.ptga123.Desbian.entity.particle.Blood2_Particle;
import com.ptga123.Desbian.entity.particle.Blood_Particle;
import com.ptga123.Desbian.entity.particle.Fire_Particle;
import com.ptga123.Desbian.level.Level;

public class Blood_ParticleSpawner extends Spawner
{
	@SuppressWarnings("unused")
	private int life;
	
	public Blood_ParticleSpawner(int x, int y, int life, int amount, Level level)
	{
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++)
		{
//Ici Pour RajoutŽ Des Couleurs
				level.add(new Blood_Particle(x, y, life));
				level.add(new Blood2_Particle(x, y, life));
		}
	}

}
