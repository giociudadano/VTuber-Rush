package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.ui.ToolBar;

import static com.vtuberrush.src.helpers.Constants.Tiles.ROAD;

/**
 * An <b><i>editing game scene</i></b> is a subclass of a game scene that facilitates the
 * editing and modification of level tiles.
 * the mouse with other game objects such as buttons, and allows interacting
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Editing extends GameScene implements SceneMethods{
	
	private int[][] level;
	private Tile selectedTile;
	private ToolBar toolBar;

	private int mouseX, mouseY;
	private int xLast, yLast;
	private boolean drawSelectedTile = false;
	
	private Flag start, end;

	/**
	 * Creates a new <b><i>editing game scene</i></b>, attempts to load an
	 * existing game level, and initializes a new toolbar object.
	 * @param game - The current instance of the game.
	 */
	public Editing(Game game) {
		super(game);
		loadLevelDefault();
		toolBar = new ToolBar(0,550,1280,200,this);
	}
	
	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
		ArrayList<Flag> flags = LoadSave.readFlags("new_level");
		start = flags.get(0);
		end = flags.get(1);
	}
	
	/**
	 * Updates the animation of all tiles in the editing field.
	 */
	public void tick() {
		tickAnimation();
	}

	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		toolBar.draw(graphics);
		drawSelectedTile(graphics);
		drawFlags(graphics);
	}

	private void drawLevel(Graphics graphics) {
		graphics.setColor(new Color(114, 195, 122));
		graphics.fillRect(0, 0, 1280, 720);
		for(int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				int id = level[y][x];
				if(isAnimated(id)) {
					graphics.drawImage(getSprite(id, frame), x*32, y*32, null);
				} else {
					graphics.drawImage(getSprite(id), x*32, y*32, null);
				}
			}
		}
	}
	
	/**
	 * Saves all changes made in the level editor to the level file and updates the
	 * current instance of the level across the editing and playing fields.
	 */
	public void saveLevel() {
		LoadSave.saveLevel("new_level", level, start, end);
		game.getPlaying().setLevel(level);
	}
	
	private void drawFlags(Graphics graphics) {
		if (start != null) {
			graphics.drawImage(toolBar.getImageStart(), start.getX() * 32, start.getY() * 32, 32, 32, null);
		}
		
		if (end != null) {
			graphics.drawImage(toolBar.getImageEnd(), end.getX() * 32, end.getY() * 32, 32, 32, null);
		}
	}
	
	private void drawSelectedTile(Graphics graphics) {
		if(selectedTile != null && drawSelectedTile) {
			graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
		}
	}

	/**
	 * Modifies the currently selected tile to the first tile in a category mapped to the new button pressed,
	 * or the next tile in the category of the last selected tile if a new variant was requested.
	 * @param tile - The new tile that is selected.
	 */
	public void setSelectedTile(Tile tile) {
		this.selectedTile = tile;
		drawSelectedTile = true;
	}
	
	private void changeTile(int x, int y) {
		if (selectedTile != null) {
			x = x / 32;
			y = y / 32;
			if (selectedTile.getId() > 0) {
				if (xLast == x && yLast == y) {
					return;
				}
				xLast = x;
				yLast = y;
				level[y][x] = selectedTile.getId();
			} else {
				int id = level[y][x];
				if (game.getTileManager().getTile(id).getTileType() == ROAD) {
					if(selectedTile.getId() == -1) {
						start = new Flag(x, y);
					} else {
						end = new Flag(x, y);
					}
				}
			}
		}
	}

	//Mouse Methods
	@Override
	public void mouseClicked(int x, int y) {
		if (y > 540) {
			toolBar.mouseClicked(x, y);
			drawSelectedTile = false;
		} else {
			changeTile(mouseX, mouseY);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y > 540) {
			toolBar.mouseMoved(x, y);
			drawSelectedTile = false;
		} else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
			drawSelectedTile = true;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y > 540) {
			toolBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		toolBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y < 540) {
			changeTile(x, y);
		}
	}
	
	/**
	 * Dynamically checks if the user has pressed <b>R</b> which changes the current tile
	 * into the next tile in the same category, given that the currently selected tile belongs
	 * to a tile category (e.g. grass corners, grass variants, etc.).
	 * @param e - The event indicating a key was pressed.
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			toolBar.getVariant();
		}
	}

}
