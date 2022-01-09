package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_WHITE;

import com.vtuberrush.src.managers.EnemyManager;

/**
 * A <b><i>White Slime</i> (Level 5 Slime)</b> is a subclass of an <b><i>Enemy</i></b> with 3,906 health and 11.40 movement speed. <br>
 * A white slime has 3906% of the health and 31.66% of the movement speed of a green slime.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class SlimeWhite extends Enemy {

	/**
	 * Creates a new <b><i>White Slime</i></b> at the defined location.
	 * @param x - Defines the horizontal position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param y - Defines the vertical position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param id - A unique identifier for this enemy.
	 * @param enemyManager - Facilitates the rendering and pathfinding of this enemy.
	 */
	public SlimeWhite(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_WHITE, enemyManager);
	}

}
