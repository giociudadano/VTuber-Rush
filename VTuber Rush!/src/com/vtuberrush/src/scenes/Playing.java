package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;

import com.vtuberrush.src.helpers.LevelBuilder;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.managers.TileManager;

public class Playing extends GameScene implements SceneMethods {

	private int[][] level;
	private TileManager tileManager;
	
	public Playing(Game game) {
		super(game);
		level = LevelBuilder.getLevelData();
		tileManager = new TileManager();
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(new Color(138,212,79));
		graphics.fillRect(0, 0, 1280, 720);
		for(int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				int id = level[y][x];
				graphics.drawImage(tileManager.getSprite(id), x*64, y*64, null);
			}
		}
		
	}

}
