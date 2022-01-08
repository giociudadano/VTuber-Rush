package com.vtuberrush.src.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.main.GameStates;

/**
 * Listens for mouse movement and input and runs the appropriate function related to the input.<br>
 * Used for listening for <b>LMB</b> when clicking on buttons, clicking on units when playing, and
 * clicking on tiles when editing.<br>
 * Used for listening to mouse movement and detecting which unit
 * or tile to select based on the current position of the mouse.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class MouseInput implements MouseListener, MouseMotionListener{

	private Game game;
	
	/**
	 * Allows the mouse-related methods in this class to interact with other parts of the game.
	 * @param game - Allows the constructor to interact with other parts of the game when initialized.
	 */
	public MouseInput(Game game) {
		this.game = game;
	}

	/**
	 * Checks the current game state of the program and runs the method related to dragging, or
	 * moving the mouse while a mouse button is still being pressed.
	 * <br><i>This method is currently blank but will be used in the future.</i>
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().mouseDragged(e.getX(),e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseDragged(e.getX(),e.getY());
			break;
		case EDITING:
			game.getEditing().mouseDragged(e.getX(),e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseDragged(e.getX(),e.getY());
			break;
		default:
			break;
		}
	}

	/**
	 * Checks the current game state of the program and runs the method related to moving the mouse.<br>
	 * Used to dynamically render a displayed unit or selected tile that is about to be placed.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().mouseMoved(e.getX(),e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseMoved(e.getX(),e.getY());
			break;
		case EDITING:
			game.getEditing().mouseMoved(e.getX(),e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseMoved(e.getX(),e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mouseMoved(e.getX(),e.getY());
			break;
		default:
			break;
		}
	}

	/**
	 * Checks the current game state of the program and runs the method related to moving the mouse.<br>
	 * Used to click on buttons, for selecting and placing units while playing, and for editing the
	 * tiles using the built-in level editor.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			switch(GameStates.gameState) {
			case MENU:
				game.getMenu().mouseClicked(e.getX(),e.getY());
				break;
			case PLAYING:
				game.getPlaying().mouseClicked(e.getX(),e.getY());
				break;
			case EDITING:
				game.getEditing().mouseClicked(e.getX(),e.getY());
				break;
			case SETTINGS:
				game.getSettings().mouseClicked(e.getX(),e.getY());
				break;
			case GAME_OVER:
				game.getGameOver().mouseClicked(e.getX(),e.getY());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Checks the current game state of the program and runs the method related to whether the
	 * mouse is till being pressed.<br>
	 * Used to dynamically update the color of buttons.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().mousePressed(e.getX(),e.getY());
			break;
		case PLAYING:
			game.getPlaying().mousePressed(e.getX(),e.getY());
			break;
		case EDITING:
			game.getEditing().mousePressed(e.getX(),e.getY());
			break;
		case SETTINGS:
			game.getSettings().mousePressed(e.getX(),e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mousePressed(e.getX(),e.getY());
			break;
		default:
			break;
		}
	}

	/**
	 * Checks the current game state of the program and runs the method related to whether a
	 * mouse that was pressed is no longer being pressed.<br>
	 * Used to dynamically update buttons to show that it is being pressed and to update 
	 * which units in the action bar and tool bar were previously selected.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().mouseReleased(e.getX(),e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseReleased(e.getX(),e.getY());
			break;
		case EDITING:
			game.getEditing().mouseReleased(e.getX(),e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseReleased(e.getX(),e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mouseReleased(e.getX(),e.getY());
			break;
		default:
			break;
		}
	}

	/**
	 * Checks the current game state of the program and runs the method related to entering the
	 * range or bounds of a component.
	 * <br><i>This method is currently blank but will be used in the future.</i>
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Checks the current game state of the program and runs the method related to exiting the
	 * range or bounds of a component.
	 * <br><i>This method is currently blank but will be used in the future.</i>
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

}
