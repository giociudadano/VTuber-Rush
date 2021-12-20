package com.vtuberrush.src.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {
	private int x, y, width, height;
	private String text;
	private Rectangle bounds;
	private boolean mouseOver, mousePressed;
	
	private static Color colorOver = new Color(235,235,235);
	private static Color colorPressed = new Color(150,150,150);
	
	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initBounds();
	}
	
	public void draw(Graphics graphics) {
		drawBody(graphics);
		drawBorder(graphics);
		drawText(graphics);
	}
	
	private void drawBody(Graphics graphics) {
		if(mouseOver) {
			graphics.setColor(colorOver);
		} else {
			graphics.setColor(Color.white);
		}
		graphics.fillRect(x, y, width, height);
	}
	
	private void drawBorder(Graphics graphics) {
		if(mousePressed) {
			graphics.setColor(colorPressed);
		} else {
			graphics.setColor(Color.black);
		}
		graphics.drawRect(x, y, width, height);
	}

	private void drawText(Graphics graphics) {
		int textWidth = graphics.getFontMetrics().stringWidth(text);
		int textHeight = graphics.getFontMetrics().getHeight();
		
		graphics.drawString(text, x + (width - textWidth) / 2, y + (height + textHeight) / 2);
	}

	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void resetButtons() {
		this.mouseOver = false;
		this.mousePressed = false;
	}
}
