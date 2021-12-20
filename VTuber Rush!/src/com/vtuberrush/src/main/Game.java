package com.vtuberrush.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.vtuberrush.src.input.KeyboardInput;
import com.vtuberrush.src.input.MouseInput;
import com.vtuberrush.src.scenes.Menu;
import com.vtuberrush.src.scenes.Playing;
import com.vtuberrush.src.scenes.Settings;

public class Game extends JFrame implements Runnable {
	
	private GameScreen gameScreen;
	private Thread gameThread;
	
	private final double frameRateCap = 60.0;
	private final double tickRateCap = 120.0;
	
	private KeyboardInput keyboardInput;
	private MouseInput mouseInput;
	
	private Render render;
	private Menu menu;
	private Playing playing;
	private Settings settings;

	public Game() {	
		
		//Window Initialization
		setIconImage(new ImageIcon("res/WindowIcon.png").getImage());
		setTitle("Vtuber Rush!");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initClasses();
		add(gameScreen);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	private void initClasses() {
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		playing = new Playing(this);
		settings = new Settings(this);
	}
	
	private void initInput() {
		keyboardInput = new KeyboardInput();
		mouseInput = new MouseInput();
		
		addKeyListener(keyboardInput);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		
		requestFocus();
	}

	private void startGame() {
		gameThread = new Thread(this){};
		gameThread.start();
	}
	
	
	private void tickGame() {
	} 
	
	public static void main(String[] args) {
		Game game = new Game();	
		game.initInput();
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
	
	//Getters and Setters
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Settings getSettings() {
		return settings;
	}

}
