package com.vtuberrush.src.managers;

import java.awt.Graphics;
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
		
		int xDistance = (int) Math.abs(unit.getX()-enemy.getX());
		int yDistance = (int) Math.abs(unit.getY()-enemy.getY());
		int distance = xDistance + yDistance;
		
		float xPercent = (float) xDistance / distance;
		
		float xSpeed = xPercent * com.vtuberrush.src.helpers.Constants.Projectiles.getSpeed(type);
		float ySpeed = com.vtuberrush.src.helpers.Constants.Projectiles.getSpeed(type) - xSpeed;
		
		if (unit.getX() > enemy.getX()) {
			xSpeed *= -1;
		}
		
		if (unit.getY() > enemy.getY()) {
			ySpeed *= -1;
		}
		
		projectiles.add(new Projectile(unit.getX()+8, unit.getY()+8, xSpeed, ySpeed, unit.getDamage(), id++, type));
		
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
		for (Projectile projectile : projectiles) {
			if (projectile.isActive()) {
				graphics.drawImage(projectileSprites[projectile.getProjectileType()], (int) projectile.getPosition().x, 
						(int) projectile.getPosition().y, null);
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
