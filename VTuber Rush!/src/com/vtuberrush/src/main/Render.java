package com.vtuberrush.src.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Render {
	
	private Game game;

	public Render(Game game) {
		this.game = game;
	}
	
	public void render(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 12));
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().render(graphics);
			break;
		case PLAYING:
			game.getPlaying().render(graphics);
			break;
		case EDITING:
			game.getEditing().render(graphics);
			break;
		case SETTINGS:
			game.getSettings().render(graphics);
			break;
		case GAME_OVER:
			game.getGameOver().render(graphics);
			break;
		default:
			break;
		}
	}
}
