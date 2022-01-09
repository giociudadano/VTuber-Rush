package com.vtuberrush.src.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.main.GameStates;

/**
 * Listens for keyboard input and runs the appropriate function related to the input.<br>
 * Used for listening when the player presses <b>ESCAPE</b> when playing which removes
 * the currently displayed or selected unit.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class KeyboardInput implements KeyListener {

	private Game game;
	
	/**
	 * Allows the keyboard-related methods in this class to interact with other parts of the game.
	 * @param game - Allows the constructor to interact with other parts of the game when initialized.
	 */
	public KeyboardInput(Game game) {
		this.game = game;
	}
	
	/**
	 * Checks the current game state of the program and runs the method related to
	 * typing a single key.
	 * <br><i>This method is currently blank but will be used in the future.</i>
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/**
	 * Checks the current game state of the program and runs the method related to
	 * pressing a key.<br>
	 * Used for listening when the player presses <b>ESCAPE</b> when playing which removes
	 * the currently displayed or selected unit.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameStates.gameState) {
		case PLAYING:
			game.getPlaying().keyPressed(e);
		case EDITING:
			game.getEditing().keyPressed(e);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Checks the current game state of the program and runs the method related to
	 * releasing a key.
	 * <br><i>This method is currently blank but will be used in the future.</i>
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
