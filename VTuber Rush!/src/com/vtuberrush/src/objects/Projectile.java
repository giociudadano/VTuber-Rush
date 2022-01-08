package com.vtuberrush.src.objects;

import java.awt.geom.Point2D;

/**
 * A <b><i>projectile</i></b> is a superclass that interacts with any <b><i>enemy</i></b>
 * to reduce their health and apply enemy effects.<br>
 * A projectile is created from a <b><i>unit</i></b> when it is off cooldown and when an enemy is within range.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Projectile {
	
	private Point2D.Float position;
	private int id, projectileType, damage;
	private float xSpeed, ySpeed, rotation;
	private boolean active = true;
	
	/**
	 * Creates a projectile from the location of the <b><i>unit</i></b> which travels in a set velocity to the <b><i>enemy</i></b>
	 * within range.<br>
	 * The projectile deals a set amount of damage when colliding with an enemy and applies special effects depending on
	 * the passed projectile type.
	 * @param x - Defines the initial vertical position of the unit from the left of the window in pixels.
	 * @param y - Defines the initial horizontal position of the unit from the top of the window in pixels.
	 * @param xSpeed - Defines the horizontal velocity of the projectile. Dependent on projectile type.
	 * @param ySpeed - Defines the vertical velocity of the projectile. Dependent on projecitle type.
	 * @param damage - Defines the amount of damage the projectile with deal to an enemy. Dependent on projectile type.
	 * @param rotation - Defines the rotation of the image of the projectile in degrees. Used in rendering the direction of the projectile.
	 * @param id - Defines the unique identifier of the projectile.
	 * @param projectileType - Defines the type or source of the projectile. Varies the velocity and damage of different projectile types.
	 */
	public Projectile(float x, float y, float xSpeed, float ySpeed, int damage, float rotation, int id, int projectileType) {
		position = new Point2D.Float(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.damage = damage;
		this.rotation = rotation;
		this.id = id;
		this.projectileType = projectileType;
	}
	
	/**
	 * Updates the position of the projectile based on the set velocity.
	 */
	public void move() {
		position.x += xSpeed;
		position.y += ySpeed;
	}

	/**
	 * Returns the position of the projectile.
	 * @return <b>position</b> - Defines the horizontal and vertical position of the projectile.
	 */
	public Point2D.Float getPosition() {
		return position;
	}

	/**
	 * Sets the position of the projectile.
	 * @param position - Defines the horizontal and vertical position of the projectile.
	 */
	public void setPosition(Point2D.Float position) {
		this.position = position;
	}

	/**
	 * Returns the unique identifier of the projectile.
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the type or source of the projectile.
	 * @return {@link #projectileType}
	 */
	public int getProjectileType() {
		return projectileType;
	}

	/**
	 * Returns <b><i>true</i></b> if the projectile has not collided with an enemy.
	 * @return {@link #active}
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the state of the projectile to whether it has not collided with an enemy.
	 * @param active - Defines whether the projectile has not collided with an enemy.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Returns the amount of damage the projectile will deal upon colliding with an enemy.
	 * @return {@link #damage}
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Returns the current angle of rotation of the image of the projectile in degrees.
	 * @return {@link #rotation}
	 */
	public float getRotation() {
		return rotation;
	}
	
}
