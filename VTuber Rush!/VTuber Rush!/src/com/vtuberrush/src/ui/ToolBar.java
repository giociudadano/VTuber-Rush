package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.scenes.Editing;

public class ToolBar extends Bar {

	private Editing editing;
	private Button buttonMenu, buttonSave;
	private Tile selectedTile;
	
	private Map<Button, ArrayList<Tile>> map = new HashMap<Button, ArrayList<Tile>>();
	private Button buttonGrass, buttonRoad, buttonWater, buttonWaterCorners, buttonWaterEdges, buttonGrassVariants, buttonRoadEdges;
	private Button currentButton;
	private int variantIndex = 0;
	
	public ToolBar(int x, int y, int width, int height, Editing editing) {
		super(x, y, width, height);
		this.editing = editing;
		initButtons();
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
		initButtonsMap(buttonRoadEdges, editing.getGame().getTileManager().getTilesRoadEdges(), 100, 560, 50, 50, i++);
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
		drawButtonsSingle(graphics, buttonGrass);
		drawButtonsSingle(graphics, buttonRoad);
		drawButtonsSingle(graphics, buttonWater);
		drawButtonsMap(graphics);
		drawSelectedTile(graphics);
	}
	
	private void drawButtonsSingle(Graphics graphics, Button button) {
		graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
		drawButtonsMouseInput(graphics, button);
	}
	
	private void drawButtonsMap(Graphics graphics) {
		for(Map.Entry<Button, ArrayList<Tile>> entry : map.entrySet()) {
			Button button = entry.getKey();
			BufferedImage image = entry.getValue().get(0).getSprite();
			graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
			drawButtonsMouseInput(graphics, button);
		}
	}
	
	private void drawButtonsMouseInput(Graphics graphics, Button button) {
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

	private void drawSelectedTile(Graphics graphics) {
		if (selectedTile != null) {
			graphics.setColor(Color.yellow);
			switch (selectedTile.getId()) {
			case 0, 1, 2:
				graphics.drawRect(100 + (60 * selectedTile.getId()), 560, 50, 50);
				break;
			case 3, 4, 5, 6:
				graphics.drawRect(280, 560, 50, 50);
				break;
			case 7, 8, 9, 10:
				graphics.drawRect(340, 560, 50, 50);
				break;
			case 11, 12, 13:
				graphics.drawRect(400, 560, 50, 50);
				break;
			case 14, 15, 16, 17:
				graphics.drawRect(460, 560, 50, 50);
				break;
			default:
				break;
			}
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
		for (Button button : map.keySet()) {
			button.resetButtons();
		}
	}

}
