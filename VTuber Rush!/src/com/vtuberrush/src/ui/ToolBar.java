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
	
	public ToolBar(int x, int y, int width, int height, Editing editing) {
		super(x, y, width, height);
		this.editing = editing;
		initButtons();
		initImages();
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 25);
		buttonSave = new Button("Save", 10, 590, 80, 25);
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
	
	private void initImages() {
		imageStart = LoadSave.getSpriteAtlas().getSubimage(128, 0, 32, 32);
		imageEnd = LoadSave.getSpriteAtlas().getSubimage(160, 0, 32, 32);
	}
	
	private void initButtonsMap(Button button, ArrayList<Tile> tiles, int x, int y, int width, int height, int id) {
		button = new Button("", x + (60 * id), y, width, height, id);
		map.put(button, tiles);
	}
	

	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(x, y, width, height);
		drawButtons(graphics);
	}
	
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
	
	private void drawButtonsSingle(Graphics graphics, Button button) {
		graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
		drawButtonsFeedback(graphics, button);
	}
	
	private void drawButtonsMap(Graphics graphics) {
		for(Map.Entry<Button, ArrayList<Tile>> entry : map.entrySet()) {
			Button button = entry.getKey();
			BufferedImage image = entry.getValue().get(0).getSprite();
			graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
			drawButtonsFeedback(graphics, button);
		}
	}
	
	private void drawButtonsUtility(Graphics graphics, Button button, BufferedImage image) {
		graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
		drawButtonsFeedback(graphics, button);
	}
	
	
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
	
	public void getVariant() {
		variantIndex = (variantIndex + 1) % map.get(currentButton).size();
		selectedTile = map.get(currentButton).get(variantIndex);
		editing.setSelectedTile(selectedTile);
	}
	
	private BufferedImage getButtonImage(int id) {
		return editing.getGame().getTileManager().getSprite(id);
	}
	
	private void saveLevel() {
		editing.saveLevel();
	}
	
	//Mouse Methods
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

	public BufferedImage getImageStart() {
		return imageStart;
	}

	public BufferedImage getImageEnd() {
		return imageEnd;
	}
	
}
