package com.vtuberrush.src.managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Projectile;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Units.*;
import static com.vtuberrush.src.helpers.Constants.Projectiles.*;

public class ProjectileManager {
	
	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private BufferedImage[] projectileSprites;
	private int id = 0;
	
	
	public ProjectileManager(Playing playing) {
		this.playing = playing;
		loadProjectiles();
	}
	
	private void loadProjectiles() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		projectileSprites = new BufferedImage[3];
		for(int i = 0; i < 3; i++) {
			projectileSprites[i] = atlas.getSubimage(16 * i, 208, 16, 16);
		}
	}
	
	public void addProjectile(Unit unit, Enemy enemy) {
		int type = getProjectileType(unit);
		
		int xDistance = (int) (unit.getX()-enemy.getX());
		int yDistance = (int) (unit.getY()-enemy.getY());
		int distance = Math.abs(xDistance) + Math.abs(yDistance);
		
		float xPercent = (float) Math.abs(xDistance) / distance;
		
		float xSpeed = xPercent * com.vtuberrush.src.helpers.Constants.Projectiles.getSpeed(type);
		float ySpeed = com.vtuberrush.src.helpers.Constants.Projectiles.getSpeed(type) - xSpeed;
		
		if (unit.getX() > enemy.getX()) {
			xSpeed *= -1;
		}
		
		if (unit.getY() > enemy.getY()) {
			ySpeed *= -1;
		}
	
		float rotation = (float) Math.toDegrees((float) Math.atan(yDistance / (float) xDistance));
		
		if (xDistance < 0) {
			rotation += 180;
		}
		
		projectiles.add(new Projectile(unit.getX()+16, unit.getY()+16, xSpeed, ySpeed, unit.getDamage(), rotation, id++, type));
		
	}

	public void tick() {
		for (Projectile projectile : projectiles) {
			if (projectile.isActive()) {
				projectile.move();
				if(isHit(projectile)) {
					projectile.setActive(false);
				}
			}
		}
	}
	
	private boolean isHit(Projectile projectile) {
		for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
			if(enemy.getBounds().contains(projectile.getPosition())) {
				enemy.takeDamage(projectile.getDamage());
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		for (Projectile projectile : projectiles) {
			if (projectile.isActive()) {
				AffineTransform oldXForm = graphics2d.getTransform();
				graphics2d.translate(projectile.getPosition().x, projectile.getPosition().y);
				graphics2d.rotate(Math.toRadians(projectile.getRotation()));
				graphics2d.drawImage(projectileSprites[projectile.getProjectileType()], -8, -8, null);
				graphics2d.rotate(-Math.toRadians(-projectile.getRotation()));
				graphics2d.translate(-projectile.getPosition().x, -projectile.getPosition().y);
				graphics2d.setTransform(oldXForm);
			}
		}
		
	}
	
	private int getProjectileType(Unit unit) {
		switch(unit.getUnitType()) {
		case POMU: return POMU_PROJ;
		case FINANA: return FINANA_PROJ;
		case ELIRA: return ELIRA_PROJ;
		default: return 0;
		}
	}

}
