package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.objects.Tile;
import com.vtuberrush.src.scenes.Editing;

public class ToolBar extends Bar {

	private Editing editing;
	private Button buttonMenu, buttonSave;
	private Tile selectedTile;
	
	private ArrayList<Button>tileButtons = new ArrayList<>();
	
	public ToolBar(int x, int y, int width, int height, Editing editing) {
		super(x, y, width, height);
		this.editing = editing;
		initButtons();
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 25);
		buttonSave = new Button("Save", 10, 590, 80, 25);
		
		int i = 0;
		for(Tile tile : editing.getGame().getTileManager().tiles) {
			tileButtons.add(new Button(tile.getName(), 100 + (60 * i), 560, 50, 50, i++));
		}
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(x, y, width, height);
		drawButtons(graphics);
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
		buttonSave.draw(graphics);
		drawTileButtons(graphics);
		drawSelectedTile(graphics);
	}
	
	private void drawSelectedTile(Graphics graphics) {
		if (selectedTile != null) {
			graphics.setColor(Color.yellow);
			graphics.drawRect(100 + (60 * selectedTile.getId()), 560, 50, 50);
		}
	}
	
	
	private BufferedImage getButtonImage(int id) {
		return editing.getGame().getTileManager().getSprite(id);
	}

	private void drawTileButtons(Graphics graphics) {
		for (Button button : tileButtons) {
			graphics.drawImage(getButtonImage(button.getId()), button.x, button.y, button.width, button.height, null);
			
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
	}
	
	private void saveLevel() {
		editing.saveLevel();
	}
	
	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		} else if (buttonSave.getBounds().contains(x, y)) {
			saveLevel();
		} else {
			for (Button button : tileButtons) {
				if (button.getBounds().contains(x, y)) {
					selectedTile = editing.getGame().getTileManager().getTile(button.getId());
					editing.setSelectedTile(selectedTile);
					return;
				}
			}
		}
	}

	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		for (Button button : tileButtons) {
			button.setMouseOver(false);
		}
		
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		} else if (buttonSave.getBounds().contains(x, y)) {
			buttonSave.setMouseOver(true);
		} else {
			for (Button button : tileButtons) {
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
		} else {
			for (Button button : tileButtons) {
				if(button.getBounds().contains(x, y)) {
					button.setMousePressed(true);
					return;
				}
			}
		}
		
	}
	
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	private void resetButtons() {
		buttonMenu.resetButtons();
		buttonSave.resetButtons();
	}

}
