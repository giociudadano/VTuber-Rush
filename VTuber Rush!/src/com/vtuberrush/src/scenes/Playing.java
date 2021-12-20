package com.vtuberrush.src.scenes;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;

import com.vtuberrush.src.helpers.LevelBuilder;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.managers.TileManager;
import com.vtuberrush.src.ui.Button;

public class Playing extends GameScene implements SceneMethods {

	private int[][] level;
	private TileManager tileManager;
	private Button buttonMenu;
	
	public Playing(Game game) {
		super(game);
		level = LevelBuilder.getLevelData();
		tileManager = new TileManager();
		initButtons();
	}

	

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(new Color(138,212,79));
		graphics.fillRect(0, 0, 1280, 720);
		for(int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				int id = level[y][x];
				graphics.drawImage(tileManager.getSprite(id), x*64, y*64, null);
			}
		}
		drawButtons(graphics);
	}


	@Override
	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		}
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 10, 80, 25);
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
	}
	
	private void resetButtons() {
		buttonMenu.resetButtons();
	}


}
