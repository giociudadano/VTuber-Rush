package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.vtuberrush.src.scenes.Playing;

public class ActionBar extends Bar{
	private Playing playing;
	private Button buttonMenu;
	
	private Button[] unitButtons;
	
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
		unitButtons = new Button[3];
		for(int i = 0; i < unitButtons.length; i++) {
			unitButtons[i] = new Button("", 100 + (122 * i), 560, 112, 136, i);
		}
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
		for (Button button : unitButtons) {
			graphics.drawImage(playing.getUnitManager().getUnitIcons()[button.getId()], button.x, button.y, button.width, button.height, null);
			drawButtonsFeedback(graphics, button);
		}
	}
	
	private void drawButtonsFeedback(Graphics graphics, Button button) {
		//mouseOver
		
		if(button.isMouseOver()) {
			Graphics2D graphics2d = (Graphics2D) graphics;
			graphics.setColor(Color.white);
			if (button.isMousePressed()) {
				graphics.setColor(Color.yellow);
			}
			graphics2d.drawRoundRect(button.x, button.y, button.width, button.height,10,13);
			graphics2d.drawRoundRect(button.x+1, button.y+1, button.width-2, button.height-2,6,10);
		}
	}

	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		}
	}

	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		for (Button button : unitButtons) {
			button.setMouseOver(false);
		}
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		} else {
			for (Button button : unitButtons) {
				if (button.getBounds().contains(x, y)) {
					button.setMouseOver(true);
				}
			}
		}
		
	}

	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		} else {
			for (Button button : unitButtons) {
				if (button.getBounds().contains(x, y)) {
					button.setMousePressed(true);
				}
			}
		}
	}
	

	public void mouseReleased(int x, int y) {
		buttonMenu.resetButtons();
		for (Button button : unitButtons) {
			button.resetButtons();
		}
	}
	
}
