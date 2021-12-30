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

import static com.vtuberrush.src.helpers.Constants.Direction.*;
import static com.vtuberrush.src.helpers.Constants.Tiles.*;
import static com.vtuberrush.src.helpers.Constants.Enemies.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private BufferedImage effectSlowed, effectBurned;
	private int animationIndex, animationDelay;
	
	private Flag start, end;
	
	public EnemyManager(Playing playing, Flag start, Flag end) {
		this.playing = playing;
		this.start = start;
		this.end = end;
		enemySprites = new BufferedImage[5][7];
		loadEffects();
		loadEnemies();
	}
	
	public void loadEffects() {
		effectSlowed = LoadSave.getSpriteAtlas().getSubimage(192, 0, 16, 16);
		effectBurned = LoadSave.getSpriteAtlas().getSubimage(208, 0, 16, 16);
	}
	
	public void loadEnemies() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				enemySprites[i][j] = atlas.getSubimage(32 * j, 352 + (32 * i), 32, 32);
			}
		}
	}
	
	public void tick() {
		tickAnimation();
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				tickMove(enemy);
			}
		}	
	}

	private void tickAnimation() {
		animationDelay = (animationDelay + 1) % 24;
		if(animationDelay == 23) {
			animationIndex = (animationIndex + 1) % 7;
		}
	}

	//Pathfinding AI
	private void tickMove(Enemy enemy) {
		if (enemy.getDirection() == NONE) {
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

	private boolean isEnd(Enemy enemy) {
		if (enemy.getX() == end.getX()*32) {
			if (enemy.getY() == end.getY()*32) {
				return true;
			}
		}
		return false;
	}

	private float getSpeedX(int direction, int enemyType) {
		switch (direction) {
		case LEFT: return -getSpeed(enemyType);
		case RIGHT: return getSpeed(enemyType) + 32;
		default: return 0;
		}
	}

	private float getSpeedY(int direction, int enemyType) {
		switch (direction) {
		case UP: return -getSpeed(enemyType);
		case DOWN: return getSpeed(enemyType) + 32;
		default: return 0;
		}
	}
	
	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	public void draw(Graphics graphics) {
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				drawEnemy(graphics, enemy);
				drawHealthBar(graphics, enemy);
				drawEffects(graphics, enemy);
			}
		}	
	}
	
	private void drawEnemy(Graphics graphics, Enemy enemy) {
		graphics.setColor(new Color(0, 0, 0, 50));
		graphics.fillOval((int)enemy.getX()+2, (int)enemy.getY()+24, 28, 8);
		graphics.drawImage(enemySprites[enemy.getEnemyType()][getAnimationIndex()], (int)enemy.getX(), (int)enemy.getY(), null);
	}

	private void drawHealthBar(Graphics graphics, Enemy enemy) {
		graphics.setColor(new Color(156, 0, 0));
		graphics.fillRect((int) enemy.getX(), (int) enemy.getY()-8, 32, 3);
		graphics.setColor(new Color(254, 90, 89));
		graphics.fillRect((int) enemy.getX(), (int) enemy.getY()-8, getHealthBarWidth(enemy), 3);
	}
	
	private void drawEffects(Graphics graphics, Enemy enemy) {
		int i = 0;
		if(enemy.isSlowed()) {
			graphics.drawImage(effectSlowed, (int) enemy.getX() + (18 * i++), (int) enemy.getY(), 16, 16, null);
		}
		if (enemy.isBurned()) {
			graphics.drawImage(effectBurned, (int) enemy.getX() + (18 * i++), (int) enemy.getY(), 16, 16, null);
		}
	}
	
	private int getHealthBarWidth(Enemy enemy) {
		return (int)(32 * enemy.getHealthPercent());
	}

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
	
	public void addGold(int type) {
		playing.addGold(type);
	}
	
	public void resetGame() {
		enemies.clear();
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public int getRemainingEnemies() {
		int size = 0;
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				size++;
			}
		}
		return size;
	}
	
	
	private int getAnimationIndex() {
		return animationIndex;
	}
}
