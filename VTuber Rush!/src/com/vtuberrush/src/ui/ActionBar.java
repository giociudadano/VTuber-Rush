package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;

import com.vtuberrush.src.scenes.Playing;

public class ActionBar extends Bar{
	private Playing playing;
	private Button buttonMenu;
	
	public ActionBar(int x, int y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		initButtons();
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(x, y, width, height);
		drawButtons(graphics);
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 25);
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
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
	
	private void resetButtons() {
		buttonMenu.resetButtons();
	}
	
}
