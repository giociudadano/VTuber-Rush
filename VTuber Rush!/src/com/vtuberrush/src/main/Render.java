package com.vtuberrush.src.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Calls the appropriate render method of the current scene based on the current game state. <p>
 * Defaults to rendering the main menu when first initialized by the program.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Render {
	
	private Game game;

	/**
	 * Creates a new render object to render the current instance of the game.
	 * @param game - Allows the constructor to interact with other parts of the game when initialized.
	 */
	public Render(Game game) {
		this.game = game;
	}
	
	/**
	 * Checks the current game state of the program and runs the appropriate render method. <br>
	 * Sets the default font of the program to <b>MiHoYo_SDK_Web 12</b> with anti-aliasing enabled.
	 * @param graphics - Responsible for drawing objects to the screen.
	 * @see Render#render(Graphics)
	 */
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
