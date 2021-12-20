package com.vtuberrush.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
	
	private GameScreen gameScreen;
	private BufferedImage image;
	
	private final double frameRateCap = 60.0;
	private final double tickRateCap = 120.0;
	
	private Thread gameThread;

	public Game() {	
		
		//Resource Methods
		importImage();
		
		//Window Initialization
		setIconImage(new ImageIcon("/WindowIcon.png").getImage());
		setTitle("Vtuber Rush!");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		
		gameScreen = new GameScreen(image);
		add(gameScreen);
		
		pack();
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

	private void startGame() {
		gameThread = new Thread(this){};
		gameThread.start();
	}
	
	private void loopGame() {
	}
	
	private void tickGame() {
	} 
	
	public static void main(String[] args) {
		Game game = new Game();	
		game.startGame();
	}



	@Override
	public void run() {
		
		double tickRate = 1000000000.0 / tickRateCap;
		double frameRate = 1000000000.0 / frameRateCap;
		
		long frameRateTime = System.nanoTime();
		long tickRateTime = System.nanoTime();
		long time = System.currentTimeMillis();
		
		int frames = 0;
		int ticks = 0;
		
		long timeNow;
		
		while(true) {
			timeNow = System.nanoTime();
			
			//Renders graphics at 60 FPS
			if(timeNow - frameRateTime >= frameRate) {
				repaint();
				frameRateTime = timeNow;
				frames++;
			}
			
			//Ticks game logic at 120 TPS
			if(timeNow - tickRateTime >= tickRate) {
				tickGame();
				tickRateTime = timeNow;
				ticks++;
			}
			
			//Prints tick rate and frame rate
			if(System.currentTimeMillis() - time >= 1000) {
				System.out.println("FPS: " + frames + " | TPS: " + ticks);
				frames = 0;
				ticks = 0;
				time = System.currentTimeMillis();
			}
		}
		
	}
}
