package com.vtuberrush.src.main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.vtuberrush.src.input.KeyboardInput;
import com.vtuberrush.src.input.MouseInput;

public class GameScreen extends JPanel {
	
	private Dimension windowSize = new Dimension(1280,720);
	private Game game;
	
	private KeyboardInput keyboardInput;
	private MouseInput mouseInput;
	
	public GameScreen(Game game) {
		this.game = game;
		setWindowSize();
	}
	
	private void setWindowSize() {
		setMinimumSize(windowSize);
		setPreferredSize(windowSize);
		setMaximumSize(windowSize);	
	}

	public void initInput() {
		keyboardInput = new KeyboardInput(game);
		mouseInput = new MouseInput(game);
		
		addKeyListener(keyboardInput);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		
		requestFocus();
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		game.getRender().render(graphics);
	}
}
