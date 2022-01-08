package com.vtuberrush.src.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Units.ROSEMI;
import static com.vtuberrush.src.helpers.Constants.Units.PETRA;

/**
 * Facilitates the rendering, targeting, placing, deleting and
 * other methods related to interacting with units.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class UnitManager {
	
	private Playing playing;
	private BufferedImage[] unitSprites;
	private BufferedImage[] unitIcons;
	private BufferedImage effectHasted;
	private ArrayList<Unit> units = new ArrayList<>();
	private int id = 0;
	
	/**
	 * Initializes the list of units and unit effects.
	 * Created when initializing the <b>PLAYING</b> game state.
	 * @param playing - Defines the specific instance of the scene where the unit manager is initialized.
	 * @see #loadEffects()
	 * @see #loadUnits()
	 */
	public UnitManager(Playing playing) {
		this.playing = playing;
		loadEffects();
		loadUnits();
	}
	
	/**
	 * Initializes rendering of unit effects such as <i>Haste <b>(Rosemi)</b></i>.
	 */
	private void loadEffects() {
		effectHasted = LoadSave.getSpriteAtlas().getSubimage(192, 16, 16, 16);
	}

	/**
	 * Initializes rendering of unit sprites (playing field) and unit icons (action bar).
	 */
	private void loadUnits() {
		BufferedImage spriteAtlas = LoadSave.getSpriteAtlas();
		BufferedImage iconAtlas = LoadSave.getUnitIconsAtlas();
		unitSprites = new BufferedImage[6];
		unitIcons = new BufferedImage[6];
		for (int i = 0; i < 6; i++) {
			unitSprites[i] = spriteAtlas.getSubimage(i*32, 160, 32, 48);
			unitIcons[i] = iconAtlas.getSubimage(i*112, 0, 112, 136);
		}
		
	}
	
	/**
	 * Updates all units on the field and checks if the unit is ready to attack an enemy
	 * or buff an ally <b><i>(Rosemi)</i></b>.
	 */
	public void tick() {
		for(Unit unit : units) {
			unit.tick();
			attackEnemy(unit);
			buffUnits(unit);
		}
	}

	/**
	 * Checks if the enemy is within range of the passed unit, for which the passed unit attacks the
	 * enemy if <b><i>true</i></b>.
	 * <br>If the passed unit is <b><i>Petra</i></b>, nearby enemies are chilled, frozen, and damaged in a radius.
	 * <br>Otherwise, the unit attempts to shoot a projectile towards the enemy.
	 * @param unit - The unit that is attempting to attack the enemy.
	 */
	private void attackEnemy(Unit unit) {
		for(Enemy enemy : playing.getEnemyManager().getEnemies()) {
			if (enemy.isAlive()) {
				if (isNearby(unit, enemy)) {
					int type = unit.getUnitType();
					if(type == PETRA) {
						enemy.setChilled(true);
						enemy.takeFrozen();
					}
					if(unit.isOffCooldown()) {
						if (type == PETRA) {
							playing.blastEnemy(unit, enemy);
						} else if (type != ROSEMI) {
							playing.shootEnemy(unit, enemy);
						}
						unit.resetCooldown();
					}
				} else {
					enemy.setChilled(false);
				}
			}
		}
	}
	
	/**
	 * Checks if the passed unit is <b><i>Rosemi</i></b>, for which the passed unit grants <b><i>haste</i></b>
	 * to all nearby allies if <b><i>true</i></b>.
	 * @param unit - The unit that is attempting to buff other units.
	 */
	private void buffUnits(Unit unit) {
		if (unit.getUnitType() == ROSEMI) {
			for (Unit u: units) {
				if (isNearby(unit, u)) {
					u.takeHaste();
				}
			}
		}
	}

	/**
	 * Uses the distance formula to check if the enemy can be attacked.
	 * @param unit - The unit that is attempting to attack the enemy.
	 * @param enemy - The enemy being attacked.
	 * @return <b><i>true</i></b> - The enemy is within range of the passed unit.
	 */
	public boolean isNearby(Unit unit, Enemy enemy) {
		int range = com.vtuberrush.src.helpers.MathFunctions.getDistance(unit.getX(), unit.getY(), enemy.getX(), enemy.getY());
		return range < unit.getRange()/2;
	}
	
	/**
	 * Uses the distance formula to check if the ally can be buffed.
	 * @param unit - The <b><i>Rosemi</i></b> unit buffing other units.
	 * @param unit2 - The ally being buffed.
	 * @return <b><i>true</i></b> - The enemy is within range to be buffed.
	 */
	private boolean isNearby(Unit unit, Unit unit2) {
		int range = com.vtuberrush.src.helpers.MathFunctions.getDistance(unit.getX(), unit.getY(), unit2.getX(), unit2.getY());
		return range < unit.getRange()/4;
	}
	
	/**
	 * Renders all placed units and their effects to the playing field.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	public void draw(Graphics graphics) {
		for (Unit unit : units) {
			if (unit != null) {
				graphics.setColor(new Color(0, 0, 0, 50));
				graphics.fillOval(unit.getX()+2, unit.getY()+12, 28, 8);
				graphics.drawImage(unitSprites[unit.getUnitType()], unit.getX(), unit.getY()-32, null);
				drawEffects(graphics, unit);
			}
		}
	}
	
	/**
	 * Renders unit effects such as <i>Haste <b>(Rosemi)</b></i> and area efects
	 * such as the <i>Haste Radius <b>(Rosemi)</b></i> and <i>Slowing Field <b>(Petra)</b></i>.
	 * @param graphics - Responsible for drawing objects to the screen.
	 * @param unit - The unit being affected by a unit effect or affecting using an area effect.
	 */
	private void drawEffects(Graphics graphics, Unit unit) {
		int type = unit.getUnitType();
		if(type == PETRA) {
			graphics.setColor(new Color(214, 245, 255, 100));
			graphics.drawOval(unit.getX()+16 - (int)(unit.getRange()/2),
					unit.getY()+16 - (int)(unit.getRange()/2),
					(int)(unit.getRange()), (int)(unit.getRange()));
		}
		if(unit.isHasted()) {
			if (type != ROSEMI) {
				graphics.drawImage(effectHasted, unit.getX(), unit.getY()-32, 16, 16, null);
			} else {
				graphics.setColor(new Color(255, 214, 227, 100));
				graphics.drawOval(unit.getX()+16 - (int)(unit.getRange()/4),
						unit.getY()+16 - (int)(unit.getRange()/4),
						(int)(unit.getRange()/2), (int)(unit.getRange()/2));
			}
		}
	}
	
	/**
	 * Places a new unit.
	 * @param unit - The unit being placed.
	 * @param x - Defines the horizontal position of the unit from the left of the window in pixels rounded to the nearest tile.
	 * @param y - Defines the vertical position of the unit from the top of the window in pixels rounded to the nearest tile.
	 */
	public void addUnit(Unit unit, int x, int y) {
		units.add(new Unit(x, y, id++, unit.getUnitType()));
	}
	
	/**
	 * Removes the unit.
	 * @param unit - The unit being removed.
	 */
	public void removeUnit(Unit unit) {
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).getId() == unit.getId()) {
				units.remove(i);
			}
		}
	}
	
	/**
	 * Increments the level of the selected unit by one.
	 * @param unit - The unit being upgraded.
	 */
	public void upgradeUnit(Unit unit) {
		for (Unit u : units) {
			if (u.getId() == unit.getId()) {
				u.addLevel();
			}
		}	
	}
	
	/**
	 * Clears all units in the field.
	 * <br> Called during a game over or game complete condition.
	 */
	public void resetGame() {
		units.clear();
		id = 0;
	}
	
	/**
	 * Returns the image atlas of the unit sprites (playing field).
	 * @return {@link #unitSprites}
	 */
	public BufferedImage[] getUnitSprites() {
		return unitSprites;
	}
	
	/**
	 * Returns the image atlas of the unit icons (action bar).
	 * @return {@link #unitIcons}
	 */
	public BufferedImage[] getUnitIcons() {
		return unitIcons;
	}

	/**
	 * Returns the unit in the clicked location in the playing field, if it exists.
	 * <br>Returns <b><i>null</i></b> if the location did not have any unit.
	 * @param x - Defines the horizontal position of the unit from the left of the window in pixels rounded to the nearest tile.
	 * @param y - Defines the vertical position of the unit from the top of the window in pixels rounded to the nearest tile.
	 * @return <b><i>unit</i></b> - The unit being selected in the playing field, if it exists.
	 */
	public Unit getUnitAt(int x, int y) {
		for (Unit unit : units) {
			if (unit.getX() == x) {
				if (unit.getY() == y) {
					return unit;
				}
			}
		}
		return null;
	}

}
