package com.vtuberrush.src.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

public class UnitManager {
	
	private Playing playing;
	private BufferedImage[] unitSprites;
	private BufferedImage[] unitIcons;
	private ArrayList<Unit> units = new ArrayList<>();
	private int id = 0;
	
	public UnitManager(Playing playing) {
		this.playing = playing;
		loadUnits();
	}
	
	public void tick() {
		for(Unit unit : units) {
			unit.tick();
			attackEnemy(unit);
		}
	}

	private void attackEnemy(Unit unit) {
		for(Enemy enemy : playing.getEnemyManager().getEnemies()) {
			if (enemy.isAlive()) {
				if (isNearby(unit, enemy)) {
					if(unit.isOffCooldown()) {
						playing.shootEnemy(unit, enemy);
						unit.resetCooldown();
					}
				}
			}
		}
	}

	private boolean isNearby(Unit unit, Enemy enemy) {
		int range = com.vtuberrush.src.helpers.MathFunctions.getDistance(unit.getX(), unit.getY(), enemy.getX(), enemy.getY());
		return range < unit.getRange()/2;
	}

	private void loadUnits() {
		BufferedImage spriteAtlas = LoadSave.getSpriteAtlas();
		BufferedImage iconAtlas = LoadSave.getUnitIconsAtlas();
		unitSprites = new BufferedImage[3];
		unitIcons = new BufferedImage[3];
		for (int i = 0; i < 3; i++) {
			unitSprites[i] = spriteAtlas.getSubimage(i*32, 160, 32, 48);
			unitIcons[i] = iconAtlas.getSubimage(i*112, 0, 112, 136);
		}
		
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 50));
		for (Unit unit : units) {
			graphics.fillOval(unit.getX()+2, unit.getY()+12, 28, 8);
			graphics.drawImage(unitSprites[unit.getUnitType()], unit.getX(), unit.getY()-32, null);
		}
	}
	
	public void addUnit(Unit unit, int x, int y) {
		units.add(new Unit(x, y, id++, unit.getUnitType()));
	}
	
	public void removeUnit(Unit unit) {
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).getId() == unit.getId()) {
				units.remove(i);
			}
		}
	}
	
	public void upgradeUnit(Unit unit) {
		for (Unit u : units) {
			if (u.getId() == unit.getId()) {
				u.addLevel();
			}
		}	
	}
	
	public void resetGame() {
		units.clear();
		id = 0;
	}
	
	public BufferedImage[] getUnitSprites() {
		return unitSprites;
	}
	
	public BufferedImage[] getUnitIcons() {
		return unitIcons;
	}

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
