package com.vtuberrush.src.managers;

import java.util.ArrayList;
import java.util.Arrays;

import com.vtuberrush.src.scenes.Playing;
import com.vtuberrush.src.waves.Wave;

public class WaveManager {

	private Playing playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int tickAddDuration = 120;
	private int tickAdd = tickAddDuration;
	private int tickWaveDuration = 120 * 5;
	private int tickWave = 0;
	private int enemyIndex, waveIndex;
	private boolean waveDelayStart, waveDelayEnd;
	
	public WaveManager(Playing playing) {
		this.playing = playing;
		initWaves();
	}
	
	public void tick() {
		if (tickAdd < tickAddDuration) {
			tickAdd++;
		}
		if (waveDelayStart) {
			tickWave++;
			if(tickWave >= tickWaveDuration) {
				waveDelayEnd = true;
			}
		}
	}
		
	private void initWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0))));
	}
	
	public void delayWaveStart() {
		waveDelayStart = true;
	}
	
	public void initNextWave() {
		waveIndex++;
		waveDelayStart = false;
		waveDelayEnd = false;
		enemyIndex = 0;
	}
	
	public boolean isWaveDelayEnd() {
		return waveDelayEnd;
	}
	
	public boolean isAddEnemy() {
		return tickAdd >= tickAddDuration;
	}
	
	public boolean isAddEnemyNext() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}
	
	public boolean isAddWaveNext() {
		return waveIndex < waves.size();
	}
	
	public ArrayList<Wave> getWaves() {
		return waves;
	}
	
	public int getWaveIndex() {
		return waveIndex;
	}

	public int getNextEnemy() {
		tickAdd = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}
	
	public float getTimeLeft() {
		float secondsLeft = tickWaveDuration - tickWave;
		return secondsLeft / 120.0f;
	}

	public boolean isTimerStart() {
		return waveDelayStart;
	}
	
}
