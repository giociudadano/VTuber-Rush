package com.vtuberrush.src.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GameScreen extends JPanel {
	
	private Random random;
	private BufferedImage image;
	private Dimension windowSize = new Dimension(1280,720);
	private ArrayList<BufferedImage> sprites = new ArrayList<>();
	
	public GameScreen(BufferedImage image) {
		this.image = image;
		setWindowSize();
		loadSprites();
		random = new Random();
	}
	
	private void setWindowSize() {
		setMinimumSize(windowSize);
		setPreferredSize(windowSize);
		setMaximumSize(windowSize);	
	}

	private void loadSprites() {
		for(int y = 0; y < 40; y++) {
			for(int x = 0; x < 20; x++) {
				sprites.add(image.getSubimage(x*32, y*32, 32, 32));
			}
		}
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		for(int y = 0; y < 20; y++) {
			for(int x = 0; x < 40; x++) {
				graphics.drawImage(sprites.get(getRandomInt()), x*32, y*32, null);
			}
		}
	}
	
	private int getRandomInt() {
		return random.nextInt(400);
	}
}
