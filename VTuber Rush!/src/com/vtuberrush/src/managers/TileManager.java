package com.vtuberrush.src.managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.helpers.LoadSave;

public class TileManager {
	
	public Tile GRASS, WATER, ROAD;
	public BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	public TileManager() {
		loadAtlas();
		createTiles();
	}

	private void loadAtlas() {
		atlas = LoadSave.getSpriteAtlas();
	}
	
	private void createTiles() {
		tiles.add(GRASS = new Tile(getSprite(0,0)));
		tiles.add(WATER = new Tile(getSprite(1,0)));
		tiles.add(ROAD = new Tile(getSprite(2,0)));
	}
	
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}
	
	private BufferedImage getSprite(int x, int y) {
		return atlas.getSubimage(x*64, y*64, 64, 64);
	}

}
