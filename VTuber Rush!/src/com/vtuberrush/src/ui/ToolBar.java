package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.scenes.Editing;

/**
 * A <b><i>toolbar</i></b> is a subclass of a <b><i>bar</i></b> used for selecting and placing
 * down tiles in the game's built-in level editor. <br>
 * A toolbar contains a collection of <b><i>Tile Buttons</i></b> which can be selected and placed
 * down when clicking on the playing field.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class ToolBar extends Bar {

	private Editing editing;
	private Button buttonMenu, buttonSave;
	private Button buttonStart, buttonEnd;
	private BufferedImage imageStart, imageEnd;
	private Tile selectedTile;
	
	private Map<Button, ArrayList<Tile>> map = new HashMap<Button, ArrayList<Tile>>();
	private Button buttonGrass, buttonRoad, buttonWater;
	private Button buttonWaterCorners, buttonWaterEdges, buttonGrassVariants, buttonGrassEdges, buttonGrassCorners;
	private Button currentButton;
	private int variantIndex = 0;
	
	/**
	 * Creates a new bar in defined location, and initializes all tile buttons and tile sprites.
	 * @param x - The horizontal position of the bar from the left of the window in pixels.
	 * @param y - The vertical position of the bar from the top of the window in pixels.
	 * @param width - The horizontal size of the bar in pixels.
	 * @param height - The vertical size of the bar in pixels.
	 * @param editing - Defines the specific instance of the scene where the tool bar is initialized.
	 */
	public ToolBar(int x, int y, int width, int height, Editing editing) {
		super(x, y, width, height);
		this.editing = editing;
		initButtons();
		initImages();
	}
	
	/**
	 * Initializes all clickable tile buttons in the tool bar.
	 */
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 30);
		buttonSave = new Button("Save", 10, 595, 80, 30);
		int i = 0;
		
		buttonGrass = new Button("Grass", 100, 560, 50, 50, i++);
		buttonRoad = new Button("Road", 160, 560, 50, 50, i++);
		buttonWater = new Button("Water", 220, 560, 50, 50, i++);
		initButtonsMap(buttonWaterCorners, editing.getGame().getTileManager().getTilesWaterCorners(), 100, 560, 50, 50, i++);
		initButtonsMap(buttonWaterEdges, editing.getGame().getTileManager().getTilesWaterEdges(), 100, 560, 50, 50, i++);
		initButtonsMap(buttonGrassVariants, editing.getGame().getTileManager().getTilesGrassVariants(), 100, 560, 50, 50, i++);
		initButtonsMap(buttonGrassEdges, editing.getGame().getTileManager().getTilesGrassEdges(), 100, 560, 50, 50, i++);
		initButtonsMap(buttonGrassCorners, editing.getGame().getTileManager().getTilesGrassCorners(), 100, 560, 50, 50, i++);
		
		buttonStart = new Button("Start", 100, 620, 50, 50);
		buttonEnd = new Button("End", 160, 620, 50, 50);
	}
	
	/**
	 * Initializes all tile sprites used for the tile buttons in the tool bar.
	 */
	private void initImages() {
		imageStart = LoadSave.getSpriteAtlas().getSubimage(128, 0, 32, 32);
		imageEnd = LoadSave.getSpriteAtlas().getSubimage(160, 0, 32, 32);
	}
	
	/**
	 * Maps a tile button to each specified tile category (e.g. grass corners, grass variants, etc.).
	 * @param button - The button assigning to a tile category.
	 * @param tiles - The tile category being assigned to.
	 * @param x - The initial horizontal position of the button from the left of the window in pixels.
	 * @param y - The vertical position of the button from the top of the window in pixels.
	 * @param width - The horizontal size of the button in pixels.
	 * @param height - The vertical size of the button in pixels.
	 * @param id - The unique identifier of each button.
	 */
	private void initButtonsMap(Button button, ArrayList<Tile> tiles, int x, int y, int width, int height, int id) {
		button = new Button("", x + (60 * id), y, width, height, id);
		map.put(button, tiles);
	}
	
	/**
	 * Renders the tool bar and all buttons.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(62, 69, 87, 200));
		graphics.fillRect(x, y, width, height);
		drawButtons(graphics);
	}
	
	/**
	 * Renders all tile buttons and menu and save standard buttons.
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
		buttonSave.draw(graphics);
		drawButtonsUtility(graphics, buttonStart, imageStart);
		drawButtonsUtility(graphics, buttonEnd, imageEnd);
		drawButtonsSingle(graphics, buttonGrass);
		drawButtonsSingle(graphics, buttonRoad);
		drawButtonsSingle(graphics, buttonWater);
		drawButtonsMap(graphics);
	}
	
	/**
	 * Renders to a tile button a tile sprite containing only a single tile (e.g. road base, grass base, etc.).
	 * @param graphics - Responsible for drawing objects to the screen.
	 * @param button - Contains information to where the tile sprite is rendered.
	 */
	private void drawButtonsSingle(Graphics graphics, Button button) {
		graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
		drawButtonsFeedback(graphics, button);
	}
	
	/**
	 * Renders to a tile button the first tile sprite of a tile category (e.g. grass corners, grass variants, etc.)
	 * @param graphics - Responsible for drawing objects to the screen.
	 */
	private void drawButtonsMap(Graphics graphics) {
		for(Map.Entry<Button, ArrayList<Tile>> entry : map.entrySet()) {
			Button button = entry.getKey();
			BufferedImage image = entry.getValue().get(0).getSprite();
			graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
			drawButtonsFeedback(graphics, button);
		}
	}
	
	/**
	 * Renders to a tile button the tile sprites for setting the <i>start</i> and <i>end</i> of a level.
	 * @param graphics - Responsible for drawing objects to the screen.
	 * @param button - Contains information to where the tile sprite is rendered.
	 * @param image - The image of the <i>start</i> and <i>end</i> flags.
	 */
	private void drawButtonsUtility(Graphics graphics, Button button, BufferedImage image) {
		graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
		drawButtonsFeedback(graphics, button);
	}
	
	/**
	 * Dynamically updates the border color of the passed button when it is being hovered or pressed.
	 * @param graphics - Responsible for drawing objects to the screen.
	 * @param button - Contains information to which button is being updated.
	 */
	private void drawButtonsFeedback(Graphics graphics, Button button) {
		//mouseOver
		if(button.isMouseOver()) {
			graphics.setColor(Color.white);
		} else {
			graphics.setColor(Color.black);
		}
		
		graphics.drawRect(button.x, button.y, button.width, button.height);
		
		//mousePressed
		if(button.isMousePressed()) {
			graphics.setColor(new Color(150,150,150));
			graphics.drawRect(button.x, button.y, button.width, button.height);
		}
	}
	
	/**
	 * Switches the current variant or rotation of a tile category.
	 */
	public void getVariant() {
		variantIndex = (variantIndex + 1) % map.get(currentButton).size();
		selectedTile = map.get(currentButton).get(variantIndex);
		editing.setSelectedTile(selectedTile);
	}
	
	/**
	 * Returns the image of a tile given its ID. <br>
	 * Called by other methods when drawing single tile buttons.
	 * @param id - The unique identifier of a tile.
	 * @return <b>Tile</b> - The image of a tile given its ID.
	 */
	private BufferedImage getButtonImage(int id) {
		return editing.getGame().getTileManager().getSprite(id);
	}
	
	/**
	 * Saves the level. Called when clicking on the save level button.
	 */
	private void saveLevel() {
		editing.saveLevel();
	}
	
	/**
	 * Traverses the list of buttons and checks if a mouse being clicked is within bounds of a button, executing the
	 * relevant method associated with the button.
	 * @param x - The horizontal position of the mouse from the left of the window in pixels.
	 * @param y - The vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		} else if (buttonSave.getBounds().contains(x, y)) {
			saveLevel();
		} else if (buttonGrass.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(buttonGrass.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else if (buttonRoad.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(buttonRoad.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else if (buttonWater.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(buttonWater.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else if (buttonStart.getBounds().contains(x, y)) {
			selectedTile = new Tile(imageStart, -1, -1);
			editing.setSelectedTile(selectedTile);
			return;
		} else if (buttonEnd.getBounds().contains(x, y)) {
			selectedTile = new Tile(imageEnd, -2, -2);
			editing.setSelectedTile(selectedTile);
			return;
		} else {
			for (Button button : map.keySet()) {
				if (button.getBounds().contains(x, y)) {
					selectedTile = map.get(button).get(0);
					editing.setSelectedTile(selectedTile);
					currentButton = button;
					variantIndex = 0;
					return;
				}
			}
		}
	}

	/**
	 * Traverses the list of buttons and checks if the position of a mouse is within bounds of a button, dynamically
	 * updating the color of the tile button when <i>hovered</i>.<br>
	 * If the mouse is not hovered, the <i>hovered</i> condition is set to <b><i>false</i></b> by default.
	 * @param x - The horizontal position of the mouse from the left of the window in pixels.
	 * @param y - The vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseMoved(int x, int y) {
		
		//Default
		buttonMenu.setMouseOver(false);
		buttonSave.setMouseOver(false);
		buttonGrass.setMouseOver(false);
		buttonRoad.setMouseOver(false);
		buttonWater.setMouseOver(false);
		buttonStart.setMouseOver(false);
		buttonEnd.setMouseOver(false);
		for (Button button : map.keySet()) {
			button.setMouseOver(false);
		}
		
		//Hovered
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		} else if (buttonSave.getBounds().contains(x, y)) {
			buttonSave.setMouseOver(true);
		} else if (buttonGrass.getBounds().contains(x, y)) {
			buttonGrass.setMouseOver(true);
		} else if (buttonRoad.getBounds().contains(x, y)) {
			buttonRoad.setMouseOver(true);
		} else if (buttonWater.getBounds().contains(x, y)) {
			buttonWater.setMouseOver(true);
		} else if (buttonStart.getBounds().contains(x, y)) {
			buttonStart.setMouseOver(true);
		} else if (buttonEnd.getBounds().contains(x, y)) {
			buttonEnd.setMouseOver(true);
		} else {
			for (Button button : map.keySet()) {
				if(button.getBounds().contains(x, y)) {
					button.setMouseOver(true);
					return;
				}
			}
		}
		
	}

	/**
	 * Traverses the list of buttons and checks if the mouse being pressed is within bounds of a button, dynamically
	 * updating the border color of the tile button when <i>pressed</i>.<br>
	 * @param x - The horizontal position of the mouse from the left of the window in pixels.
	 * @param y - The vertical position of the mouse from the top of the window in pixels.
	 */
	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		} else if (buttonSave.getBounds().contains(x, y)) {
			buttonSave.setMousePressed(true);
		} else if (buttonGrass.getBounds().contains(x, y)) {
			buttonGrass.setMousePressed(true);
		} else if (buttonRoad.getBounds().contains(x, y)) {
			buttonRoad.setMousePressed(true);
		} else if (buttonWater.getBounds().contains(x, y)) {
			buttonWater.setMousePressed(true);
		} else if (buttonStart.getBounds().contains(x, y)) {
			buttonStart.setMousePressed(true);
		} else if (buttonEnd.getBounds().contains(x, y)) {
			buttonEnd.setMousePressed(true);
		} else {
			for (Button button : map.keySet()) {
				if(button.getBounds().contains(x, y)) {
					button.setMousePressed(true);
					return;
				}
			}
		}
		
	}
	
	/**
	 * Dynamically resets all buttons after a button is <i>pressed</i>.
	 * @param x - The horizontal position of the mouse from the left of the window in pixels.
	 * @param y - The vertical position of the mouse from the top of the window in pixels.
	 */
	public void mouseReleased(int x, int y) {
		buttonMenu.resetButtons();
		buttonSave.resetButtons();
		buttonGrass.resetButtons();
		buttonRoad.resetButtons();
		buttonWater.resetButtons();
		buttonStart.resetButtons();
		buttonEnd.resetButtons();
		for (Button button : map.keySet()) {
			button.resetButtons();
		}
	}

	/**
	 * Returns the tile sprite of the <i>start</i> flag.
	 * @return {@link #imageStart}
	 */
	public BufferedImage getImageStart() {
		return imageStart;
	}

	/**
	 * Returns the tile sprite of the <i>end</i> flag.
	 * @return {@link #imageEnd}
	 */
	public BufferedImage getImageEnd() {
		return imageEnd;
	}
	
}
