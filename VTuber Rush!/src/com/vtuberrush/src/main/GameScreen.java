package com.vtuberrush.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GameScreen extends JPanel {
	
	private Random random;
	private BufferedImage image;
	private ArrayList<BufferedImage> sprites = new ArrayList<>();
	
	public GameScreen(BufferedImage image) {
		this.image = image;
		loadSprites();
		random = new Random();
	}
	
	private void loadSprites() {
		for(int y = 0; y < 20; y++) {
			for(int x = 0; x < 10; x++) {
				sprites.add(image.getSubimage(x*64, y*64, 64, 64));
			}
		}
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		for(int y = 0; y < 10; y++) {
			for(int x = 0; x < 20; x++) {
				graphics.drawImage(sprites.get(getRandomInt()), x*64, y*64, null);
			}
		}
	}
	
	private int getRandomInt() {
		return random.nextInt(200);
	}
}
