package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_WHITE;

import com.vtuberrush.src.managers.EnemyManager;

public class SlimeWhite extends Enemy {

	public SlimeWhite(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_WHITE, enemyManager);
	}

}
