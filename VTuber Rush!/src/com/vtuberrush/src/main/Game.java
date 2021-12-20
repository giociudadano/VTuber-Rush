package com.vtuberrush.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends JFrame{
	
	private GameScreen gameScreen;
	private BufferedImage image;

	private double frameDuration = 1000000000.0 / 60.0;
	private long frameTime = System.nanoTime();
	
	public Game() {
		
		//Resource Methods
		importImage();
		
		//Window Initialization
		setIconImage(new ImageIcon("/WindowIcon.png").getImage());
		setTitle("Vtuber Rush!");
		setSize(1280,720);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		
		gameScreen = new GameScreen(image);
		add(gameScreen);
		
		setVisible(true);
	}
	
	private void importImage() {
		InputStream imageStream = getClass().getResourceAsStream("/PomuRainpuff.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loopGame() {
		while(true) {
			if(System.nanoTime() - frameTime >= frameDuration) {
				frameTime = System.nanoTime();
				repaint();
			} else {}
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();	
		game.loopGame();
	}
}
