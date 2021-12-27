package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_RED;

import com.vtuberrush.src.managers.EnemyManager;

public class SlimeRed extends Enemy {

	public SlimeRed(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_RED, enemyManager);
	}

}
