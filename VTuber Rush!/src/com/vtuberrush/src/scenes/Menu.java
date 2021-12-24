package com.vtuberrush.src.scenes;

import java.awt.Graphics;

import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.ui.Button;
import static com.vtuberrush.src.main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {
	
	private Button buttonPlaying, buttonEditing, buttonSettings, buttonQuit;
	
	public Menu(Game game) {
		super(game);
		initButtons();
	}

	@Override
	public void render(Graphics graphics) {
		drawButtons(graphics);
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		if (buttonPlaying.getBounds().contains(x, y)) {
			setGameState(PLAYING);
		} else if (buttonEditing.getBounds().contains(x, y)) {
			setGameState(EDITING);
		} else if (buttonSettings.getBounds().contains(x, y)) {
			setGameState(SETTINGS);
		} else if (buttonQuit.getBounds().contains(x, y)) {
			System.exit(0);
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		
		buttonPlaying.setMouseOver(false);
		buttonEditing.setMouseOver(false);
		buttonSettings.setMouseOver(false);
		buttonQuit.setMouseOver(false);
		
		if (buttonPlaying.getBounds().contains(x, y)) {
			buttonPlaying.setMouseOver(true);
		} else if (buttonEditing.getBounds().contains(x, y)) {
			buttonEditing.setMouseOver(true);
		} else if (buttonSettings.getBounds().contains(x, y)) {
			buttonSettings.setMouseOver(true);
		} else if (buttonQuit.getBounds().contains(x, y)) {
			buttonQuit.setMouseOver(true);
		}
	}
	
	@Override
	public void mousePressed(int x, int y) {
		if (buttonPlaying.getBounds().contains(x, y)) {
			buttonPlaying.setMousePressed(true);
		} else if (buttonEditing.getBounds().contains(x, y)) {
			buttonEditing.setMousePressed(true);
		} else if (buttonSettings.getBounds().contains(x, y)) {
			buttonSettings.setMousePressed(true);
		} else if (buttonQuit.getBounds().contains(x, y)) {
			buttonQuit.setMousePressed(true);
		}
		
	}
	
	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	@Override
	public void mouseDragged(int x, int y) {
	}
	
	private void initButtons() {
		buttonPlaying = new Button("Play", 550, 250, 200, 60);
		buttonEditing = new Button("Edit", 550, 320, 200, 60);
		buttonSettings = new Button("Settings", 550, 390, 200, 60);
		buttonQuit = new Button("Exit", 550, 460, 200, 60);
	}

	private void drawButtons(Graphics graphics) {
		buttonPlaying.draw(graphics);
		buttonEditing.draw(graphics);
		buttonSettings.draw(graphics);
		buttonQuit.draw(graphics);
	}
	
	private void resetButtons() {
		buttonPlaying.resetButtons();
		buttonEditing.resetButtons();
		buttonSettings.resetButtons();
		buttonQuit.resetButtons();
	}

}
