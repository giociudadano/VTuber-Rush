package com.vtuberrush.src.main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.managers.TileManager;
import com.vtuberrush.src.scenes.Editing;
import com.vtuberrush.src.scenes.GameOver;
import com.vtuberrush.src.scenes.Menu;
import com.vtuberrush.src.scenes.Playing;
import com.vtuberrush.src.scenes.Settings;

/**
 * The main class of the project responsible for creating a new instance of the game.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 * @see #main(String[])
 */
public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;	
	private Thread gameThread;				
	private final double frameRateCap = 60.0;
	private final double tickRateCap = 120.0;		
	private Render render;
	private Menu menu;
	private Editing editing;
	private Playing playing;
	private Settings settings;
	private GameOver gameOver;
	private TileManager tileManager;
	
	/**
	 * Creates a new instance of a game, initializes the input, and starts a thread. The main method of the main class. 
	 * @see #Game()
	 * @see #gameScreen
	 * @see #startGame()
	 */
	public static void main(String[] args) {
		Game game = new Game();	
		game.gameScreen.initInput();
		game.startGame();
	}
	
	/**
	 * Creates a window using {@link javax.swing.JFrame}, initializes necessary classes, and
	 * creates a new level from memory if a level file does not exist.
	 * @see #initClasses()
	 * @see #createLevelDefault()
	 */
	public Game() {
		LoadSave.createFolder();
		setIconImage(new ImageIcon(LoadSave.getGameIcon()).getImage());
		setTitle("VTuber Rush!");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initClasses();
		createLevelDefault();
		add(gameScreen);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Initializes the necessary classes needed to run the game.
	 */
	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		playing = new Playing(this);
		editing = new Editing(this);
		settings = new Settings(this);
		gameOver = new GameOver(this);
	}
	
	/**
	 * Creates a default level made of grass blocks and attempts to load an existing level
	 * from memory. If a level already exists, the new level replaces the default level.
	 * <p>
	 * Otherwise, the user is free to edit the default level using the built-in
	 * level editor in-game.
	 * @see {@link com.vtuberrush.src.helpers.LoadSave}
	 */
	private void createLevelDefault() {
		int[] array = new int[720];
		for(int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
		LoadSave.createLevel(array);
	}
	
	/**
	 * Creates a new thread of the game which allows the method {@link #run()} to be called repeatedly.
	 */
	private void startGame() {
		gameThread = new Thread(this){};
		gameThread.start();
	}
	
	/**
	 * Checks the current game state and runs the appropriate game tick method. This function is
	 * called 120 times per second.
	 * @see {@link com.vtuberrush.src.scenes.Editing.tick}
	 * @see {@link com.vtuberrush.src.scenes.Playing.tick}
	 */
	private void tickGame() {
		switch(GameStates.gameState) {
		case PLAYING:
			playing.tick();
			break;
		case EDITING:
			editing.tick();
			break;
		default:
			break;
		}
	} 
	
	/**
	 * Calls {@link #repaint()} 60 times per second and {@link #tickGame()} 120 times per second.
	 * <p>
	 * The number of times this function is dependent on system performance.
	 * As such, it is appropriate to create independent functions for rendering
	 * images and running the game to control certain game mechanics such as
	 * speed or cooldowns that are dependent on system time.
	 */
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
	
	/**
	 * Returns the {@link #render} object which draws all the current objects on screen.
	 * @return {@link #render}
	 */
	public Render getRender() {
		return render;
	}

	/**
	 * Returns the {@link #menu} scene.
	 * @return {@link #menu}
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Returns the {@link #playing} scene.
	 * @return {@link #playing}
	 */
	public Playing getPlaying() {
		return playing;
	}

	/**
	 * Returns the {@link #editing} scene.
	 * @return {@link #editing}
	 */
	public Editing getEditing() {
		return editing;
	}
	
	/**
	 * Returns the {@link #settings} scene.
	 * @return {@link #settings}
	 */
	public Settings getSettings() {
		return settings;
	}
	
	/**
	 * Returns the {@link #gameOver} scene.
	 * @return {@link #gameOver}
	 */
	public GameOver getGameOver() {
		return gameOver;
	}
	
	/**
	 * Returns the {@link #tileManager} responsible for rendering the level on startup.
	 * @return {@link #tileManager}
	 */
	public TileManager getTileManager() {
		return tileManager;
	}

}
