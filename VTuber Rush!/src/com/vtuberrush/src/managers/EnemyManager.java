package com.vtuberrush.src.managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Direction.*;
import static com.vtuberrush.src.helpers.Constants.Tiles.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	
	private float speed = 2.5f;
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		enemySprites = new BufferedImage[4];
		addEnemy(0, 32*13);
		loadEnemies();
	}
	
	public void tick() {
		for (Enemy enemy : enemies) {
			if (isPathable(enemy)) {
				
			}
		}	
	}
	
	private boolean isPathable(Enemy enemy) {
		int x = (int)(enemy.getX() + getSpeedX(enemy.getDirection()));
		int y = (int)(enemy.getY() + getSpeedY(enemy.getDirection()));
		if (getTileType(x, y) == ROAD) {
			enemy.move(speed, enemy.getDirection());
		} else if (isEnd()){
			
		} else {
			setDirection(enemy);
		}
		return false;
	}

	private void setDirection(Enemy enemy) {
		int direction = enemy.getDirection();
		checkOffset(enemy, direction, (int)(enemy.getX()/32), (int)(enemy.getY()/32));
		if (direction == LEFT || direction == RIGHT) {
			int y = (int)(enemy.getY() + getSpeedY(UP));
			if (getTileType((int)enemy.getX(), y) == ROAD) {
				enemy.move(speed, UP);
			} else {
				enemy.move(speed, DOWN);
			}
		} else {
			int x = (int)(enemy.getX() + getSpeedX(RIGHT));
			if (getTileType(x, (int)enemy.getY()) == ROAD) {
				enemy.move(speed, RIGHT);
			} else {
				enemy.move(speed, LEFT);
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

	private boolean isEnd() {
		return false;
	}

	//Pathfinding
	private float getSpeedX(int direction) {
		switch (direction) {
		case LEFT: return -speed;
		case RIGHT: return speed + 32;
		default: return 0;
		}
	}

	private float getSpeedY(int direction) {
		switch (direction) {
		case UP: return -speed;
		case DOWN: return speed + 32;
		default: return 0;
		}
	}
	
	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	public void draw(Graphics graphics) {
		for (Enemy enemy : enemies) {
			drawEnemy(graphics, enemy);
		}	
	}
	
	public void loadEnemies() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		enemySprites[0] = atlas.getSubimage(0, 128, 32, 32);
		enemySprites[1] = atlas.getSubimage(32, 128, 32, 32);
		enemySprites[2] = atlas.getSubimage(64, 128, 32, 32);
		enemySprites[3] = atlas.getSubimage(96, 128, 32, 32);
	}
	
	public void addEnemy(int x, int y) {
		enemies.add(new Enemy(x, y, 0, 0));
	}
		
	private void drawEnemy(Graphics graphics, Enemy enemy) {
		graphics.drawImage(enemySprites[0], (int)enemy.getX(), (int)enemy.getY(), null);
	}
}
