package com.vtuberrush.src.enemies;

import java.awt.Rectangle;

import com.vtuberrush.src.managers.EnemyManager;

import static com.vtuberrush.src.helpers.Constants.Direction.UP;
import static com.vtuberrush.src.helpers.Constants.Direction.DOWN;
import static com.vtuberrush.src.helpers.Constants.Direction.LEFT;
import static com.vtuberrush.src.helpers.Constants.Direction.RIGHT;

/**
 * An <b><i>enemy</i></b> is a superclass object that moves around the level from a defined <i>start</i> flag
 * to an <i>end</i> flag both faciliated by the <b>EnemyManager</b> and <b>WaveManager</b>.<br>
 * An enemy interacts with a <b><i>unit</i></b> or <b><i>projectile</i></b> to recieve damage and debuffs
 * depending on the source. <p>
 * If an enemy's health is zero, then the player is rewarded an amount of gold depending on the subclass object <br>
 * If an enemy reaches the <i>end</i> flag, the player is subtracted health and is checked for a game end condition.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public abstract class Enemy {
	
	/**
	 * Defines the horizontal position of this enemy from the left of the window in pixels.
	 */
	protected float x;
	
	/**
	 * Defines the vertical position of this enemy from the top of the window in pixels.
	 */
	protected float y;
	
	/**
	 * Defines the hitbox or the four rectangular coordinates (x, y) of this enemy from the
	 * top and left of the window in pixels. Used for detecting projectile collision.
	 */
	protected Rectangle bounds;
	
	/**
	 * Defines whether the enemy is currently alive.
	 */
	protected boolean alive = true;
	
	/**
	 * Defines the current health of this enemy, reducable by taking damage.
	 */
	protected int health;
	
	/**
	 * Defines the total maximum health of this enemy.
	 */
	protected int maxHealth;
	
	/**
	 * A unique identifier for this unit, used as a unique identifier for the <b><i>enemies</i></b> [Enemy] array list.
	 */
	protected int id;
	
	/**
	 * Defines the subclass object or type of this enemy.
	 */
	protected int enemyType;
	
	/**
	 * Facilitates the rendering and pathfinding of enemies.
	 */
	protected EnemyManager enemyManager;
	
	/**
	 * Defines the current direction of where this enemy is currently travelling.
	 */
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


	/**
	 * Spawns a new enemy at the defined location or where the <i>start</i> flag is located depending on the
	 * current wave number and current order of enemies in that wave facilitated by the
	 * <b>EnemyManager</b>.<p>
	 * Sets the health of this enemy upon spawning depending on the enemy type.
	 * @param x - Defines the horizontal position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param y - Defines the vertical position of where the enemy will spawn or where the <i>start</i> flag is located.
	 * @param id - A unique identifier for this enemy.
	 * @param enemyType - Defines the subclass object or type of enemy that will be spawned (e.g. Green Slime, Blue Slime).
	 * @param enemyManager - Facilitates the spawning of this enemy.
	 */
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
	
	/**
	 * Sets the health of this enemy depending on the enemy type.
	 */
	private void setMaxHealth() {
		maxHealth = com.vtuberrush.src.helpers.Constants.Enemies.getMaxHealth(enemyType);
		health = maxHealth;
	}
	
	/**
	 * Reduces the health of this enemy by the passed amount.
	 * @param damage - The amount of health to be reduced.
	 */
	public void takeDamage(int damage) {
		this.health -= damage;
		if (health <= 0) {
			alive = false;
			enemyManager.addGold(enemyType);
		}
	}
	
	/**
	 * Applies a <i><b>Slow</b> (Elira)</i> effect to this enemy for 2.5 seconds, reducing its movement speed by 60%. <br>
	 * Called when a <b><i>projectile</i></b> whose source is <i>Elira</i> collides with this enemy.
	 */
	public void takeSlow() {
		tickSlow = 0;
	}
	
	/**
	 * Applies a <i><b>Burn</b> (Selen)</i> effect to this enemy for 4 seconds, reducing its health over time.<br>
	 * Called when a <b><i>projectile</i></b> whose source is <i>Selen</i> collides with this enemy.
	 */
	public void takeBurn() {
		tickBurn = 0;
	}
	
	/**
	 * Applies a <i><b>Chilling</b> (Petra)</i> effect to this enemy while active, reducing its movement speed by 25%. <br>
	 * Called when this enemy is near Petra.
	 * @param chilled - Defines if the enemy is currently chilled.
	 */
	public void setChilled(boolean chilled) {
		isChilled = chilled;
	}
	
	/**
	 * Applies a <i><b>Frozen</b> (Petra)</i> effect to this enemy for 0.75 seconds, reducing its movement speed by 100%. <br>
	 * Called when this unit is near <i>Petra</i> and has not recently been frozen in the last 5 seconds.
	 */
	public void takeFrozen() {
		if (tickFrozen >= tickFrozenCooldown) {
			tickFrozen = 0;
		}
	}
	
	/**
	 * Instantly remove this enemy from the playing field.
	 * Called when an enemy collides with an <i>end</i> flag.
	 */
	public void takePurge() {
		alive = false;
		health = 0;
	}
	
	/**
	 * Moves this enemy in the currently set direction by the <b>EnemyManager</b> and in a set speed depending on their enemy type.
	 * Adjusts the hitbox of this unit when moving and applies all existing effects to this unit.<br>
	 * The direction of this enemy changes when colliding with a non-<i>Road</i> tile.<br>
	 * The speed of this enemy changes if this enemy is currently <i><b>Slowed</b> (Finana)</i>, <i><b>Chilled</b></i>,
	 * or <i><b>Frozen</b> (Petra)</i>.
	 * <p><i>Defaults to the right direction.</i>
	 * @param speed - The current speed of this enemy. Dependent on existing enemy effects and the enemy type.
	 * @param directionMove - The current direction of this enemy. Changes when colliding with a non-<i>Road</i> tile.
	 * @see #tickBurn()
	 */
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
	
	/**
	 * Reduces the health of this enemy by a flat amount and a portion of the target's maximum health every 0.5 seconds.
	 */
	private void tickBurn() {
		if (tickBurn < tickBurnDuration) {
			tickBurn++;
			tickBurnCooldown = (tickBurnCooldown + 1) % 60;
			if (tickBurnCooldown == 0) {
				takeDamage((int)(5 + this.maxHealth * 0.005));
			}
		}
	}

	/**
	 * Adjusts the hitbox of this enemy for detecting <b><i>projectile</i></b> collision, called when the enemy is currently moving.
	 */
	private void tickHitbox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}

	/**
	 * Returns <b><i>true</i></b> if this enemy is currently alive.
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Returns <b><i>true</i></b> if this enemy is currently <i><b>Slowed</b> (Finana)</i>.
	 */
	public boolean isSlowed() {
		return tickSlow < tickSlowDuration;
	}
	
	/**
	 * Returns <b><i>true</i></b> if this enemy is currently <i><b>Burning</b> (Selen)</i>.
	 */
	public boolean isBurned() {
		return tickBurn < tickBurnDuration;
	}
	
	/**
	 * Returns <b><i>true</i></b> if this enemy is currently <i><b>Chilled</b> (Petra)</i>.
	 */
	public boolean isChilled() {
		return isChilled;
	}

	/**
	 * Returns <b><i>true</i></b> if this enemy is currently <i><b>Frozen</b> (Petra)</i>.
	 */
	public boolean isFrozen() {
		return tickFrozen < tickFrozenDuration;
	}
	
	/**
	 * Returns the horizontal position of this enemy from the left of the window in pixels.
	 * @return {@link #x}
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the vertical position of this enemy from the top of the window in pixels.
	 * @return {@link #y}
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Returns the hitbox of this enemy, defined as four rectangular coordinates (x, y) from
	 * the top and left of the window in pixels.<br>
	 * Called when detecting for projectile collision.
	 * @return {@link #bounds}
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Returns the current health of this enemy.
	 * @return {@link #health}
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Returns the current health of this enemy as a percent of its maximum health.<br>
	 * Called when updating the health bar of this enemy.
	 * @return <b>% HP</b> - The amount of percent health this enemy has remaining.
	 */
	public float getHealthPercent() {
		return health / (float) maxHealth;
	}

	/**
	 * Returns the unique identifier for this unit, used as a unique identifier for the <b><i>enemies</i></b> [Enemy] array list.
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the enemy type of this unit.
	 * @return {@link #enemyType}
	 */
	public int getEnemyType() {
		return enemyType;
	}
	
	/**
	 * Returns the current direction of this unit.
	 * @return {@link #direction}
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Modifies the position of this unit to the passed (x, y) coordinates. Called only when a <i>start</i> flag
	 * exists outside a road tile.
	 * <p><b>Warning:</b><br> Do not use when dynamically updating the position of all units.
	 * @param x - Defines the horizontal position of where this enemy is located.
	 * @param y - Defines the vertical position of where this enemy is located.
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
