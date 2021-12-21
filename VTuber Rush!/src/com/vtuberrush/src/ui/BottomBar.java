package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;


public class BottomBar {
	private int x, y, width, height;
	private Button buttonMenu;
	
	public BottomBar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initButtons();
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(0,0,0,200));
		graphics.fillRect(x, y, width, height);
		drawButtons(graphics);
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 25);
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
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
