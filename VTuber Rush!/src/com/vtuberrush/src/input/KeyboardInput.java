package com.vtuberrush.src.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.main.GameStates;

public class KeyboardInput implements KeyListener {

	private Game game;
	public KeyboardInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

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

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
