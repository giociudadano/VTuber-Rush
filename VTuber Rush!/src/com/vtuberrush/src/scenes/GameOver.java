package com.vtuberrush.src.scenes;

import java.awt.Graphics;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.ui.Button;

import static com.vtuberrush.src.main.GameStates.PLAYING;
import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

public class GameOver extends GameScene implements SceneMethods{

	private Button buttonReplay, buttonMenu;
	
	public GameOver(Game game) {
		super(game);
		initButtons();
	}
	
	private void initButtons() {
		buttonReplay = new Button("Try Again", 550, 420, 200, 60);
		buttonMenu = new Button("Main Menu", 550, 490, 200, 60);
	}
	
	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(LoadSave.getBackground(), 0, 0, null);
		buttonReplay.draw(graphics);
		buttonMenu.draw(graphics);
	}
	
	private void resetGame() {
		game.getPlaying().resetGame();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (buttonReplay.getBounds().contains(x, y)) {
			resetGame();
			setGameState(PLAYING);
		} else if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
			resetGame();
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		buttonReplay.setMouseOver(false);
		buttonMenu.setMouseOver(false);
		if (buttonReplay.getBounds().contains(x, y)) {
			buttonReplay.setMouseOver(true);
		} else if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (buttonReplay.getBounds().contains(x, y)) {
			buttonReplay.setMousePressed(true);
		} else if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		buttonReplay.resetButtons();
		buttonMenu.resetButtons();
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
