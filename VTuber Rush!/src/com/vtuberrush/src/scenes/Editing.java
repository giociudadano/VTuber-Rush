package com.vtuberrush.src.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.ui.ToolBar;

import static com.vtuberrush.src.helpers.Constants.Tiles.ROAD;

public class Editing extends GameScene implements SceneMethods{
	
	private int[][] level;
	private Tile selectedTile;
	private ToolBar toolBar;

	private int mouseX, mouseY;
	private int xLast, yLast;
	private boolean drawSelectedTile = false;
	
	private Flag start, end;

	public Editing(Game game) {
		super(game);
		loadLevelDefault();
		toolBar = new ToolBar(0,550,1280,200,this);
	}
	
	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
		ArrayList<Flag> flags = LoadSave.readFlags("new_level");
		start = flags.get(0);
		end = flags.get(1);
	}
	
	public void tick() {
		tickAnimation();
	}

	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		toolBar.draw(graphics);
		drawSelectedTile(graphics);
		drawFlags(graphics);
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
		LoadSave.saveLevel("new_level", level, start, end);
		game.getPlaying().setLevel(level);
	}
	
	private void drawFlags(Graphics graphics) {
		if (start != null) {
			graphics.drawImage(toolBar.getImageStart(), start.getX() * 32, start.getY() * 32, 32, 32, null);
		}
		
		if (end != null) {
			graphics.drawImage(toolBar.getImageEnd(), end.getX() * 32, end.getY() * 32, 32, 32, null);
		}
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
			x = x / 32;
			y = y / 32;
			if (selectedTile.getId() > 0) {
				if (xLast == x && yLast == y) {
					return;
				}
				xLast = x;
				yLast = y;
				level[y][x] = selectedTile.getId();
			} else {
				int id = level[y][x];
				if (game.getTileManager().getTile(id).getTileType() == ROAD) {
					if(selectedTile.getId() == -1) {
						start = new Flag(x, y);
					} else {
						end = new Flag(x, y);
					}
				}
			}
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
