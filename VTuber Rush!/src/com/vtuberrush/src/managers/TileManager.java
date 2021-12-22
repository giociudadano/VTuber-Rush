package com.vtuberrush.src.managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.helpers.ImageBuilder;
import com.vtuberrush.src.helpers.LoadSave;

public class TileManager {
	
	public Tile GRASS, GRASS_A, GRASS_B, GRASS_C;
	public Tile ROAD, ROAD_EDGE_T, ROAD_EDGE_B;
	public Tile WATER, WATER_EDGE_BL, WATER_EDGE_TL, WATER_EDGE_TR, WATER_EDGE_BR, WATER_EDGE_B, WATER_EDGE_T, WATER_EDGE_L, WATER_EDGE_R; 
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
		int id = 0;
		
		tiles.add(GRASS = new Tile(getSprite(0,6),id++,"Grass A"));
		tiles.add(ROAD = new Tile(getSprite(7,12),id++,"Road"));
		tiles.add(WATER = new Tile(getSprite(0,13),id++,"Water"));
		
		tiles.add(WATER_EDGE_BL = new Tile(getSprite(0,12),id++,"Water Edge Bottom Left"));
		tiles.add(WATER_EDGE_TL = new Tile(getSprite(2,11), id++, "Water Edge Top Left"));
		tiles.add(WATER_EDGE_TR = new Tile(getSprite(4,11), id++, "Water Edge Top Right"));
		tiles.add(WATER_EDGE_BR = new Tile(getSprite(6,11), id++, "Water Edge Bottom Right"));
		tiles.add(WATER_EDGE_B = new Tile(getSprite(4, 10), id++, "Water Edge Bottom"));
		tiles.add(WATER_EDGE_T = new Tile(getSprite(4, 9), id++, "Water Edge Top"));
		tiles.add(WATER_EDGE_L = new Tile(getSprite(0, 9), id++, "Water Edge Left"));
		tiles.add(WATER_EDGE_R = new Tile(getSprite(0, 10), id++, "Water Edge Right"));
		
		tiles.add(GRASS_A = new Tile(getSprite(1,6),id++,"Grass B"));
		tiles.add(GRASS_B = new Tile(getSprite(2,6),id++,"Grass C"));
		tiles.add(GRASS_C = new Tile(getSprite(3,6),id++,"Grass D"));
		
		tiles.add(ROAD_EDGE_T = new Tile(getSprite(4,3),id++,"Road Edge Top"));
		tiles.add(ROAD_EDGE_B = new Tile(getSprite(4,2),id++,"Road Edge Bottom"));
	}
	
	public Tile getTile(int id) {
		return tiles.get(id);
	}
	
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}
	
	private BufferedImage getSprite(int x, int y) {
		return atlas.getSubimage(x*32, y*32, 32, 32);
	}
	
	private BufferedImage[] getSpriteArray(int x1, int y1, int x2, int y2) {
		return new BufferedImage[] {getSprite(x1, y1), getSprite(x2, y2)};
	}

}
