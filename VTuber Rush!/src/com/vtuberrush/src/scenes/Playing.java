package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;

import com.vtuberrush.src.helpers.LevelBuilder;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.managers.TileManager;
import com.vtuberrush.src.ui.BottomBar;

public class Playing extends GameScene implements SceneMethods {

	private int[][] level;
	private TileManager tileManager;
	private BottomBar bottomBar;
	
	public Playing(Game game) {
		super(game);
		level = LevelBuilder.getLevelData();
		tileManager = new TileManager();
		bottomBar = new BottomBar(0,550,1280,200,this);
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
		bottomBar.draw(graphics);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y > 550) {
			bottomBar.mouseClicked(x, y);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y > 550) {
			bottomBar.mouseMoved(x, y);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y > 550) {
			bottomBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		bottomBar.mouseReleased(x, y);
	}
	
	public TileManager getTileManager() {
		return tileManager;
	}

}
