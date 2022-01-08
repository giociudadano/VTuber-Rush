package com.vtuberrush.src.objects;

import java.awt.image.BufferedImage;

/**
 * A <b><i>tile</i></b> is a superclass that represents the grid system of
 * the game and interacts with game logic such as if a <b><i>unit</i></b>
 * is able to be placed or if an <b><i>enemy</i></b> can pathfind to the set location.<br>
 * A tile in a location can be edited using the game's built-in level editor or
 * by using pre-defined level files.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Tile {
	
	private BufferedImage[] sprite;
	private int id, tileType;
	
	/**
	 * Creates a non-animated tile that can be placed and interacted with using game logic.
	 * @param sprite - The unique, non-animated image that is rendered by the game.
	 * @param id - The unique identifier of that tile.
	 * @param tileType - The type of that tile, used for game logic. Multiple tiles can have
	 * the same type, but no two tiles should have the same identifier.
	 */
	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	/**
	 * Creates an animated tile that can be placed and interacted with using game logic.
	 * <p><i>This method is planned to be used in the future.</i>
	 * @param sprite - The unique, animated image that is rendered by the game.
	 * @param id - The unique identifier of that tile.
	 * @param tileType - The type of that tile, used for game logic. Multiple tiles can have
	 * the same type, but no two tiles should have the same identifier.
	 */
	public Tile(BufferedImage[] sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	/**
	 * Checks if the tile is animated.
	 * @return <b><i>true</i></b> - This tile is animated.
	 */
	public boolean isAnimated() {
		return (sprite.length > 1);
	}
	
	/**
	 * Returns the unique identifier of this tile.
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the type of this tile.
	 * @return {@link #tileType}
	 */
	public int getTileType() {
		return tileType;
	}

	/**
	 * Returns the non-animated image of this tile.
	 * @return {@link #sprite}
	 */
	public BufferedImage getSprite() {
		return sprite[0];
	}
	
	/**
	 * Returns the image of this tile with the current passed tile.
	 * <p><i>This method is planned to be used in the future.</i>
	 * @param frame - The current frame animation of the tile.
	 * @return {@link #sprite}
	 */
	public BufferedImage getSprite(int frame) {
		return sprite[frame];
	}
}
