package com.vtuberrush.src.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Button {
	public int x, y, width, height, id;
	private String text;
	private Rectangle bounds;
	private boolean mouseOver, mousePressed;
	
	//UI Buttons
	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initBounds();
	}
	
	//Level Editor Buttons
	public Button(String text, int x, int y, int width, int height, int id) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		initBounds();
	}
	
	public void draw(Graphics graphics) {
		drawBody(graphics);
		drawBorder(graphics);
		drawText(graphics);
	}
	
	private void drawBody(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(mouseOver) {
			graphics.setColor(new Color(229, 226, 219));
		} else {
			graphics.setColor(new Color(229, 226, 219, 200));
		}
		graphics2d.fillRoundRect(x, y, width, height, (int)(width * 0.3), height);
	}
	
	private void drawBorder(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(mousePressed) {
			graphics.setColor(new Color(241, 239, 237));
		} else {
			graphics.setColor(new Color(219, 212, 204, 200));
		}
		graphics2d.drawRoundRect(x+2, y+2, width-4, height-4, (int)((width * 0.3)-4), height-4);
	}

	private void drawText(Graphics graphics) {
		int textWidth = graphics.getFontMetrics().stringWidth(text);
		int textHeight = graphics.getFontMetrics().getHeight();
		graphics.setColor(new Color(79, 88, 106));
		graphics.drawString(text, x + (width - textWidth) / 2, y + (height + textHeight) / 2);
	}

	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void resetButtons() {
		this.mouseOver = false;
		this.mousePressed = false;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public int getId() {
		return id;
	}
	
	
}
