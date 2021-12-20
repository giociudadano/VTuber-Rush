package com.vtuberrush.src.main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GameScreen extends JPanel {
	
	private Dimension windowSize = new Dimension(1280,720);
	private Game game;
	
	public GameScreen(Game game) {
		this.game = game;
		setWindowSize();
	}
	
	private void setWindowSize() {
		setMinimumSize(windowSize);
		setPreferredSize(windowSize);
		setMaximumSize(windowSize);	
	}


	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		game.getRender().render(graphics);
	}
}
