package com.vtuberrush.src.managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.enemies.SlimeBlue;
import com.vtuberrush.src.enemies.SlimeGreen;
import com.vtuberrush.src.enemies.SlimeRed;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Direction.*;
import static com.vtuberrush.src.helpers.Constants.Tiles.*;
import static com.vtuberrush.src.helpers.Constants.Enemies.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	
	private float speed = 0.5f;
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		enemySprites = new BufferedImage[4];
		addEnemy(0, 32*13, SLIME_GREEN);
		addEnemy(32, 32*13, SLIME_BLUE);
		addEnemy(64, 32*13, SLIME_RED);
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
			tickMove(enemy);
		}	
	}
	
	//Pathfinding AI
	private void tickMove(Enemy enemy) {
		if (enemy.getDirection() == NONE) {
			setDirection(enemy);
		}
		
		int x = (int)(enemy.getX() + getSpeedX(enemy.getDirection()));
		int y = (int)(enemy.getY() + getSpeedY(enemy.getDirection()));
		
		if (getTileType(x, y) == ROAD) {
			enemy.move(speed, enemy.getDirection());
		} else if (isEnd()){
			
		} else {
			setDirection(enemy);
		}
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

	//
	public void draw(Graphics graphics) {
		for (Enemy enemy : enemies) {
			drawEnemy(graphics, enemy);
		}	
	}
	
	public void addEnemy(int x, int y, int type) {
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
		graphics.drawImage(enemySprites[enemy.getEnemyType()], (int)enemy.getX(), (int)enemy.getY(), null);
	}
}
