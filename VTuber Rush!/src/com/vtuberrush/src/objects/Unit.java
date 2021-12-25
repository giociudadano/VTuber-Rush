package com.vtuberrush.src.objects;

public class Unit {
	private int x, y, id, damage, unitType, tickCooldown;
	private float range, cooldown;
	
	public Unit(int x, int y, int id, int unitType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.unitType = unitType;
		setDamage();
		setRange();
		setCooldown();
	}

	public void tick() {
		tickCooldown++;
	}
	
	public boolean isOffCooldown() {
		return tickCooldown > cooldown;
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
}
