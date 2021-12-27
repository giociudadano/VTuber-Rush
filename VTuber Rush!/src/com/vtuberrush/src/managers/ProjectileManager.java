package com.vtuberrush.src.managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Projectile;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Units.*;
import static com.vtuberrush.src.helpers.Constants.Projectiles.*;

import com.vtuberrush.src.helpers.MathFunctions;

public class ProjectileManager {
	
	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] projectileSprites, explosionSprites;
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
		
		explosionSprites = new BufferedImage[8];
		for(int i = 0; i < 8; i++) {
			explosionSprites[i] = atlas.getSubimage(32 * i, 224, 32, 32);
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
					if(projectile.getProjectileType() == ELIRA_PROJ) {
						explosions.add(new Explosion(projectile.getPosition()));
						explodeProjectile(projectile);
					}
				}
			}
		}
		
		for (Explosion explosion : explosions) {
			if (explosion.getIndex() < 8) {
				explosion.tick();
			}
		}
	}
	
	

	private boolean isHit(Projectile projectile) {
		for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
			if (enemy.isAlive()) {
				if(enemy.getBounds().contains(projectile.getPosition())) {
					enemy.takeDamage(projectile.getDamage());
					if(projectile.getProjectileType() == FINANA_PROJ) {
						enemy.takeSlow();
					}
					return true;
				}
			}
		}
		return false;
	}
	
	private void explodeProjectile(Projectile projectile) {
		for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
			if (enemy.isAlive()) {
				float distance = MathFunctions.getDistance(projectile.getPosition().x,
						projectile.getPosition().y, enemy.getX(), enemy.getY());
				if(distance <= 48) {
					enemy.takeDamage(projectile.getDamage()/2);
				}
			}
		}
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
		drawExplosions(graphics2d);	
	}
	
	private void drawExplosions(Graphics2D graphics2d) {
		for (Explosion explosion : explosions) {
			if (explosion.getIndex() < 8) {
				graphics2d.drawImage(explosionSprites[explosion.getIndex()], (int) explosion.getPosition().x-16,
						(int) explosion.getPosition().y-16, null);
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
	
	public class Explosion {
		
		private Point2D.Float position;
		private int explosionTick = 0, explosionIndex = 0;
		
		public Explosion(Point2D.Float position) {
			this.position = position;
		}
		
		public void tick() {
			explosionTick = (explosionTick + 1) % 12;
			if(explosionTick == 11) {
				explosionIndex++;
			}
		}
		
		public int getIndex() {
			return explosionIndex;
		}
		
		public Point2D.Float getPosition(){
			return position;
		}
	}
}
