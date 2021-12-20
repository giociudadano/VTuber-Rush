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
	private long time = System.currentTimeMillis();
	private int frames;
	

	
	public GameScreen(BufferedImage image) {
		this.image = image;
		loadSprites();
		random = new Random();
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
		
		getFrames();
	}
	
	private void getFrames() {
		frames++;
		if(System.currentTimeMillis() - time >= 1000) {
			System.out.println("FPS: " + frames);
			frames = 0;
			time = System.currentTimeMillis();
		}
	}
	
	private int getRandomInt() {
		return random.nextInt(400);
	}
}
