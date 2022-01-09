package com.vtuberrush.src.managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.helpers.LoadSave;

import static com.vtuberrush.src.helpers.Constants.Tiles.WATER;
import static com.vtuberrush.src.helpers.Constants.Tiles.GRASS;
import static com.vtuberrush.src.helpers.Constants.Tiles.ROAD;

/**
 * Facilitates the rendering of tiles.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class TileManager {
	
	public Tile GRASS_BASE, GRASS_VAR_A, GRASS_VAR_B, GRASS_VAR_C;
	public Tile GRASS_EDGE_T, GRASS_EDGE_B, GRASS_EDGE_L, GRASS_EDGE_R, GRASS_EDGE_TL, GRASS_EDGE_TR, GRASS_EDGE_BL, GRASS_EDGE_BR;
	public Tile ROAD_BASE;
	public Tile WATER_BASE, WATER_EDGE_TL, WATER_EDGE_TR, WATER_EDGE_BL, WATER_EDGE_BR, WATER_EDGE_T, WATER_EDGE_B, WATER_EDGE_L, WATER_EDGE_R; 
	
	/**
	 * Stores the sprites of all tiles in the form of subimages encompassing the larger image atlas.
	 */
	public BufferedImage atlas;
	
	/**
	 * Stores an array list of <b><i>Tile</i></b> objects used for rendering a level.
	 * <br> Each tile is assigned to a smaller array list which is then appended to this main collection.
	 */
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	/**
	 * A sub-array list containing {@link #GRASS_BASE} variants composed of
	 * {@link #GRASS_VAR_A}, {@link #GRASS_EDGE_B}, and {@link #GRASS_VAR_C}.
	 */
	public ArrayList<Tile> tilesGrassVariants = new ArrayList<>();
	
	/**
	 * A sub-array list containing grass edges composed of
	 * {@link #GRASS_EDGE_T}, {@link #GRASS_EDGE_B}, {@link #GRASS_EDGE_L}, and {@link #GRASS_EDGE_R}.
	 */
	public ArrayList<Tile> tilesGrassEdges = new ArrayList<>();
	
	/**
	 * A sub-array list containing grass corners composed of
	 * {@link #GRASS_EDGE_TL}, {@link #GRASS_EDGE_TR}, {@link #GRASS_EDGE_BL}, and {@link #GRASS_EDGE_BR}.
	 */
	public ArrayList<Tile> tilesGrassCorners = new ArrayList<>();
	
	/**
	 * A sub-array list containing water edges composed of
	 * {@link #WATER_EDGE_T}, {@link #WATER_EDGE_B}, {@link #WATER_EDGE_L}, and {@link #WATER_EDGE_R}.
	 */
	public ArrayList<Tile> tilesWaterEdges = new ArrayList<>();
	
	/**
	 * A sub-array list containing water corners composed of
	 * {@link #WATER_EDGE_TL}, {@link #WATER_EDGE_TR}, {@link #WATER_EDGE_BL}, and {@link #WATER_EDGE_BR}.
	 */
	public ArrayList<Tile> tilesWaterCorners = new ArrayList<>();
	
	/**
	 * Initializes all <b><i>Tile</i></b> objects used in the game.
	 */
	public TileManager() {
		loadAtlas();
		createTiles();
	}

	/**
	 * Initializes the image atlas used for mapping subimages to tiles as sprites.
	 */
	private void loadAtlas() {
		atlas = LoadSave.getSpriteAtlas();
	}
	
	/**
	 * Initializes all <b><i>Tile</i></b> objects that are placeable in the built-in
	 * level editor to the <b><i>tiles</i></b> [Tile] array list.
	 */
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
		
		tilesGrassEdges.add(GRASS_EDGE_T = new Tile(getSprite(2,1),id++, GRASS));
		tilesGrassEdges.add(GRASS_EDGE_B = new Tile(getSprite(1,1),id++, GRASS));
		tilesGrassEdges.add(GRASS_EDGE_L = new Tile(getSprite(3,1),id++, GRASS));
		tilesGrassEdges.add(GRASS_EDGE_R = new Tile(getSprite(4,1),id++, GRASS));
		
		tilesGrassCorners.add(GRASS_EDGE_TL = new Tile(getSprite(4,3), id++, GRASS));
		tilesGrassCorners.add(GRASS_EDGE_TR = new Tile(getSprite(5,3), id++, GRASS));
		tilesGrassCorners.add(GRASS_EDGE_BL = new Tile(getSprite(6,3), id++, GRASS));
		tilesGrassCorners.add(GRASS_EDGE_BR = new Tile(getSprite(7,3), id++, GRASS));
		
		tiles.addAll(tilesWaterCorners);
		tiles.addAll(tilesWaterEdges);
		tiles.addAll(tilesGrassVariants);
		tiles.addAll(tilesGrassEdges);
		tiles.addAll(tilesGrassCorners);
	}
	
	/**
	 * Returns a tile object with the passed id. Used in placing tiles in the level editor.
	 * @param id - The unique identifier of the tile.
	 * @return <b>tile</b> - The tile with the passed id. 
	 */
	public Tile getTile(int id) {
		return tiles.get(id);
	}
	
	/**
	 * Returns the image of the tile with the passed id. Used in displaying tiles before placing it.
	 * @param id - The unique identifier of the tile.
	 * @return <b>image</b> - The image of the tile with the passed id.
	 */
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}
	
	/**
	 * Returns the image of the tile with the passed grid coordinates.
	 * Used when first initializing a new <b><i>Tile</i></b>.
	 * @param x - Defines the vertical position of the unit from the left of the window in number of tiles.
	 * @param y - Defines the horizontal position of the unit from the top of the window in number of tiles.
	 * @return <b>subimage</b> - A portion of the image atlas containing the sprite of the tile.
	 */
	private BufferedImage getSprite(int x, int y) {
		return atlas.getSubimage(x*32, y*32, 32, 32);
	}
	
	/**
	 * Returns the frame of an animated tile with the passed grid coordinates.
	 * Used when first initializing a new animated <b><i>Tile</i></b>.
	 * @param id - The unique identifier of the tile.
	 * @param frame - The current frame of the animated tile.
	 * @return <b>image</b> - The image of the tile with the passed id in the defined frame.
	 */
	public BufferedImage getSpriteAnimated(int id, int frame) {
		return tiles.get(id).getSprite(frame);
	}
	
	/**
	 * Returns <b><i>true</i></b> if the tile is animated.
	 * @param id - The unique identifier of the tile.
	 */
	public boolean isAnimated(int id) {
		return tiles.get(id).isAnimated();
	}
	
	/**
	 * Returns an array list of all grass variants.
	 * @return {@link #tilesGrassVariants}
	 */
	public ArrayList<Tile> getTilesGrassVariants() {
		return tilesGrassVariants;
	}

	/**
	 * Returns an array list of all grass edges.
	 * @return {@link #tilesGrassEdges}
	 */
	public ArrayList<Tile> getTilesGrassEdges() {
		return tilesGrassEdges;
	}

	/**
	 * Returns an array list of all grass corners.
	 * @return {@link #tilesGrassCorners}
	 */
	public ArrayList<Tile> getTilesGrassCorners() {
		return tilesGrassCorners;
	}

	/**
	 * Returns an array list of all water edges.
	 * @return {@link #tilesWaterEdges}
	 */
	public ArrayList<Tile> getTilesWaterEdges() {
		return tilesWaterEdges;
	}

	/**
	 * Returns an array list of all water corners.
	 * @return {@link #tilesWaterCorners}
	 */
	public ArrayList<Tile> getTilesWaterCorners() {
		return tilesWaterCorners;
	}

}
