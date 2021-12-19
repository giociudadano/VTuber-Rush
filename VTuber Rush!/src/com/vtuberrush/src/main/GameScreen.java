package com.vtuberrush.src.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class GameScreen extends JPanel {
	
	private Random random;
	private BufferedImage image;
	
	public GameScreen(BufferedImage image) {
		this.image = image;
		random = new Random();
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(image, 0, 0, null);
	}
	
	private Color getRandomColor() {
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		
		return new Color(r, g, b);
	}
}
