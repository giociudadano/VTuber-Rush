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
	}
	
	private void drawTileButtons(Graphics graphics) {
		for (Button button : tileButtons) {
			graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
		}
	}

	private BufferedImage getButtonImage(int id) {
		return playing.getTileManager().getSprite(id);
	}

	private void resetButtons() {
		buttonMenu.resetButtons();
	}

	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		}
	}

	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		}
	}

	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		}
		
	}

	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
}
