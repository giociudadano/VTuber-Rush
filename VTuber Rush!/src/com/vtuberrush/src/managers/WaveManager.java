package com.vtuberrush.src.managers;

import java.util.ArrayList;
import java.util.Arrays;

import com.vtuberrush.src.scenes.Playing;
import com.vtuberrush.src.waves.Wave;

/**
 * Facilitates the spawning of waves or groups of enemies in set intervals.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class WaveManager {

	private Playing playing;
	private ArrayList<Wave> waves = new ArrayList<>();
	private int tickAddDuration = 150;
	private int tickAdd = tickAddDuration;
	private int tickWaveDuration = 120 * 5;
	private int tickWave = 0;
	private int enemyIndex, waveIndex;
	private boolean waveDelayStart, waveDelayEnd;
	
	/**
	 * Initializes and creates a list of waves for the player to defend against.
	 * Created when initializing the <b>PLAYING</b> game state.
	 * @param playing - Defines the specific instance of the scene where the wave manager is initialized.
	 * @see #initWaves()
	 */
	public WaveManager(Playing playing) {
		this.playing = playing;
		initWaves();
	}
	
	/**
	 * Updates game timers used for controlling the delay of spawning between
	 * enemies and the delay for spawning enemies between waves.
	 * @see #isAddEnemyNext()
	 * @see #isAddWaveNext()
	 */
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
	
	/**
	 * Creates a new <b><i>Wave</i></b> object 25 times and assigns it to <b><i>waves</i></b> array list
	 * containing Wave objects as its members.
	 * <br> Used by the <b><i>EnemyManager</i></b> to spawn new enemies in set intervals while
	 * playing the game.
	 */
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
	
	/**
	 * Starts a timer which spawns the next wave after 5 seconds.
	 * <br>Called when all enemies in a wave have been killed and the user is ready to
	 * accept another wave of enemies.
	 */
	public void delayWaveStart() {
		waveDelayStart = true;
	}
	
	/**
	 * Increments the wave index by one, resets the wave delay timer, and defines the next
	 * enemy to be spawned as the enemy in the first index of the next wave.
	 */
	public void initNextWave() {
		waveIndex++;
		tickWave = 0;
		waveDelayStart = false;
		waveDelayEnd = false;
		enemyIndex = 0;
	}
	
	/**
	 * Returns <i>true</i> if the timer used to spawn the next wave is more than 5 seconds.
	 * <br>Used to check and spawn the next wave if set to <i>false</i>.
	 * @return {@link #waveDelayEnd}
	 */
	public boolean isWaveDelayEnd() {
		return waveDelayEnd;
	}
	
	/**
	 * Returns <i>true</i> if the timer used to spawn the next enemy in the wave is more than 1.25 seconds.
	 * <br>Used to check whether to spawn a new enemy.
	 */
	public boolean isAddEnemy() {
		return tickAdd >= tickAddDuration;
	}
	
	/**
	 * Returns <i>true</i> if there are still available enemies to be spawned in the wave.
	 * <br>Used for spawning a new wave and stopping spawns in a spawned wave.
	 */
	public boolean isAddEnemyNext() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}
	
	/**
	 * Returns <i>true</i> if there are still waves to be spawned.
	 * <br>Used to end the game if all enemies were spawned and killed.
	 */
	public boolean isAddWaveNext() {
		return waveIndex < waves.size();
	}
	
	/**
	 * Returns an array list whose members are <b><i>Wave</i></b> objects.
	 * @return {@link #waves}
	 */
	public ArrayList<Wave> getWaves() {
		return waves;
	}
	
	/**
	 * Returns the current wave number.
	 * @return {@link #waveIndex}
	 */
	public int getWaveIndex() {
		return waveIndex;
	}

	/**
	 * Returns the next enemy in the <b><i>Wave</i></b> enemy type list.
	 * <br>Resets the timer used to delay the spawning between enemies in a wave.
	 * @return {@link #waveIndex}
	 */
	public int getNextEnemy() {
		tickAdd = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}
	
	/**
	 * Returns the amount of time remaining before the next wave.
	 */
	public float getTimeLeft() {
		float secondsLeft = tickWaveDuration - tickWave;
		return secondsLeft / 120.0f;
	}

	/**
	 * Returns <i>true</i> if the timer used to delay the spawning between waves is
	 * currently active.
	 */
	public boolean isTimerStart() {
		return waveDelayStart;
	}

	/**
	 * Resets all timers, wave numbers, and enemy indices.
	 * <br> Called during a game over or game complete condition.
	 */
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
