package com.vtuberrush.src.objects;

import static com.vtuberrush.src.helpers.Constants.Units.POMU;
import static com.vtuberrush.src.helpers.Constants.Units.FINANA;
import static com.vtuberrush.src.helpers.Constants.Units.ELIRA;
import static com.vtuberrush.src.helpers.Constants.Units.ROSEMI;
import static com.vtuberrush.src.helpers.Constants.Units.PETRA;
import static com.vtuberrush.src.helpers.Constants.Units.SELEN;

/**
 * A <b><i>unit</i></b> is a superclass that interacts with any <b><i>enemy</i></b>
 * to prevent it from reaching the end of the level. <br>
 * A unit can be created, upgraded, and deleted by the user in order to defend waves
 * of enemies and complete the game.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Unit {
	private int x, y, id, damage, unitType, tickCooldown;
	private float range, cooldown;
	private int level;
	private int tickHasteCooldown = 960;
	private int tickHasteDuration = 360;
	private int tickHaste = tickHasteCooldown;
	
	/**
	 * Creates a new unit with the passed unit type in the set location.
	 * @param x - Defines the vertical position of the unit from the left of the window in pixels rounded to the nearest tile.
	 * @param y - Defines the horizontal position of the unit from the top of the window in pixels rounded to the nearest tile.
	 * @param id - A unique identifier for this unit, used as the index in the <b><i>units</i></b> [Unit] array list.
	 * @param unitType - Defines the type of this unit (e.g. Pomu, Finana, Elira).
	 */
	public Unit(int x, int y, int id, int unitType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.unitType = unitType;
		level = 1;
		setDamage();
		setRange();
		setCooldown();
	}

	/**
	 * Runs a set of tick methods.
	 * @see #tickCooldown()
	 */
	public void tick() {
		tickCooldown();
	}
	
	
	/**
	 * Runs a 120Hz timer which is checked and used by other methods for game logic such
	 * as if the unit is ready to fire a projectile <i>(cooldown)</i> and if the unit has
	 * recently been buffed by other support units <i>(haste)</i>.
	 */
	private void tickCooldown() {
		tickCooldown++;
		tickHaste++;
		if (isHasted()) {
			if (tickHaste % 2 == 0) {
				tickCooldown++;
			}
		}
	}
	
	/**
	 * Increases the level of this unit and increments their base stats depending on their unit type.
	 */
	public void addLevel() {
		this.level++;
		switch(unitType) {
		case POMU:
			damage += 5;
			range += 15;
			cooldown -= 7;
			break;
		case FINANA:
			damage += 3;
			range += 15;
			cooldown -= 15;
			break;
		case ELIRA:
			damage += 7;
			range += 10;
			cooldown -= 10;
			break;
		case PETRA:
			damage += 1;
			range += 12;
			break;
		case ROSEMI:
			range += 80;
			break;
		case SELEN:
			damage += 10;
			range += 10;
			cooldown -= 10;
			break;
		default: return;
		}
	}
	
	/**
	 * Returns <b><i>true</i></b> if this unit is ready to fire a projectile or blast in a radius.
	 */
	public boolean isOffCooldown() {
		return tickCooldown > cooldown;
	}
	
	/**
	 * Returns <b><i>true</i></b> if this unit is currently being hasted by <b><i>Rosemi</i></b>.
	 */
	public boolean isHasted() {
		return tickHasteDuration > tickHaste;
	}
	
	/**
	 * Checks if the unit was not recently hasted by <b><i>Rosemi</i></b>. If <b><i>true</i></b>, then the unit is
	 * hasted again for the set duration.
	 */
	public void takeHaste() {
		if (tickHaste > tickHasteCooldown) {
			tickHaste = 0;
		}
	}

	/**
	 * Resets the cooldown of this unit and allows this unit to attack again.
	 */
	public void resetCooldown() {
		tickCooldown = 0;
	}
	
	/**
	 * Checks the base damage of this unit from the <b><i>Constants.Units</i></b> class depending on their type.
	 */
	private void setDamage() {
		damage = com.vtuberrush.src.helpers.Constants.Units.getDamage(unitType);
	}
	
	/**
	 * Checks the base range of this unit from the <b><i>Constants.Units</i></b> class depending on their type.
	 */
	private void setRange() {
		range = com.vtuberrush.src.helpers.Constants.Units.getRange(unitType);
	}
	
	/**
	 * Checks the base cooldown of this unit from the <b><i>Constants.Units</i></b> class depending on their type.
	 */
	private void setCooldown() {
		cooldown = com.vtuberrush.src.helpers.Constants.Units.getCooldown(unitType);
	}

	/**
	 * Returns the horizontal position of this unit from the left of the window in pixels rounded to the nearest tile.
	 * @return {@link #x}
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the vertical position of this unit from the top of the window in pixels rounded to the nearest tile.
	 * @return {@link #y}
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the unique identifier for this unit, used as the index in the <b><i>units</i></b> [Unit] array list.
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the type of this unit.
	 * @return {@link #unitType}
	 */
	public int getUnitType() {
		return unitType;
	}

	/**
	 * Returns the damage of this unit based on their type.
	 * @return {@link #damage}
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Returns the range of this unit based on their type.
	 * @return {@link #range}
	 */
	public float getRange() {
		return range;
	}

	/**
	 * Returns the cooldown or attack speed of this unit based on their type.
	 * @return {@link #cooldown}
	 */
	public float getCooldown() {
		return cooldown;
	}
	
	/**
	 * Returns the current level of this unit.
	 * @return {@link #level}
	 */
	public int getLevel() {
		return level;
	}
}
