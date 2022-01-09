package com.vtuberrush.src.scenes;

import java.awt.image.BufferedImage;

import com.vtuberrush.src.main.Game;

/**
 * A <b><i>game scene</i></b> is a superclass object that facilitates the
 * rendering and animation of tiles across different subclasses, allows interaction of
 * the mouse with other game objects such as buttons, and allows interacting
 * with the main game class depending on the current game state.<br>
 * 
 * A subclass inheriting a game scene implements a set of pre-defined
 * methods in <b>SceneMethods</b> related to rendering objects to the screen
 * and listening for mouse events.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class GameScene {
	
	/**
	 * The current instance of the game.
	 */
	protected Game game;
	
	/**
	 * The amount of delay in ticks for a tile to change a frame of animation.
	 */
	protected int frameDelay;
	
	/**
	 * The current index of animation of all animated tiles.
	 */
	protected int frame;
	
	/**
	 * Creates a new game scene. 
	 * @param game - The current instance of the game.
	 */
	public GameScene(Game game) {
		this.game = game;
	}
	
	/**
	 * Returns the current instance of the game.
	 * @return {@link #game}
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Ticks the current frame every 0.16 seconds which animates all level tiles
	 * on the playing field.
	 */
	protected void tickAnimation() {
		frameDelay = (frameDelay + 1) % 20;
		if (frameDelay == 0) {
			frame = (frame + 1) % 4;
		}
	}
	
	/**
	 * Checks if the tile containing the passed ID is animated.
	 * @param id - The ID of the tile being checked if it is animated.
	 * @return <b><i>true</i></b> - This tile is animated.
	 */
	protected boolean isAnimated(int id) {
		return game.getTileManager().isAnimated(id);
	}
	
	/**
	 * Returns the sprite of a non-animated tile with the associated id.
	 * @param id - The id of the tile whose sprite is being obtained.
	 * @return <b>image</b> - The sprite of a non-animated tile with the associated id.
	 */
	protected BufferedImage getSprite(int id) {
		return game.getTileManager().getSprite(id);
	}
	
	/**
	 * Returns the sprite of an animated tile with the associated id in the passed frame.
	 * @param id - The id of the tile whose sprite is being obtained.
	 * @param frame - The current frame of animation of the tile with the passed id.
	 * @return <b>image</b> - The sprite of an animated tile with the associated id.
	 */
	protected BufferedImage getSprite(int id, int frame) {
		return game.getTileManager().getSpriteAnimated(id, frame);
	}
}
