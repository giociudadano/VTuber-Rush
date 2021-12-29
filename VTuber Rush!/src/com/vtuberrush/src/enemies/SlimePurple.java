package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_PURPLE;

import com.vtuberrush.src.managers.EnemyManager;

public class SlimePurple extends Enemy {

	public SlimePurple(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_PURPLE, enemyManager);
	}

}
