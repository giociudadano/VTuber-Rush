package com.vtuberrush.src.objects;

import static com.vtuberrush.src.helpers.Constants.Units.*;

public class Unit {
	private int x, y, id, damage, unitType, tickCooldown;
	private float range, cooldown;
	private int level;
	private int tickHasteBuffer = 720;
	private int tickHasteCooldown = 360;
	private int tickHaste = tickHasteCooldown;
	
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

	public void tick() {
		tickCooldown();
	}
	
	private void tickCooldown() {
		tickCooldown++;
		tickHaste++;
		if (isHasted()) {
			if (tickHaste % 2 == 0) {
				tickCooldown++;
			}
		}
	}

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
		case ROSEMI:
			range += 60;
		case SELEN:
			damage += 10;
			range += 10;
			cooldown -= 10;
			break;
		default: return;
		}
	}
	
	public boolean isOffCooldown() {
		return tickCooldown > cooldown;
	}
	
	public boolean isHasted() {
		return tickHasteCooldown > tickHaste;
	}
	
	public void takeHaste() {
		if (tickHaste > tickHasteBuffer) {
			tickHaste = 0;
		}
	}

	public void resetCooldown() {
		tickCooldown = 0;
	}
	
	private void setDamage() {
		damage = com.vtuberrush.src.helpers.Constants.Units.getDamage(unitType);
	}
	
	private void setRange() {
		range = com.vtuberrush.src.helpers.Constants.Units.getRange(unitType);
	}
	
	private void setCooldown() {
		cooldown = com.vtuberrush.src.helpers.Constants.Units.getCooldown(unitType);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public int getUnitType() {
		return unitType;
	}

	public int getDamage() {
		return damage;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}
	
	public int getLevel() {
		return level;
	}
}
