package com.vtuberrush.src.managers;

import java.util.ArrayList;
import java.util.Arrays;

import com.vtuberrush.src.scenes.Playing;
import com.vtuberrush.src.waves.Wave;

public class WaveManager {

	private Playing playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int tickAddDuration = 150;
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

		//400, 600, 900, 1175, 1425
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,1,0,0,1,1,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,1,0,0,1,2))));
		
		//1800, 2250, 2525, 2950, 3175
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,1,0,0,1,1,0,0,1,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,0,2,0,2,0,1,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,0,0,2,0,0,0,1,1,0,0,1,0,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,0,1,0,0,2,1,0,0,1,0,0,2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,1,0,1,1,2,1,0,1,2,2))));
		
		//3350, 3925, 4025, 4724, 4825
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,2,1,1,0,1,1,2,1,2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,1,0,2,2,0,2,2,1,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1,0,0,1,1,0,0,0,1,1,1,0,0,1,1,1,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,3,1,0,0,0,1,1,3,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,1,0,0,1,0,2,0,0,1,1,2,0,0,2))));
		
		//5462, 5425, 6149, 6087, 6811
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,1,1,0,0,1,2,2,0,0,3,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1,1,0,0,2,1,1,1,0,0,1,2,1,1,0,0,1,1,2,0,0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,2,0,1,3,0,2,0,1,3,2,2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1,0,0,1,1,2,1,1,3,0,1,1,2,1,0,0,2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,3,1,3,2,3))));
		
		//7586, 7531, 8436, 8749, 9842
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,2,1,1,0,0,3,2,1,1,0,0,3,1))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,2,1,0,1,2,0,2,1,0,1,1,0,4))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,2,2,1,1,0,0,0,3,2,2,1,0,0,3))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1,2,2,3,1,2,2,2,3,1,1,2,2))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,3,2,2,3,4))));
		
	}
	
	public void delayWaveStart() {
		waveDelayStart = true;
	}
	
	public void initNextWave() {
		waveIndex++;
		tickWave = 0;
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

	public void resetGame() {
		waves.clear();
		initWaves();
		enemyIndex = 0;
		waveIndex = 0;
		waveDelayStart = false;
		waveDelayEnd = false;
		tickWave = tickWaveDuration;
	}
	
}
