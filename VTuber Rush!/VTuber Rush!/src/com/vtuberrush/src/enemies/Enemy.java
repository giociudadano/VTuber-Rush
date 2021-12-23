package com.vtuberrush.src.enemies;

import java.awt.Rectangle;
import static com.vtuberrush.src.helpers.Constants.Direction.*;

public class Enemy {
	private float x, y;
	private Rectangle bounds;
	private int health;
	private int id;
	private int enemyType;
	private int direction;
	
	public Enemy(float x, float y, int id, int enemyType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		bounds = new Rectangle((int)x, (int)y, 32, 32);
		direction = RIGHT;
	}

	public void move(float speed, int directionMove) {
		direction = directionMove;
		switch (directionMove) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		default: break;
		}
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getId() {
		return id;
	}

	public int getEnemyType() {
		return enemyType;
	}
	
	public int getDirection() {
		return direction;
	}
	
}
