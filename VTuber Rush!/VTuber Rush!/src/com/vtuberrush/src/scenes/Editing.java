package com.vtuberrush.src.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.ui.ToolBar;

public class Editing extends GameScene implements SceneMethods{
	
	private int[][] level;
	private Tile selectedTile;
	private ToolBar toolBar;

	private int mouseX, mouseY;
	private int tileXLast, tileYLast;
	private boolean drawSelectedTile = false;

	public Editing(Game game) {
		super(game);
		loadLevelDefault();
		toolBar = new ToolBar(0,550,1280,200,this);
	}
	
	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
		game.getPlaying().setLevel(level);
	}
	
	public void tick() {
		tickAnimation();
	}

	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		toolBar.draw(graphics);
		drawSelectedTile(graphics);
	}

	private void drawLevel(Graphics graphics) {
		for(int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				int id = level[y][x];
				if(isAnimated(id)) {
					graphics.drawImage(getSprite(id, frame), x*32, y*32, null);
				} else {
					graphics.drawImage(getSprite(id), x*32, y*32, null);
				}
			}
		}
	}
	
	public void saveLevel() {
		LoadSave.saveLevel("new_level", level);
	}
	
	private void drawSelectedTile(Graphics graphics) {
		if(selectedTile != null && drawSelectedTile) {
			graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
		}
	}

	public void setSelectedTile(Tile tile) {
		this.selectedTile = tile;
		drawSelectedTile = true;
	}
	
	private void changeTile(int x, int y) {
		if (selectedTile != null) {
			int tileX = x / 32;
			int tileY = y / 32;
			if (tileXLast == tileX && tileYLast == tileY) {
				return;
			}
			tileXLast = tileX;
			tileYLast = tileY;
			level[tileY][tileX] = selectedTile.getId();
		}
	}

	//Mouse Methods
	@Override
	public void mouseClicked(int x, int y) {
		if (y > 540) {
			toolBar.mouseClicked(x, y);
			drawSelectedTile = false;
		} else {
			changeTile(mouseX, mouseY);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y > 540) {
			toolBar.mouseMoved(x, y);
			drawSelectedTile = false;
		} else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
			drawSelectedTile = true;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y > 540) {
			toolBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		toolBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y < 540) {
			changeTile(x, y);
		}
	}
	
	//Keyboard Methods
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			toolBar.getVariant();
		}
	}

}
