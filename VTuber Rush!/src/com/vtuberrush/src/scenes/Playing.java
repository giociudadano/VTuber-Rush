package com.vtuberrush.src.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

	private int[][] level;
	private ActionBar bottomBar;
	private int mouseX, mouseY;
	
	public Playing(Game game) {
		super(game);
		loadLevelDefault();
		bottomBar = new ActionBar(0,550,1280,200,this);
	}

	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
	}

	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		bottomBar.draw(graphics);
	}
	
	private void drawLevel(Graphics graphics) {
		for(int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				int id = level[y][x];
				graphics.drawImage(getSprite(id), x*32, y*32, null);
			}
		}
	}
	
	private BufferedImage getSprite(int id) {
		return game.getTileManager().getSprite(id);
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
		} else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
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
	
	@Override
	public void mouseDragged(int x, int y) {
	}

	public void setLevel(int[][] level) {
		this.level = level;
	}
	
}
