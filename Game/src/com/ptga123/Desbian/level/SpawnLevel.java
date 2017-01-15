package com.ptga123.Desbian.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ptga123.Desbian.entity.mob.AI_Template.AI_Chaser;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Mix;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_RandomMovement;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Shooter;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Star;
import com.ptga123.Desbian.input.Keyboard;

public class SpawnLevel extends Level {
	int x, y;

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! could not load level file!");
		}

		// Spawn
		// Testing-------------------------------------------------------------------------------------------
		for (int i = 0; i < 1; i++) {
			add(new AI_RandomMovement(8, 8));
			add(new AI_Mix(32, 17));
			add(new AI_Mix(47, 17));
			add(new AI_Mix(39, 12));
			add(new AI_Mix(40, 12));
			add(new AI_Chaser(32, 20));
			add(new AI_Chaser(47, 20));
			add(new AI_Chaser(24, 25));
			add(new AI_Chaser(55, 25));
			//add(new AI_Shooter(8, 8));
			//add(new AI_Star(40, 25));
		}
	}
}
