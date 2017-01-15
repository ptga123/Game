package com.ptga123.Desbian.level;

import com.ptga123.Desbian.entity.mob.AI_Template.AI_Chaser;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_Mix;
import com.ptga123.Desbian.entity.mob.AI_Template.AI_RandomMovement;

public class GeneratedLevel extends Level {
	int x, y;

	public GeneratedLevel(int width, int height) {
		super(width, height);
	}

	protected void loadLevel(int witdh, int height) {
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
		}
	}
}