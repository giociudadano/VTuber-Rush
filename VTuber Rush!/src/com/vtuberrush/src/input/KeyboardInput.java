package com.vtuberrush.src.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_1) {
			System.out.println("1 is pressed!");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
