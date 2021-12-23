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
	private Unit unit;
	
	public UnitManager(Playing playing) {
		this.playing = playing;
		loadUnits();
		initUnits();
	}
	
	public void tick() {
		
	}
	
	private void initUnits() {
		unit = new Unit(32, 32, 0, POMU);
	}

	private void loadUnits() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		unitSprites = new BufferedImage[1];
		for (int i = 0; i < 1; i++) {
			unitSprites[i] = atlas.getSubimage(i*32, 160, 48, 48);
		}
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(unitSprites[POMU], unit.getX(), unit.getY()-16, null);
	}
}
