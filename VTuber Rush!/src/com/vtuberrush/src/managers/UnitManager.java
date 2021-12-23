package com.vtuberrush.src.managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

import static com.vtuberrush.src.helpers.Constants.Units.*;

public class UnitManager {
	
	private Playing playing;
	private BufferedImage[] unitSprites;
	private BufferedImage[] unitIcons;
	private Unit unit;
	
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
	}
	
	public BufferedImage[] getUnitIcons() {
		return unitIcons;
	}
}
