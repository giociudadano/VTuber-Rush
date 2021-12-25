package com.vtuberrush.src.enemies;

import java.awt.Rectangle;
import static com.vtuberrush.src.helpers.Constants.Direction.*;

public abstract class Enemy {
	protected float x, y;
	protected Rectangle bounds;
	protected boolean alive = true;
	protected int health, maxHealth;
	protected int id;
	protected int enemyType;
	protected int direction;

	
	public Enemy(float x, float y, int id, int enemyType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		bounds = new Rectangle((int)x, (int)y, 32, 32);
		direction = NONE;
		setMaxHealth();
	}
	
	private void setMaxHealth() {
		maxHealth = com.vtuberrush.src.helpers.Constants.Enemies.getMaxHealth(enemyType);
		health = maxHealth;
	}
	
	public void takeDamage(int damage) {
		this.health -= damage;
		if (health <= 0) {
			alive = false;
		}
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
		tickHitbox();
	}
	
	private void tickHitbox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
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

	public boolean isAlive() {
		return alive;
	}
	
	public int getHealth() {
		return health;
	}
	
	public float getHealthPercent() {
		return health / (float) maxHealth;
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
