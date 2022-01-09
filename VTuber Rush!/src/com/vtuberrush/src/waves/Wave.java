package com.vtuberrush.src.waves;

import java.util.ArrayList;

/**
 * A <b><i>wave</i></b> is an integer array list whose elements are the enemy types.<br>
 * Waves are constructed in the <b><i>WaveManager</i></b> and are used for spawning
 * defined enemy types in short intervals when a new wave is started in the game.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Wave {
	/**
	 * A collection used to map the integers with their respective enemy type<br>
	 * Enemies are spawned from the first index incrementing until the end.
	 */
	public ArrayList<Integer> enemyList;
	
	/**
	 * Creates a new wave using the passed integers as the enemy types.
	 * @param enemyList - A list of integers used to map the integers with their respective enemy type.
	 */
	public Wave(ArrayList<Integer> enemyList) {
		this.enemyList = enemyList;
	}
	
	/**
	 * Returns a list of enemies used for spawning a new wave.
	 * @return {@link #enemyList}
	 */
	public ArrayList<Integer> getEnemyList() {
		return enemyList;
	}
}
