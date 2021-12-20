package com.vtuberrush.src.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.vtuberrush.src.main.GameStates.*;

import com.vtuberrush.src.main.GameStates;

public class KeyboardInput implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_1) {
			GameStates.gameState = MENU;
		} else if (e.getKeyCode() == KeyEvent.VK_2) {
			GameStates.gameState = PLAYING;
		} else if (e.getKeyCode() == KeyEvent.VK_3) {
			GameStates.gameState = SETTINGS;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
