package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_PURPLE;

import com.vtuberrush.src.managers.EnemyManager;

/**
 * A <b><i>Purple Slime</i> (Level 4 Slime)</b> is a subclass of an <b><i>Enemy</i></b> with 1,562 health and 15.12 movement speed. <br>
 * A purple slime has 1562% of the health and 42% of the movement speed of a green slime.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class SlimePurple extends Enemy {

	/**
	 * Creates a new <b><i>Purple Slime</i></b> at the defined location.
	 * @param x - Defines the horizontal position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param y - Defines the vertical position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param id - A unique identifier for this enemy.
	 * @param enemyManager - Facilitates the rendering and pathfinding of this enemy.
	 */
	public SlimePurple(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_PURPLE, enemyManager);
	}

}
