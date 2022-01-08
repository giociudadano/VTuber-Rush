package com.vtuberrush.src.enemies;

import java.awt.Rectangle;

import com.vtuberrush.src.managers.EnemyManager;

import static com.vtuberrush.src.helpers.Constants.Direction.UP;
import static com.vtuberrush.src.helpers.Constants.Direction.DOWN;
import static com.vtuberrush.src.helpers.Constants.Direction.LEFT;
import static com.vtuberrush.src.helpers.Constants.Direction.RIGHT;

public abstract class Enemy {
	
	protected float x, y;
	protected Rectangle bounds;
	protected boolean alive = true;
	protected int health, maxHealth;
	protected int id;
	protected int enemyType;
	protected EnemyManager enemyManager;
	protected int direction;
	
	private int tickSlowDuration = 300;
	private int tickSlow = tickSlowDuration;
	
	private int tickBurnDuration = 480;
	private int tickBurnCooldown;
	private int tickBurn = tickBurnDuration;
	
	private boolean isChilled = false;
	private int tickFrozenCooldown = 690;
	private int tickFrozenDuration = 90;
	private int tickFrozen = tickFrozenCooldown;


	
	public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		this.enemyManager = enemyManager;
		bounds = new Rectangle((int)x, (int)y, 32, 32);
		direction = -1;
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
			enemyManager.addGold(enemyType);
		}
	}
	
	public void takeSlow() {
		tickSlow = 0;
	}
	
	public void takeBurn() {
		tickBurn = 0;
	}
	
	public void takeFrozen() {
		if (tickFrozen >= tickFrozenCooldown) {
			tickFrozen = 0;
		}
	}
	
	public void takePurge() {
		alive = false;
		health = 0;
	}
	
	public void move(float speed, int directionMove) {
		direction = directionMove;
		if (isChilled()) {
			speed *= 0.75f;
		}
		if (isSlowed()) {
			tickSlow++;
			speed *= 0.4f;
		}
		if (tickFrozen < tickFrozenCooldown) {
			tickFrozen++;
			if (isFrozen()) {
				speed = 0;
			}
		}
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
		tickBurn();
	}
	
	private void tickBurn() {
		if (tickBurn < tickBurnDuration) {
			tickBurn++;
			tickBurnCooldown = (tickBurnCooldown + 1) % 60;
			if (tickBurnCooldown == 0) {
				takeDamage((int)(5 + this.maxHealth * 0.005));
			}
		}
	}

	private void tickHitbox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public boolean isSlowed() {
		return tickSlow < tickSlowDuration;
	}
	
	public boolean isBurned() {
		return tickBurn < tickBurnDuration;
	}
	
	public boolean isChilled() {
		return isChilled;
	}

	public boolean isFrozen() {
		return tickFrozen < tickFrozenDuration;
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
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setChilled(boolean b) {
		isChilled = b;
	}
}
