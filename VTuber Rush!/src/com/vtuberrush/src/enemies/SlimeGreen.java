package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_GREEN;

import com.vtuberrush.src.managers.EnemyManager;

public class SlimeGreen extends Enemy {

	public SlimeGreen(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_GREEN, enemyManager);
	}

}
