package com.vtuberrush.src.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.enemies.SlimeBlue;
import com.vtuberrush.src.enemies.SlimeGreen;
import com.vtuberrush.src.enemies.SlimeRed;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Direction.*;
import static com.vtuberrush.src.helpers.Constants.Tiles.*;
import static com.vtuberrush.src.helpers.Constants.Enemies.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	
	private Flag start, end;
	
	public EnemyManager(Playing playing, Flag start, Flag end) {
		this.playing = playing;
		this.start = start;
		this.end = end;
		enemySprites = new BufferedImage[4];
		addEnemy(SLIME_GREEN);
		addEnemy(SLIME_BLUE);
		addEnemy(SLIME_RED);
		loadEnemies();
	}
	
	public void loadEnemies() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 4; i++) {
			enemySprites[i] = atlas.getSubimage(32 * i, 128, 32, 32);
		}
	}
	
	public void tick() {
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				tickMove(enemy);
			}
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
			}
		}	
	}
	
	private void drawHealthBar(Graphics graphics, Enemy enemy) {
		graphics.setColor(new Color(254, 90, 89));
		graphics.fillRect((int) enemy.getX(), (int) enemy.getY()-8, getHealthBarWidth(enemy), 3);
	}
	
	private int getHealthBarWidth(Enemy enemy) {
		return (int)(32 * enemy.getHealthPercent());
	}

	public void addEnemy(int type) {
		int x = start.getX() * 32;
		int y = start.getY() * 32;
		switch(type) {
		case SLIME_GREEN:
			enemies.add(new SlimeGreen(x, y, 0));
			break;
		case SLIME_BLUE:
			enemies.add(new SlimeBlue(x, y, 0));
			break;
		case SLIME_RED:
			enemies.add(new SlimeRed(x, y, 0));
			break;
		}
	}
		
	private void drawEnemy(Graphics graphics, Enemy enemy) {
		graphics.setColor(new Color(0, 0, 0, 50));
		graphics.fillOval((int)enemy.getX()+2, (int)enemy.getY()+24, 28, 8);
		graphics.drawImage(enemySprites[enemy.getEnemyType()], (int)enemy.getX(), (int)enemy.getY(), null);
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
}
