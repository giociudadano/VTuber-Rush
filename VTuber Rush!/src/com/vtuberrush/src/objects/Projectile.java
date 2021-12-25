package com.vtuberrush.src.objects;

import java.awt.geom.Point2D;

public class Projectile {
	
	private Point2D.Float position;
	private int id, projectileType;
	private boolean active = true;
	
	public Projectile (float x, float y, int id, int projectileType) {
		position = new Point2D.Float(x, y);
		this.id = id;
		this.projectileType = projectileType;
	}
	
	public void move (float x, float y) {
		position.x += x;
		position.y += y;
	}

	public Point2D.Float getPosition() {
		return position;
	}

	public void setPosition(Point2D.Float position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public int getProjectileType() {
		return projectileType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
