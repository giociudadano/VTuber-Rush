package com.vtuberrush.src.waves;

import java.util.ArrayList;

public class Wave {
	public ArrayList<Integer> enemyList;
	public Wave(ArrayList<Integer> enemyList) {
		this.enemyList = enemyList;
	}
	
	public ArrayList<Integer> getEnemyList() {
		return enemyList;
	}
}
