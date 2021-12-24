package com.vtuberrush.src.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
	
	public void addUnit(Unit selectedUnit, int x, int y) {
		units.add(new Unit(x, y, id++, selectedUnit.getUnitType()));
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
