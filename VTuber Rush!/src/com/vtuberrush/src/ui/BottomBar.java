package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.scenes.Playing;

public class BottomBar {
	private int x, y, width, height;
	private Playing playing;
	private Button buttonMenu;
	private ArrayList<Button>tileButtons = new ArrayList<>();
	private Tile selectedTile;
	
	public BottomBar(int x, int y, int width, int height, Playing playing) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.playing = playing;
		initButtons();
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(0,0,0,200));
		graphics.fillRect(x, y, width, height);
		drawButtons(graphics);
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 25);
		
		int i = 0;
		for(Tile tile : playing.getTileManager().tiles) {
			tileButtons.add(new Button(tile.getName(), 100 + (60 * i), 560, 50, 50, i++));
		}
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
		drawTileButtons(graphics);
		drawSelectedTile(graphics);
	}
	
	private void drawSelectedTile(Graphics graphics) {
		if (selectedTile != null) {
			graphics.setColor(Color.yellow);
			graphics.drawRect(100 + (60 * selectedTile.getId()), 560, 50, 50);
		}
	}

	private void drawTileButtons(Graphics graphics) {
		for (Button button : tileButtons) {
			graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
			
			//mouseOver
			if(button.isMouseOver()) {
				graphics.setColor(Color.white);
			} else {
				graphics.setColor(Color.black);
			}
			graphics.drawRect(button.x, button.y, button.width, button.height);
			
			//mousePressed
			if(button.isMousePressed()) {
				graphics.setColor(new Color(150,150,150));
				graphics.drawRect(button.x, button.y, button.width, button.height);
			}
		}
	}

	private BufferedImage getButtonImage(int id) {
		return playing.getTileManager().getSprite(id);
	}

	

	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		} else {
			for (Button button : tileButtons) {
				if (button.getBounds().contains(x, y)) {
					selectedTile = playing.getTileManager().getTile(button.getId());
					playing.setSelectedTile(selectedTile);
					return;
				}
			}
		}
	}

	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		for (Button button : tileButtons) {
			button.setMouseOver(false);
		}
		
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		} else {
			for (Button button : tileButtons) {
				if(button.getBounds().contains(x, y)) {
					button.setMouseOver(true);
					return;
				}
			}
		}
	}

	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		} else {
			for (Button button : tileButtons) {
				if(button.getBounds().contains(x, y)) {
					button.setMousePressed(true);
					return;
				}
			}
		}
		
	}
	

	public void mouseReleased(int x, int y) {
		resetButtons();
		
	}
	
	private void resetButtons() {
		buttonMenu.resetButtons();
		for (Button button : tileButtons) {
			button.resetButtons();
		}
	}
}
