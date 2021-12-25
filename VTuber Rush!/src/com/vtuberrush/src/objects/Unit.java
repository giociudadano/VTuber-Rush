package com.vtuberrush.src.objects;

public class Unit {
	private int x, y, id, unitType;
	private float damage, range, cooldown;
	
	public Unit(int x, int y, int id, int unitType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.unitType = unitType;
		setDamage();
		setRange();
		setCooldown();
	}

	private void setDamage() {
		damage = com.vtuberrush.src.helpers.Constants.Units.getDamage(unitType);
	}
	
	private void setRange() {
		damage = com.vtuberrush.src.helpers.Constants.Units.getRange(unitType);
	}
	
	private void setCooldown() {
		damage = com.vtuberrush.src.helpers.Constants.Units.getCooldown(unitType);
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

	public float getDamage() {
		return damage;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}
}
