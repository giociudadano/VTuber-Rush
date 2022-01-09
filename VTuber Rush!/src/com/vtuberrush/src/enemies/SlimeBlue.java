package com.vtuberrush.src.enemies;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_BLUE;

import com.vtuberrush.src.managers.EnemyManager;

/**
 * A <b><i>Blue Slime</i> (Level 2 Slime)</b> is a subclass of an <b><i>Enemy</i></b> with 250 health and 27 movement speed. <br>
 * A blue slime has 250% of the health and 75% of the movement speed of a green slime.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class SlimeBlue extends Enemy {

	/**
	 * Creates a new <b><i>Blue Slime</i></b> at the defined location.
	 * @param x - Defines the horizontal position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param y - Defines the vertical position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param id - A unique identifier for this enemy.
	 * @param enemyManager - Facilitates the rendering and pathfinding of this enemy.
	 */
	public SlimeBlue(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, SLIME_BLUE, enemyManager);
	}

}
