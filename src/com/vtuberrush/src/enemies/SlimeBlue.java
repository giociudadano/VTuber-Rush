package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_BLUE;

import com.vtuberrush.src.managers.EnemyManager;

public class SlimeBlue extends Enemy {

	public SlimeBlue(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_BLUE, enemyManager);
	}

}
