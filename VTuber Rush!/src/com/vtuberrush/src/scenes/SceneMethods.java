package com.vtuberrush.src.scenes;

import java.awt.Graphics;

/**
 * Defines a default set of methods that are required to be implemented by all subclasses inheriting game scenes.
 * <br>Used by all scenes by default to render graphics and listen to mouse movement or interaction.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public interface SceneMethods {
	
	/**
	 * Draws all objects to the game screen.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	public void render(Graphics graphics);
	
	/**
	 * Checks if the position of the mouse being <i>clicked</i> is within bounds of an object,
	 * and runs the appropriate method or function associated with clicking that object.
	 * @param x - Defines the horizontal position of the mouse from the left of the window in pixels.
	 * @param y - Defines the vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseClicked(int x, int y);
	
	/**
	 * Checks if the position of a mouse is within bounds of an object, and runs the appropriate
	 * method or function associated with hovering over that object.
	 * @param x - Defines the horizontal position of the mouse from the left of the window in pixels.
	 * @param y - Defines the vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseMoved(int x, int y);
	
	/**
	 * Checks if the position of a mouse is within bounds of an object while being <i>pressed</i>, and
	 * runs the appropriate method or function associated with pressing that object.
	 * @param x - Defines the horizontal position of the mouse from the left of the window in pixels.
	 * @param y - Defines the vertical position of the mouse from the top of the window in pixels.
	 */
	public void mousePressed(int x, int y);
	
	/**
	 * Checks if the position of a mouse is within bounds of an object after being <i>pressed</i>, and
	 * runs the appropriate method or function associated after pressing that object.
	 * @param x - Defines the horizontal position of the mouse from the left of the window in pixels.
	 * @param y - Defines the vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseReleased(int x, int y);
	
	/**
	 * Defines that all game scenes must be able to listen to mouse dragging.
	 * @param x - Defines the horizontal position of the mouse from the left of the window in pixels.
	 * @param y - Defines the vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseDragged(int x, int y);
}
