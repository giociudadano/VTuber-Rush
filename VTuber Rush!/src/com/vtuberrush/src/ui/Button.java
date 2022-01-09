package com.vtuberrush.src.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A <b><i>button</i></b> is a clickable object that interacts with the mouse listener to
 * run certain functions when hovering and releasing the mouse.<br>
 * All button objects inherit the same color, border, font, and center text alignment.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Button {
	
	/**
	 * Defines the horizontal position of this button from the left of the window in pixels.
	 */
	public int x;
	
	/**
	 * Defines the vertical position of this button from the top of the window in pixels.
	 */
	public int y;
	
	/**
	 * Defines the horizontal size of the button in pixels.
	 */
	public int width;
	
	/**
	 * Defines the vertical size of the button in pixels.
	 */
	public int height;
	
	/**
	 * Defines the ID of the tile being selected <i>(for level editor buttons only)</i>.
	 */
	public int id;
	
	private String text;
	private Rectangle bounds;
	private boolean mouseOver, mousePressed;
	
	/**
	 * Creates a new button at the location with the passed height, width, and renderable text.<br>
	 * This constructor is used for creating menu buttons.
	 * @param text - The text being rendered.
	 * @param x - The horizontal position of the button from the left of the window in pixels.
	 * @param y - The vertical position of the button from the top of the window in pixels.
	 * @param width - The horizontal size of the button in pixels.
	 * @param height - The vertical size of the button in pixels.
	 */
	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initBounds();
	}
	
	/**
	 * Creates a new button at the location with the passed height, width, and selected tile for editing<br>
	 * This constructor is used for creating level editor buttons.
	 * @param text - The text being rendered.
	 * @param x - The horizontal position of the button from the left of the window in pixels.
	 * @param y - The vertical position of the button from the top of the window in pixels.
	 * @param width - The vertical size of the button.
	 * @param height - The horizontal size of the button.
	 * @param id - The ID of the tile being selected for placing in the level editor.
	 */
	public Button(String text, int x, int y, int width, int height, int id) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		initBounds();
	}
	
	/**
	 * Renders the body, border, and text of the button.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	public void draw(Graphics graphics) {
		drawBody(graphics);
		drawBorder(graphics);
		drawText(graphics);
	}
	
	/**
	 * Renders the body of the button and dynamically checks and updates the color if the button is being hovered.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	private void drawBody(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(mouseOver) {
			graphics.setColor(new Color(229, 226, 219));
		} else {
			graphics.setColor(new Color(229, 226, 219, 200));
		}
		graphics2d.fillRoundRect(x, y, width, height, (int)(width * 0.3), height);
	}
	
	/**
	 * Renders the border of the button and dynamically checks and updates the color of the border if the button is being pressed.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	private void drawBorder(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(mousePressed) {
			graphics.setColor(new Color(241, 239, 237));
		} else {
			graphics.setColor(new Color(219, 212, 204, 200));
		}
		graphics2d.drawRoundRect(x+2, y+2, width-4, height-4, (int)((width * 0.3)-4), height-4);
	}

	/**
	 * Aligns the text to center and renders the text.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	private void drawText(Graphics graphics) {
		int textWidth = graphics.getFontMetrics().stringWidth(text);
		int textHeight = graphics.getFontMetrics().getHeight();
		graphics.setColor(new Color(79, 88, 106));
		graphics.drawString(text, x + (width - textWidth) / 2, y + (height + textHeight) / 2);
	}

	/**
	 * Returns the bounds or four rectangular coordinates (x, y) encompassing the button. <br>
	 * Used to detect the range of the button if it is being hovered, pressed, or clicked.
	 */
	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	/**
	 * Resets the state of all buttons. Called when a button is clicked.
	 */
	public void resetButtons() {
		this.mouseOver = false;
		this.mousePressed = false;
	}
	
	/**
	 * Returns <b><i>true</i></b> if the button is being hovered.
	 * @return {@link #mouseOver}
	 */
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	/**
	 * Returns <b><i>true</i></b> if the button is being pressed.
	 * @return {@link #mousePressed}
	 */
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	/**
	 * Updates the <i>pressed</i> state of this button. <br>
	 * Called when the mouse is being clicked and is within bounds of this button.
	 * @param mousePressed - The state of the button being updated.
	 */
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	/**
	 * Updates the <i>hovered</i> state of this button. <br>
	 * Called when the position of the mouse is within bounds of this button.
	 * @param mouseOver - The state of the button being updated.
	 */
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	
	/**
	 * Updates the text of this button. <br>
	 * Called when pausing and unpausing the game.
	 * @param text - The text being updated.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the rectangular bounds of this button, defined as four rectangular coordinates (x, y) from
	 * the top and left of the window in pixels.<br>
	 * Called when detecting whether the position of the mouse is within the button.
	 * @return {@link #bounds}
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Returns the unique identifier for this button, used as the index in the <b><i>buttons</i></b> [Button] array list.
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}
	
	
}
