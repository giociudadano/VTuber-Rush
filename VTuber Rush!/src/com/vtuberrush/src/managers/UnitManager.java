package com.vtuberrush.src.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Units.*;

public class UnitManager {
	
	private Playing playing;
	private BufferedImage[] unitSprites;
	private BufferedImage[] unitIcons;
	private BufferedImage effectHasted;
	private ArrayList<Unit> units = new ArrayList<>();
	private int id = 0;
	
	public UnitManager(Playing playing) {
		this.playing = playing;
		loadEffects();
		loadUnits();
	}

	public void tick() {
		for(Unit unit : units) {
			unit.tick();
			attackEnemy(unit);
			buffUnits(unit);
		}
	}

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

	private void buffUnits(Unit unit) {
		if (unit.getUnitType() == ROSEMI) {
			for (Unit u: units) {
				if (isNearby(unit, u)) {
					u.takeHaste();
				}
			}
		}
	}

	public boolean isNearby(Unit unit, Enemy enemy) {
		int range = com.vtuberrush.src.helpers.MathFunctions.getDistance(unit.getX(), unit.getY(), enemy.getX(), enemy.getY());
		return range < unit.getRange()/2;
	}
	
	private boolean isNearby(Unit unit, Unit unit2) {
		int range = com.vtuberrush.src.helpers.MathFunctions.getDistance(unit.getX(), unit.getY(), unit2.getX(), unit2.getY());
		return range < unit.getRange()/4;
	}
	
	private void loadEffects() {
		effectHasted = LoadSave.getSpriteAtlas().getSubimage(192, 16, 16, 16);
	}

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
