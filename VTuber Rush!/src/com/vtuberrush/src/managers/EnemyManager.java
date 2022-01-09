package com.vtuberrush.src.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.enemies.SlimeBlue;
import com.vtuberrush.src.enemies.SlimeGreen;
import com.vtuberrush.src.enemies.SlimePurple;
import com.vtuberrush.src.enemies.SlimeRed;
import com.vtuberrush.src.enemies.SlimeWhite;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Direction.UP;
import static com.vtuberrush.src.helpers.Constants.Direction.DOWN;
import static com.vtuberrush.src.helpers.Constants.Direction.LEFT;
import static com.vtuberrush.src.helpers.Constants.Direction.RIGHT;

import static com.vtuberrush.src.helpers.Constants.Tiles.ROAD;

import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_GREEN;
import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_BLUE;
import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_PURPLE;
import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_RED;
import static com.vtuberrush.src.helpers.Constants.Enemies.SLIME_WHITE;
import static com.vtuberrush.src.helpers.Constants.Enemies.getSpeed;

/**
 * Facilitates the rendering and pathfinding of enemies.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private BufferedImage effectSlowed, effectBurned, effectChilled, effectFrozen;
	private int animationIndex, animationDelay;
	
	private Flag start, end;
	
	/**
	 * Initializes the list of enemies and enemy effects.
	 * Created when initializing the <b>PLAYING</b> game state.
	 * @param playing - Defines the specific instance of the scene where the unit manager is initialized.
	 * @see #loadEffects()
	 * @see #loadEnemies()
	 */
	public EnemyManager(Playing playing, Flag start, Flag end) {
		this.playing = playing;
		this.start = start;
		this.end = end;
		enemySprites = new BufferedImage[5][7];
		loadEffects();
		loadEnemies();
	}
	
	
	/**
	 * Initializes the sprites used for enemy effects.
	 */
	public void loadEffects() {
		effectSlowed = LoadSave.getSpriteAtlas().getSubimage(192, 0, 16, 16);
		effectBurned = LoadSave.getSpriteAtlas().getSubimage(208, 0, 16, 16);
		effectChilled = LoadSave.getSpriteAtlas().getSubimage(224, 0, 16, 16);
		effectFrozen = LoadSave.getSpriteAtlas().getSubimage(240, 0, 16, 16);
	}
	
	/**
	 * Initializes the sprites used for enemies.
	 */
	public void loadEnemies() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				enemySprites[i][j] = atlas.getSubimage(32 * j, 352 + (32 * i), 32, 32);
			}
		}
	}
	
	/**
	 * Updates the movement and animation of all enemies.
	 */
	public void tick() {
		tickAnimation();
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				tickMove(enemy);
			}
		}	
	}

	/**
	 * Updates the animation of all enemies.
	 */
	private void tickAnimation() {
		animationDelay = (animationDelay + 1) % 24;
		if(animationDelay == 23) {
			animationIndex = (animationIndex + 1) % 7;
		}
	}

	/**
	 * Updates the movement of the passed enemy.
	 * @param enemy - The enemy being moved.
	 */
	private void tickMove(Enemy enemy) {
		if (enemy.getDirection() == -1) {
			setDirection(enemy);
		}
		
		int x = (int)(enemy.getX() + getSpeedX(enemy.getDirection(), enemy.getEnemyType()));
		int y = (int)(enemy.getY() + getSpeedY(enemy.getDirection(), enemy.getEnemyType()));
		
		if (getTileType(x, y) == ROAD) {
			enemy.move(getSpeed(enemy.getEnemyType()), enemy.getDirection());
		} else if (isEnd(enemy)) {
			enemy.takePurge();
			playing.subtractLives(enemy.getEnemyType()+1);
		} else {
			setDirection(enemy);
		}
	}

	/**
	 * Dynamically checks if the next tile in the current direction of the passed enemy is a
	 * road tile, and updates the current direction of the enemy if not.<br>
	 * If the enemy is not on a road tile, the {@link #checkOffset(Enemy, int, int, int)} method
	 * will look for the nearest available road tile and update the position of the enemy.
	 * @param enemy - The enemy being moved.
	 * @see #checkOffset(Enemy, int, int, int)
	 */
	private void setDirection(Enemy enemy) {
		int direction = enemy.getDirection();
		checkOffset(enemy, direction, (int)(enemy.getX()/32), (int)(enemy.getY()/32));
		if (isEnd(enemy)) {
			return;
		}
		if (direction == LEFT || direction == RIGHT) {
			int y = (int)(enemy.getY() + getSpeedY(UP, enemy.getEnemyType()));
			if (getTileType((int)enemy.getX(), y) == ROAD) {
				enemy.move(getSpeed(enemy.getEnemyType()), UP);
			} else {
				enemy.move(getSpeed(enemy.getEnemyType()), DOWN);
			}
		} else {
			int x = (int)(enemy.getX() + getSpeedX(RIGHT, enemy.getEnemyType()));
			if (getTileType(x, (int)enemy.getY()) == ROAD) {
				enemy.move(getSpeed(enemy.getEnemyType()), RIGHT);
			} else {
				enemy.move(getSpeed(enemy.getEnemyType()), LEFT);
			}
		}
	}

	/**
	 * Sets the position of the enemy to a road tile, called when the enemy is not on a road tile.
	 * @param enemy - The enemy moving and not on a road tile.
	 * @param direction - The current walking direction of the enemy.
	 * @param x - The horizontal position of the current non-road tile the enemy is stepping on.
	 * @param y - The vertical position of the current non-road tile the enemy is stepping on.
	 */
	private void checkOffset(Enemy enemy, int direction, int x, int y) {
		switch(direction) {
		case RIGHT: 
			x++;
			break;
		case DOWN:
			y++;
			break;
		default:
			break;
		}
		enemy.setPosition(x*32, y*32);
	}

	/**
	 * Returns <b><i>true</i></b> if the enemy is on an <i>end</i> flag. <br>
	 * Called by other methods when moving to check when to purge the enemy and subtract lives.
	 * @param enemy - The enemy currently moving.
	 */
	private boolean isEnd(Enemy enemy) {
		if (enemy.getX() == end.getX()*32) {
			if (enemy.getY() == end.getY()*32) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the horizontal velocity of the enemy.
	 * @param direction - The current horizontal direction of the enemy. Returns 0 if the enemy is moving upwards or downwards.
	 * @param enemyType - The type of the enemy. Used to obtain the speed of the enemy.
	 * @return <b>velocity</b> - The horizontal speed and direction of the enemy.
	 */
	private float getSpeedX(int direction, int enemyType) {
		switch (direction) {
		case LEFT: return -getSpeed(enemyType);
		case RIGHT: return getSpeed(enemyType) + 32;
		default: return 0;
		}
	}

	/**
	 * Returns the vertical velocity of the enemy.
	 * @param direction - The current vertical direction of the enemy. Returns 0 if the enemy is moving leftwards or rightwards.
	 * @param enemyType - The type of the enemy. Used to obtain the speed of the enemy.
	 * @return <b>velocity</b> - The vertical speed and direction of the enemy.
	 */
	private float getSpeedY(int direction, int enemyType) {
		switch (direction) {
		case UP: return -getSpeed(enemyType);
		case DOWN: return getSpeed(enemyType) + 32;
		default: return 0;
		}
	}
	
	/**
	 * Returns the type of tile using the passed tile coordinates.<br>
	 * Called by other methods to check the pathfinding of all enemies.
	 * @param x - The horizontal position of the current tile from the left of the window in number of tiles.
	 * @param y - The vertical position of the current tile from the top of the window in number of tiles.
	 * @return type - The type of tile using the passed tile coordinates.
	 */
	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	/**
	 * Renders all enemies, enemy health bars, and enemy effects.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	public void draw(Graphics graphics) {
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				drawEnemy(graphics, enemy);
				drawHealthBar(graphics, enemy);
				drawEffects(graphics, enemy);
			}
		}	
	}
	
	/**
	 * Renders the passed enemy and its shadow.
	 * @param graphics - Responsible for drawing the passed enemy to the screen.
	 * @param enemy - The enemy being drawn.
	 */
	private void drawEnemy(Graphics graphics, Enemy enemy) {
		graphics.setColor(new Color(0, 0, 0, 50));
		graphics.fillOval((int)enemy.getX()+2, (int)enemy.getY()+24, 28, 8);
		graphics.drawImage(enemySprites[enemy.getEnemyType()][getAnimationIndex()], (int)enemy.getX(), (int)enemy.getY(), null);
	}

	/**
	 * Renders the health bar of the passed enemy, which includes information related to the percentage of
	 * health remaining.
	 * @param graphics - Responsible for drawing the health bar of the passed enemy to the screen.
	 * @param enemy - The owner of the health bar being drawn.
	 */
	private void drawHealthBar(Graphics graphics, Enemy enemy) {
		graphics.setColor(new Color(156, 0, 0));
		graphics.fillRect((int) enemy.getX(), (int) enemy.getY()-8, 32, 3);
		graphics.setColor(new Color(254, 90, 89));
		graphics.fillRect((int) enemy.getX(), (int) enemy.getY()-8, getHealthBarWidth(enemy), 3);
	}
	
	/**
	 * Renders the applied effects of the passed enemy.
	 * @param graphics - Responsible for drawing the applied effects of the passed enemy to the screen.
	 * @param enemy - The owner of the applied effects being drawn.
	 */
	private void drawEffects(Graphics graphics, Enemy enemy) {
		int i = 0;
		if(enemy.isSlowed()) {
			graphics.drawImage(effectSlowed, (int) enemy.getX() + (18 * i++), (int) enemy.getY(), 16, 16, null);
		}
		if (enemy.isBurned()) {
			graphics.drawImage(effectBurned, (int) enemy.getX() + (18 * i++), (int) enemy.getY(), 16, 16, null);
		}
		if (enemy.isChilled()) {
			graphics.drawImage(effectChilled, (int) enemy.getX() + (18 * i++), (int) enemy.getY(), 16, 16, null);
		}
		if (enemy.isFrozen()) {
			graphics.drawImage(effectFrozen, (int) enemy.getX() + (18 * i++), (int) enemy.getY(), 16, 16, null);
		}
	}
	
	/**
	 * Returns the width of the current health portion of the health bar being drawn.
	 * @param enemy -  The owner of the current health portion of the health bar being drawn.
	 * @return <b>pixels</b> - The number of pixels of the width of the health bar.
	 */
	private int getHealthBarWidth(Enemy enemy) {
		return (int)(32 * enemy.getHealthPercent());
	}

	/**
	 * Adds a new enemy to the <i>start</i> flag depending on the passed enemy type. <br>
	 * Called by the <b>WaveManager</b> when spawning new enemies in a wave.
	 * @param type - The type of enemy being added.
	 */
	public void addEnemy(int type) {
		int x = start.getX() * 32;
		int y = start.getY() * 32;
		switch(type) {
		case SLIME_GREEN:
			enemies.add(new SlimeGreen(x, y, 0, this));
			break;
		case SLIME_BLUE:
			enemies.add(new SlimeBlue(x, y, 0, this));
			break;
		case SLIME_RED:
			enemies.add(new SlimeRed(x, y, 0, this));
			break;
		case SLIME_PURPLE:
			enemies.add(new SlimePurple(x, y, 0, this));
			break;
		case SLIME_WHITE:
			enemies.add(new SlimeWhite(x, y, 0, this));
			break;
		default: break;
		}
	}
	
	/**
	 * Adds gold to the user depending on the passed enemy type. <br>
	 * Called when an enemy is killed.
	 * @param type - The type of enemy being killed.
	 */
	public void addGold(int type) {
		playing.addGold(type);
	}
	
	/**
	 * Clears all enemies in the field.
	 * <br> Called during a game over or game complete condition.
	 */
	public void resetGame() {
		enemies.clear();
	}
	
	/**
	 * Returns a list of all spawnable enemies.
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * Returns the number of enemies remaining on the field.
	 * @return <b>remaining</b> - The number of enemies remaining on the field.
	 */
	public int getRemainingEnemies() {
		int size = 0;
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				size++;
			}
		}
		return size;
	}
	
	/**
	 * Returns the current animation frame for all enemies.
	 * @return <b>index</b> - The current frame of animation for all enemies.
	 */
	private int getAnimationIndex() {
		return animationIndex;
	}
}
