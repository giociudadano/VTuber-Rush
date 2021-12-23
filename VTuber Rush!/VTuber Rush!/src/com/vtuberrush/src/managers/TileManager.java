package com.vtuberrush.src.managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.helpers.ImageBuilder;
import com.vtuberrush.src.helpers.LoadSave;

import static com.vtuberrush.src.helpers.Constants.Tiles.*;

public class TileManager {
	
	public Tile GRASS_BASE, GRASS_VAR_A, GRASS_VAR_B, GRASS_VAR_C;
	public Tile ROAD_BASE, ROAD_EDGE_T, ROAD_EDGE_B, ROAD_EDGE_L, ROAD_EDGE_R;
	public Tile WATER_BASE, WATER_EDGE_TL, WATER_EDGE_TR, WATER_EDGE_BL, WATER_EDGE_BR, WATER_EDGE_T, WATER_EDGE_B, WATER_EDGE_L, WATER_EDGE_R; 
	public BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	public ArrayList<Tile> tilesGrassVariants = new ArrayList<>();
	public ArrayList<Tile> tilesRoadEdges = new ArrayList<>();
	public ArrayList<Tile> tilesWaterEdges = new ArrayList<>();
	public ArrayList<Tile> tilesWaterCorners = new ArrayList<>();
	
	public TileManager() {
		loadAtlas();
		createTiles();
	}

	private void loadAtlas() {
		atlas = LoadSave.getSpriteAtlas();
	}
	
	private void createTiles() {
		int id = 0;
		
		tiles.add(GRASS_BASE = new Tile(getSprite(0,0),id++, GRASS));
		tiles.add(ROAD_BASE = new Tile(getSprite(0,1),id++, ROAD));
		tiles.add(WATER_BASE = new Tile(getSprite(0,2),id++, WATER));
		
		tilesWaterCorners.add(WATER_EDGE_TL = new Tile(getSprite(0,3), id++, WATER));
		tilesWaterCorners.add(WATER_EDGE_TR = new Tile(getSprite(1,3), id++, WATER));
		tilesWaterCorners.add(WATER_EDGE_BL = new Tile(getSprite(2,3), id++, WATER));
		tilesWaterCorners.add(WATER_EDGE_BR = new Tile(getSprite(3,3), id++, WATER));
		
		tilesWaterEdges.add(WATER_EDGE_T = new Tile(getSprite(1, 2), id++, WATER));
		tilesWaterEdges.add(WATER_EDGE_B = new Tile(getSprite(2, 2), id++, WATER));
		tilesWaterEdges.add(WATER_EDGE_L = new Tile(getSprite(3, 2), id++, WATER));
		tilesWaterEdges.add(WATER_EDGE_R = new Tile(getSprite(4, 2), id++, WATER));
		
		tilesGrassVariants.add(GRASS_VAR_A = new Tile(getSprite(1,0),id++, GRASS));
		tilesGrassVariants.add(GRASS_VAR_B = new Tile(getSprite(2,0),id++, GRASS));
		tilesGrassVariants.add(GRASS_VAR_C = new Tile(getSprite(3,0),id++, GRASS));
		
		tilesRoadEdges.add(ROAD_EDGE_T = new Tile(getSprite(2,1),id++, GRASS));
		tilesRoadEdges.add(ROAD_EDGE_B = new Tile(getSprite(1,1),id++, GRASS));
		tilesRoadEdges.add(ROAD_EDGE_L = new Tile(getSprite(3,1),id++, GRASS));
		tilesRoadEdges.add(ROAD_EDGE_R = new Tile(getSprite(4,1),id++, GRASS));
		
		tiles.addAll(tilesWaterCorners);
		tiles.addAll(tilesWaterEdges);
		tiles.addAll(tilesGrassVariants);
		tiles.addAll(tilesRoadEdges);
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
	
	public BufferedImage getSpriteAnimated(int id, int frame) {
		return tiles.get(id).getSprite(frame);
	}
	
	private BufferedImage[] getSpriteArray(int x, int y) {
		BufferedImage[] array = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			array[i] = getSprite(x+i, y);
		}
		return array;
	}
	
	private BufferedImage[] getSpriteArray(int x1, int y1, int x2, int y2) {
		return new BufferedImage[] {getSprite(x1, y1), getSprite(x2, y2)};
	}
	
	public boolean isAnimated(int id) {
		return tiles.get(id).isAnimated();
	}
	
	public ArrayList<Tile> getTilesGrassVariants() {
		return tilesGrassVariants;
	}

	public ArrayList<Tile> getTilesRoadEdges() {
		return tilesRoadEdges;
	}


	public ArrayList<Tile> getTilesWaterEdges() {
		return tilesWaterEdges;
	}


	public ArrayList<Tile> getTilesWaterCorners() {
		return tilesWaterCorners;
	}

	
	

}
