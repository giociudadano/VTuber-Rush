package com.vtuberrush.src.ui;

/**
 * A <b><i>bar</i></b> is a non-clickable, superclass object that is placed at the bottom of the
 * playing field and is used to group <b><i>Buttons</i></b> together that perform
 * special functions such as placing down a tile or a unit. <br>
 * A bar acts as a defined separation between the playing field and and the non-playing field.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Bar {
	
	/**
	 * Defines the horizontal position of this bar from the left of the window in pixels.
	 */
	protected int x;
	
	/**
	 * Defines the vertical position of this bar from the top of the window in pixels.
	 */
	protected int y;
	
	/**
	 * Defines the horizontal size of the bar in pixels.
	 */
	protected int width;
	
	/**
	 * Defines the vertical size of the bar in pixels.
	 */
	protected int height;
	
	/**
	 * Creates a new bar in the defined location. <br>
	 * This constructor is used when first initializing the <b>PLAYING</b> and <b>EDITING</b> fields for the
	 * Action Bar and Tool Bar, respectively.
	 * @param x - The horizontal position of the bar from the left of the window in pixels.
	 * @param y - The vertical position of the bar from the top of the window in pixels.
	 * @param width - The horizontal size of the bar in pixels.
	 * @param height - The vertical size of the bar in pixels.
	 */
	public Bar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
