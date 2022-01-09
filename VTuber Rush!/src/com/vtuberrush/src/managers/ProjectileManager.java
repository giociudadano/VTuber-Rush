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

import static com.vtuberrush.src.helpers.Constants.Units.POMU;
import static com.vtuberrush.src.helpers.Constants.Units.FINANA;
import static com.vtuberrush.src.helpers.Constants.Units.ELIRA;
import static com.vtuberrush.src.helpers.Constants.Units.SELEN;

import static com.vtuberrush.src.helpers.Constants.Projectiles.POMU_PROJ;
import static com.vtuberrush.src.helpers.Constants.Projectiles.FINANA_PROJ;
import static com.vtuberrush.src.helpers.Constants.Projectiles.ELIRA_PROJ;
import static com.vtuberrush.src.helpers.Constants.Projectiles.SELEN_PROJ;

import com.vtuberrush.src.helpers.MathFunctions;

/**
 * Facilitates the rendering, adding, and removing of projectiles
 * in the playing field.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class ProjectileManager {
	
	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[][] projectileSprites;
	private BufferedImage[] explosionSprites;
	private int animationIndex, animationDelay;
	private int id = 0;
	
	/**
	 * Initializes the sprites and animations of projectiles.
	 * Created when initializing the <b>PLAYING</b> game state.
	 * @param playing - Defines the specific instance of the scene where the wave manager is initialized.
	 * @see #loadProjectiles()
	 */
	public ProjectileManager(Playing playing) {
		this.playing = playing;
		loadProjectiles();
	}
	
	/**
	 * Initializes the sprites and animations of projectiles.
	 */
	private void loadProjectiles() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		
		projectileSprites = new BufferedImage[6][8];
		for(int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				projectileSprites[i][j] = atlas.getSubimage(16 * j, 256 + (16 * i), 16, 16);
			}
		}
		
		explosionSprites = new BufferedImage[8];
		for(int i = 0; i < 8; i++) {
			explosionSprites[i] = atlas.getSubimage(32 * i, 224, 32, 32);
		}
	}
	
	/**
	 * Checks whether the <b><i>unit</i></b> is within range of an enemy, and creates a projectile in the direction of
	 * the enemy if <b><i>true</i></b>.<br>
	 * Calculates the velocity and rotation of the projectile being fired.<p>
	 * 
	 * @param unit - The unit creating the projectile. Defines the projectile type.
	 * @param enemy - The enemy being fired at by the projectile.
	 */
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
	
	/**
	 * Updates the position and animation of all projectiles and detects if a projectile collides with an enemy.<br>
	 * Creates an explosion in the collision if the projectile was fired by <b><i>Elira</i></b>.
	 */
	public void tick() {
		tickAnimation();
		for (Projectile projectile : projectiles) {
			if (projectile.isActive()) {
				projectile.move();
				if(isHit(projectile)) {
					projectile.setActive(false);
					if(projectile.getProjectileType() == ELIRA_PROJ) {
						explosions.add(new Explosion(projectile.getPosition()));
						explodeProjectile(projectile);
					}
				} else if (isOutOfBounds(projectile)) {
					projectile.setActive(false);
				}
			}
		}
		
		for (Explosion explosion : explosions) {
			if (explosion.getIndex() < 8) {
				explosion.tick();
			}
		}
	}
	
	/**
	 * Updates the animation of all projectiles.
	 */
	private void tickAnimation() {
		animationDelay = (animationDelay + 1) % 6;
		if(animationDelay == 5) {
			animationIndex = (animationIndex + 1) % 8;
		}
	}
	
	/**
	 * Checks the passed projectile for possible enemy collision, applying special effects
	 * <i>Burn <b>(Selen)</b></i> and <i>Slow <b>(Finana)</b></i> if so depending on the initial source.
	 * @param projectile - The current projectile being checked.
	 * @return <b><i>true</i></b> - The current projectile hits an enemy. 
	 * Used by other methods to set the projectile as <i>inactive</i>.
	 */
	private boolean isHit(Projectile projectile) {
		for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
			if (enemy.isAlive()) {
				if(enemy.getBounds().contains(projectile.getPosition())) {
					enemy.takeDamage(projectile.getDamage());
					if(projectile.getProjectileType() == FINANA_PROJ) {
						enemy.takeSlow();
					} else if (projectile.getProjectileType() == SELEN_PROJ) {
						enemy.takeBurn();
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks the passed projectile if it is out of bounds. Used by other methods to set the projectile as <i>inactive</i> if true.
	 * @param projectile - The current projectile being checked.
	 * @return <b><i>true</i></b> - The projectile is out of bounds.
	 */
	private boolean isOutOfBounds(Projectile projectile) {
		if (projectile.getPosition().x > 0) {
			if (projectile.getPosition().x < 1280) {
				if (projectile.getPosition().y > 0) {
					if (projectile.getPosition().y < 720) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Damages all enemies within the radius of the explosion.
	 * @param projectile - The source of the explosion. Created by <b><i>Elira</i></b> when her projectiles
	 * collide with an enemy. 
	 */
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

	/**
	 * Renders the image of all projectiles based on their velocity, rotation, and type.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	public void draw(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		for (Projectile projectile : projectiles) {
			if (projectile.isActive()) {
				AffineTransform oldXForm = graphics2d.getTransform();
				graphics2d.translate(projectile.getPosition().x, projectile.getPosition().y);
				graphics2d.rotate(Math.toRadians(projectile.getRotation()));
				graphics2d.drawImage(projectileSprites[projectile.getProjectileType()][getAnimationIndex()], -8, -8, null);
				graphics2d.rotate(-Math.toRadians(-projectile.getRotation()));
				graphics2d.translate(-projectile.getPosition().x, -projectile.getPosition().y);
				graphics2d.setTransform(oldXForm);
			}
		}
		drawExplosions(graphics2d);	
	}
	
	/**
	 * Creates an animated explosion in the location of the projectile.
	 * @param graphics2d - Responsible for drawing objects to the screen.
	 */
	private void drawExplosions(Graphics2D graphics2d) {
		for (Explosion explosion : explosions) {
			if (explosion.getIndex() < 8) {
				graphics2d.drawImage(explosionSprites[explosion.getIndex()], (int) explosion.getPosition().x-16,
						(int) explosion.getPosition().y-16, null);
			}
		}
	}
	
	/**
	 * Removes all projectiles and explosions on the playing field.
	 * <br> Called during a game over or game complete condition.
	 */
	public void resetGame() {
		projectiles.clear();
		explosions.clear();
		id = 0;
	}

	/**
	 * Returns the type of projectile being created depending on the source of the projectile.
	 * @param unit - The source of the projectile.
	 * @return <b>type</b> - The type of projectile being created.
	 */
	private int getProjectileType(Unit unit) {
		switch(unit.getUnitType()) {
		case POMU: return POMU_PROJ;
		case FINANA: return FINANA_PROJ;
		case ELIRA: return ELIRA_PROJ;
		case SELEN: return SELEN_PROJ;
		default: return 0;
		}
	}
	
	/**
	 * Returns the current index of all projectile animations.
	 * @return {@link #animationIndex}
	 */
	private int getAnimationIndex() {
		return animationIndex;
	}
	
	/**
	 * A unique, stationary projectile effect created by <b><i>Elira</i></b> when her
	 * original projectiles collide with an enemy.<br>
	 * An <b><i>explosion</i></b> contains information related to its position,
	 * current lifetime and own animation index.
	 * 
	 * @author Gio Carlo Ciudadano
	 * @version 0.0.1-alpha.1
	 */
	public class Explosion {
		
		private Point2D.Float position;
		private int explosionTick = 0, explosionIndex = 0;
		
		/**
		 * Creates an explosion in the location of the collided projectile.
		 * @param position - Defines the position of the explosion.
		 */
		public Explosion(Point2D.Float position) {
			this.position = position;
		}
		
		/**
		 * Updates the animation and lifetime of the explosion.
		 */
		public void tick() {
			explosionTick = (explosionTick + 1) % 12;
			if(explosionTick == 11) {
				explosionIndex++;
			}
		}
		
		/**
		 * Returns the current index of the animation and lifetime of the explosion.
		 * @return {@link #explosionIndex}
		 */
		public int getIndex() {
			return explosionIndex;
		}
		
		/**
		 * Returns the current position of the explosion.
		 * @return {@link #position}
		 */
		public Point2D.Float getPosition(){
			return position;
		}
	}

}
