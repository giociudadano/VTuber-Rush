package com.vtuberrush.src.main;

import java.awt.Graphics;

public class Render {
	
	private Game game;

	public Render(Game game) {
		this.game = game;
	}
	
	public void render(Graphics graphics) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().render(graphics);
			break;
		case PLAYING:
			game.getPlaying().render(graphics);
			break;
		case SETTINGS:
			game.getSettings().render(graphics);
			break;
		default:
			break;
		}
	}
}
