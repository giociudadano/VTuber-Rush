package com.vtuberrush.src.objects;

/**
* A <b><i>flag</i></b> is an object that can be placed above tiles to indicate the
* start and end points of a level. <br>
* A flag is initialized at <i>Tile(0, 0)</i> by default when creating a new level but can be
* adjusted using the game's built-in level editor.
* 
* @author Gio Carlo Ciudadano
* @version 0.0.1-alpha.1
*/
public class Flag {
	private int x, y;
	
	/**
	 * Creates a new flag at the passed location.
	 * @param x - Defines the horizontal position of the flag from the left of the window in pixels rounded to the nearest tile.
	 * @param y - Defines the vertical position of the flag from the top of the window in pixels rounded to the nearest tile.
	 */
	public Flag(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the horizontal position of this flag from the left of the window in pixels rounded to the nearest tile.
	 * @return {@link #x}
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Modifies the horizontal position of this flag from the left of the window in pixels rounded to the nearest tile.
	 * @param x - Defines the horizontal position of the flag from the left of the window in pixels rounded to the nearest tile.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Returns the vertical position of this flag from the top of the window in pixels rounded to the nearest tile.
	 * @return {@link #y}
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Modifies the vertical position of this flag from the top of the window in pixels rounded to the nearest tile.
	 * @param y - Defines the vertical position of the flag from the top of the window in pixels rounded to the nearest tile.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
