package com.vtuberrush.src.objects;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage[] sprite;
	private int id, tileType;
	
	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public Tile(BufferedImage[] sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}
	
	public int getId() {
		return id;
	}

	public boolean isAnimated() {
		return (sprite.length > 1);
	}

	public int getTileType() {
		return tileType;
	}

	public BufferedImage getSprite() {
		return sprite[0];
	}
	
	public BufferedImage getSprite(int frame) {
		return sprite[frame];
	}
}
