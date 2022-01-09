package com.vtuberrush.src.main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.vtuberrush.src.input.KeyboardInput;
import com.vtuberrush.src.input.MouseInput;

/**
 * A set of methods related to creating, interacting, and drawing to the game screen.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 *
 */
public class GameScreen extends JPanel {
	
	private Dimension windowSize = new Dimension(1280,720);
	private Game game;
	
	private KeyboardInput keyboardInput;
	private MouseInput mouseInput;
	
	/**
	 * Creates the screen of the game and calls {@link #setWindowSize()}.
	 * @param game - Takes in the current instance of the game for {@link GameScreen#initInput()}.
	 */
	public GameScreen(Game game) {
		this.game = game;
		setWindowSize();
	}
	
	/**
	 * Sets the window size of the game.
	 */
	private void setWindowSize() {
		setMinimumSize(windowSize);
		setPreferredSize(windowSize);
		setMaximumSize(windowSize);	
	}

	/**
	 * Initializes keyboard and mouse input.
	 */
	public void initInput() {
		keyboardInput = new KeyboardInput(game);
		mouseInput = new MouseInput(game);
		
		addKeyListener(keyboardInput);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		
		requestFocus();
	}
	
	/**
	 * Renders the game screen to the window.
	 * @param graphics - A set of methods that allows objects to be drawn to a window.
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		game.getRender().render(graphics);
	}
}
