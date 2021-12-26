package com.vtuberrush.src.managers;

import java.util.ArrayList;
import java.util.Arrays;

import com.vtuberrush.src.scenes.Playing;
import com.vtuberrush.src.waves.Wave;

public class WaveManager {

	private Playing playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int tickSpawnDuration = 120;
	private int tickSpawn = tickSpawnDuration;
	private int enemyIndex, waveIndex;
	
	public WaveManager(Playing playing) {
		this.playing = playing;
		createWaves();
	}
	
	public void tick() {
		if (tickSpawn < tickSpawnDuration) {
			tickSpawn++;
		}
	}
	
	private void createWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0))));
	}
	
	public boolean isSpawnEnemy() {
		return tickSpawn >= tickSpawnDuration;
	}
	
	public boolean isSpawnEnemyNext() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public int getNextEnemy() {
		tickSpawn = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}
	
}
