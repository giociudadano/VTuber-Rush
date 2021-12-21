package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;
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

	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		toolBar.draw(graphics);
		drawSelectedTile(graphics);
	}
	
	private void drawLevel(Graphics graphics) {
		graphics.setColor(new Color(138,212,79));
		graphics.fillRect(0, 0, 1280, 720);
		for(int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				int id = level[y][x];
				graphics.drawImage(getSprite(id), x*64, y*64, null);
			}
		}
	}
	
	private BufferedImage getSprite(int id) {
		return game.getTileManager().getSprite(id);
	}
	
	public void saveLevel() {
		LoadSave.saveLevel("new_level", level);
	}
	
	private void drawSelectedTile(Graphics graphics) {
		if(selectedTile != null && drawSelectedTile) {
			graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 64, 64, null);
		}
	}

	public void setSelectedTile(Tile tile) {
		this.selectedTile = tile;
		drawSelectedTile = true;
	}
	
	private void changeTile(int x, int y) {
		if (selectedTile != null) {
			int tileX = x / 64;
			int tileY = y / 64;
			if (tileXLast == tileX && tileYLast == tileY) {
				return;
			}
			tileXLast = tileX;
			tileYLast = tileY;
			level[tileY][tileX] = selectedTile.getId();
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y > 550) {
			toolBar.mouseClicked(x, y);
		} else {
			changeTile(mouseX, mouseY);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y > 550) {
			toolBar.mouseMoved(x, y);
			drawSelectedTile = false;
		} else {
			mouseX = (x / 64) * 64;
			mouseY = (y / 64) * 64;
			drawSelectedTile = true;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y < 550) {
			changeTile(x, y);
		}
	}

}
