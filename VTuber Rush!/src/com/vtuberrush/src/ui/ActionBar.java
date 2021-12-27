package com.vtuberrush.src.ui;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import com.vtuberrush.src.helpers.Constants.Units;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.scenes.Playing;

public class ActionBar extends Bar{
	private Playing playing;
	private Button buttonMenu;
	private Unit selectedUnit, displayedUnit;
	
	private Button[] unitButtons;
	private DecimalFormat formatter;
	
	private int gold = 150;
	
	public ActionBar(int x, int y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		initButtons();
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(36, 48, 61, 200));
		graphics.fillRect(x, y, width, height);
		formatter = new DecimalFormat("0.0");
		drawButtons(graphics);
		drawSelectedUnit(graphics);
		drawDisplayedUnit(graphics);
		drawStats(graphics);
	}

	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 560, 80, 30);
		unitButtons = new Button[3];
		for(int i = 0; i < unitButtons.length; i++) {
			unitButtons[i] = new Button("", 100 + (122 * i), 560, 112, 136, i);
		}
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
		for (Button button : unitButtons) {
			graphics.drawImage(playing.getUnitManager().getUnitIcons()[button.getId()], button.x, button.y, button.width, button.height, null);
			drawButtonsFeedback(graphics, button);
		}
	}
	
	private void drawSelectedUnit(Graphics graphics) {
		if (playing.getSelectedUnit() != null) {
			Graphics2D graphics2d = (Graphics2D) graphics;
			graphics.setColor(new Color(192, 252, 64));
			graphics2d.drawRoundRect(100 + (122 * selectedUnit.getUnitType()), 560, 112, 136, 10, 13);
			graphics2d.drawRoundRect(101 + (122 * selectedUnit.getUnitType()), 561, 110, 134, 6, 10);
		}
	}
	
	public void drawButtonsFeedback(Graphics graphics, Button button) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(button.isMouseOver()) {
			graphics.setColor(Color.white);
			graphics2d.drawRoundRect(button.x, button.y, button.width, button.height,10,13);
			graphics2d.drawRoundRect(button.x+1, button.y+1, button.width-2, button.height-2,6,10);
		}
		
		if (button.isMousePressed()) {
			graphics.setColor(new Color(255,255,255,50));
			graphics2d.fillRoundRect(button.x, button.y, button.width, button.height, 10, 13);
		}	

	}
	
	private void drawDisplayedUnit(Graphics graphics) {
		if (displayedUnit != null) {
			int type = displayedUnit.getUnitType();
			
		//Frame
			graphics.setColor(new Color(72, 79, 95));
			graphics.fillRect(16, 12, 400, 30);
			graphics.setColor(new Color(36, 48, 61, 200));
			graphics.fillRect(16, 42, 400, 120);
			graphics.setColor(new Color(212, 205, 197, 30));
			graphics.drawRect(18, 14, 396, 26);
			
		//Image
			graphics.drawImage(playing.getUnitManager().getUnitSprites()[type], 32, 48, 64, 96, null);
			
		//Text
			
			//Unit Name
			graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 15));
			graphics.setColor(new Color(211, 186, 145));
			graphics.drawString(Units.getName(type), 32, 32);
			
			//Unit Cost
			if (displayedUnit.getId() == -1) {
				graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 11));
				if (isPurchasable(displayedUnit)) {
					graphics.setColor(new Color(211, 186, 145));
				} else {
					graphics.setColor(new Color(250, 95, 64));
				}
					graphics.drawString("Buy \uFFE5" + Units.getCost(type), 345, 32);
			}
			
			//Unit Info
			graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 12));
			graphics.setColor(new Color(236, 230, 218));
			int lines = 0;
			String infoText = new String(Units.getInfo(type));
			for(String line : infoText.split("\n")) {
				graphics.drawString(line, 108, 64 + (13 * lines++));
			}
			lines = 0;
			
			//Unit Flavor
			graphics.setFont(new Font("MiHoYo_SDK_Web", Font.ITALIC, 11));
			graphics.setColor(new Color(236, 230, 218, 100));
			String flavorText = new String(Units.getFlavor(type));
			for(String line : flavorText.split("\n")) {
				graphics.drawString(line, 108, 122 + (12 * lines++));
			}
		}
	}	
	
	private void drawStats(Graphics graphics) {
		graphics.setColor(new Color(236, 230, 218));
		graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 12));

		//Wave Number
		int currentWave = playing.getWaveManager().getWaveIndex() + 1;
		int totalWaves = playing.getWaveManager().getWaves().size();
		graphics.drawString("Wave: " + currentWave + " / " + totalWaves, 1160, 570);
		
		//Wave Timer and Enemy Number
		if (playing.getWaveManager().isTimerStart()) {
			float timeLeft = playing.getWaveManager().getTimeLeft();
			String timeLeftString = formatter.format(timeLeft);	
			graphics.drawString("Time Left: " + timeLeftString, 1160, 585);				
		} else {
			int remainingEnemies = playing.getEnemyManager().getRemainingEnemies();
			graphics.drawString("Enemies Left: " + remainingEnemies, 1160, 585);
		}
		
		//Gold
		graphics.drawString("Gold: \uFFE5" + gold, 1160, 600);
	}
	
	public void addGold(int amount) {
		this.gold += amount;
	}
	
	public void subtractGold(int amount) {
		this.gold -= amount;
	}
	
	public boolean isPurchasable(Unit unit) {
		return gold >= Units.getCost(unit.getUnitType());
	}

	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		} else {
			for (Button button : unitButtons) {
				if (button.getBounds().contains(x, y)) {
					selectedUnit = new Unit(0, 0, -1, button.getId());
					playing.setSelectedUnit(selectedUnit);
					setDisplayedUnit(selectedUnit);
					return;
				}
			}
		}
	}

	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		for (Button button : unitButtons) {
			button.setMouseOver(false);
		}
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		} else {
			for (Button button : unitButtons) {
				if (button.getBounds().contains(x, y)) {
					button.setMouseOver(true);
				}
			}
		}
		
	}

	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		} else {
			for (Button button : unitButtons) {
				if (button.getBounds().contains(x, y)) {
					button.setMousePressed(true);
				}
			}
		}
	}
	

	public void mouseReleased(int x, int y) {
		buttonMenu.resetButtons();
		for (Button button : unitButtons) {
			button.resetButtons();
		}
	}
	
	public void setDisplayedUnit(Unit unit) {
		displayedUnit = unit;
	}
	
	public Unit getDisplayedUnit() {
		return displayedUnit;
	}
}
