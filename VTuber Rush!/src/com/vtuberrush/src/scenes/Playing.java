package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.helpers.Constants.Enemies;
import com.vtuberrush.src.helpers.Constants.Units;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.managers.EnemyManager;
import com.vtuberrush.src.managers.ProjectileManager;
import com.vtuberrush.src.managers.UnitManager;
import com.vtuberrush.src.managers.WaveManager;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.ui.ActionBar;
import com.vtuberrush.src.ui.Button;

import static com.vtuberrush.src.helpers.Constants.Tiles.*;
import static com.vtuberrush.src.helpers.Constants.Units.FINANA;

public class Playing extends GameScene implements SceneMethods {

	private int[][] level;
	private ActionBar actionBar;
	private EnemyManager enemyManager;
	private UnitManager unitManager;
	private WaveManager waveManager;
	private ProjectileManager projectileManager;
	private Unit selectedUnit;
	private int mouseX, mouseY;
	private int tickGold;
	private boolean gamePaused = false;
	
	private Flag start, end;
	private Button buttonUpgrade, buttonSell, buttonUpgradeMax;
	
	public Playing(Game game) {
		super(game);
		loadLevelDefault();
		actionBar = new ActionBar(0,550,1280,200,this);
		enemyManager = new EnemyManager(this, start, end);
		unitManager = new UnitManager(this);
		waveManager = new WaveManager(this);
		projectileManager = new ProjectileManager(this);
		
		buttonUpgrade = new Button("Upgrade", 420, 12, 80, 30);
		buttonUpgradeMax = new Button("Max Level", 420, 12, 80, 30);
		buttonSell = new Button("Sell", 420, 44, 80, 30);
	}

	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
		ArrayList<Flag> flags = LoadSave.readFlags("new_level");
		start = flags.get(0);
		end = flags.get(1);
	}

	public void tick() {
		if (!gamePaused) {
			tickAnimation();
			waveManager.tick();
			tickGold++;
			if (tickGold % 180 == 0) {
				actionBar.addGold(1);
			}
			if (isWaveNext()) {
				if (isAddWaveNext()) {
					waveManager.delayWaveStart();
					if(isWaveDelayEnd()) {
						waveManager.initNextWave();
						enemyManager.getEnemies().clear();
					}
				}
			}
			if(isAddEnemy()) {
				addEnemy();
			}
			enemyManager.tick();
			unitManager.tick();
			projectileManager.tick();
		}
	}
	
	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		drawHighlights(graphics);
		enemyManager.draw(graphics);
		unitManager.draw(graphics);
		projectileManager.draw(graphics);
		drawSelectedUnit(graphics);
		actionBar.draw(graphics);
		drawUnitInfo(graphics);
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
	
	private void drawSelectedUnit(Graphics graphics) {
		if (getSelectedUnit() != null) {
			graphics.drawImage(unitManager.getUnitSprites()[getSelectedUnit().getUnitType()], mouseX, mouseY-32, null);
		}
	}
	
	private void drawHighlights(Graphics graphics) {
		graphics.setColor(Color.white);
		graphics.drawRect(mouseX, mouseY, 32, 32);
		Unit displayedUnit = actionBar.getDisplayedUnit();
		if (displayedUnit != null) {
			if (displayedUnit.getId() != -1) {
				//Selected Unit
				graphics.setColor(new Color(192, 252, 64));
				graphics.drawRect(displayedUnit.getX(), displayedUnit.getY(), 32, 32);
				
				//Range of Selected Unit
				graphics.setColor(Color.white);
				graphics.drawOval(displayedUnit.getX()+16 - (int)(displayedUnit.getRange()/2),
					displayedUnit.getY()+16 - (int)(displayedUnit.getRange()/2),
					(int)(displayedUnit.getRange()), (int)(displayedUnit.getRange()));
			}
		}	
	}
	
	private void drawUnitInfo(Graphics graphics) {
		Unit displayedUnit = actionBar.getDisplayedUnit();
		if (displayedUnit != null) {
			if (displayedUnit.getId() != -1) {
				graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 10));
				graphics.setColor(new Color(211, 186, 145));
				graphics.drawString("LV " + actionBar.getDisplayedUnit().getLevel(), 108, 60);
				
				int i = 0;
				if (displayedUnit.getLevel() < 3) {
					buttonUpgrade.draw(graphics);
					graphics.setColor(new Color(192, 252, 64));
					graphics.drawString("Upgrade \uFFE5" + getUpgradePrice(actionBar.getDisplayedUnit()), 158 + (100 * i++), 60);
				} else {
					buttonUpgradeMax.draw(graphics);
				}
				
				//Sell	
				buttonSell.draw(graphics);
				graphics.setColor(new Color(250, 95, 64));
				graphics.drawString("Sell \uFFE5" + getSellPrice(actionBar.getDisplayedUnit()), 158 + (100 * i++), 60);
				
			

			}
		}
	}

	private void addEnemy() {
		enemyManager.addEnemy(waveManager.getNextEnemy());
	}
	
	public void shootEnemy(Unit unit, Enemy enemy) {
		projectileManager.addProjectile(unit, enemy);
	}
	
	public void addGold(int type) {
		actionBar.addGold(Enemies.getReward(type));
	}
	
	private void subtractGold(int type) {
		actionBar.subtractGold(Units.getCost(type));
	}
	
	private void sellUnit(Unit unit) {
		unitManager.removeUnit(unit);
		actionBar.addGold(getSellPrice(unit));
	}
	
	private void upgradeUnit(Unit unit) {
		unitManager.upgradeUnit(unit);
		actionBar.subtractGold(getUpgradePrice(unit));
	}
	
	private void cancelSelection() {
		setSelectedUnit(null);
		actionBar.setDisplayedUnit(null);
	}
	
	private boolean isAddEnemy() {
		if(isAddWaveNext()) {
			if (waveManager.isAddEnemy()) {
				if (waveManager.isAddEnemyNext()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isWaveNext() {
		if(waveManager.isAddEnemyNext()) {
			return false;
		}
		for (Enemy enemy : enemyManager.getEnemies()) {
			if(enemy.isAlive()) {
				return false;
			}
		}
		return true;	
	}
	
	private boolean isAddWaveNext() {
		return waveManager.isAddWaveNext();
	}
	
	private boolean isWaveDelayEnd() {
		return waveManager.isWaveDelayEnd();
	}
	
	
	private boolean isPlacable(int type, int x, int y) {
		int id = level[y/32][x/32];
		int tileType = game.getTileManager().getTile(id).getTileType();
		if (type == FINANA) {
			return tileType == WATER;
		} else {
			return tileType == GRASS;
		}
	}
	
	private void checkDisplayedUnit(Unit displayedUnit, int x, int y) {
		if (displayedUnit != null) {
			if (displayedUnit.getId() != -1) {
				if(buttonSell.getBounds().contains(x, y)) {
					sellUnit(displayedUnit);
				} else if (buttonUpgrade.getBounds().contains(x,y)) {
					if (displayedUnit.getLevel() < 3) {
						if (actionBar.getGold() >= getUpgradePrice(displayedUnit)) {
							upgradeUnit(displayedUnit);
						}
					}
				}
			}
		}
	}
	
	private void checkSelectedUnit(int x, int y) {
		if (getSelectedUnit() != null) {
			if (isPlacable(getSelectedUnit().getUnitType(), mouseX, mouseY)) {
				if (getUnitAt(mouseX, mouseY) == null) {
					if (isPurchasable(selectedUnit)) {
						unitManager.addUnit(getSelectedUnit(), mouseX, mouseY);
						subtractGold(selectedUnit.getUnitType());
						cancelSelection();
					}
				}
			}
		} else {
			Unit unit = getUnitAt(mouseX, mouseY);
			actionBar.setDisplayedUnit(unit);
		}
	}
	
	//Mouse Methods
	@Override
	public void mouseClicked(int x, int y) {
		if (y > 540) {
			actionBar.mouseClicked(x, y);
		} else {
			Unit displayedUnit = actionBar.getDisplayedUnit();
			checkDisplayedUnit(displayedUnit, x, y);
			checkSelectedUnit(x, y);
		}
	}

	private boolean isPurchasable(Unit unit) {
		return actionBar.isPurchasable(unit);
	}

	@Override
	public void mouseMoved(int x, int y) {
		buttonSell.setMouseOver(false);
		buttonUpgrade.setMouseOver(false);
		if (y > 540) {
			actionBar.mouseMoved(x, y);
		} else {
			if (actionBar.getDisplayedUnit() != null) {
				if(buttonSell.getBounds().contains(x, y)) {
					buttonSell.setMouseOver(true);
				} else if (buttonUpgrade.getBounds().contains(x, y)) {
					buttonUpgrade.setMouseOver(true);
				}
			}
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelSelection();
		}
	}
	
	@Override
	public void mousePressed(int x, int y) {
		if (y > 540) {
			actionBar.mousePressed(x, y);
		} else {
			if (actionBar.getDisplayedUnit() != null) {
				if(buttonSell.getBounds().contains(x, y)) {
					buttonSell.setMousePressed(true);
				} else if (buttonUpgrade.getBounds().contains(x, y)) {
					buttonUpgrade.setMousePressed(true);
				}
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
		buttonSell.resetButtons();
		buttonUpgrade.resetButtons();
	}
	
	@Override
	public void mouseDragged(int x, int y) {
	}

	//Getters and Setters
	private Unit getUnitAt(int x, int y) {
		return unitManager.getUnitAt(x, y);
	}
	
	public UnitManager getUnitManager() {
		return unitManager;
	}
	
	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public Unit getSelectedUnit() {
		return selectedUnit;
	}
	
	private int getSellPrice(Unit unit) {	
		int upgradeCost = (unit.getLevel() - 1) * getUpgradePrice(unit);
		return (int) ((Units.getCost(unit.getUnitType()) + upgradeCost)/2);
	}
	
	private int getUpgradePrice(Unit unit) {
		return (int) (Units.getCost(unit.getUnitType()) * 0.75);
	}
	
	public int getTileType(int x, int y) {
		x = x / 32;
		y = y / 32;
		if (x < 0 || y < 0 || x > 39 || y > 17) {
			return 0;
		}
		int id = level[y][x];
		return game.getTileManager().getTile(id).getTileType();
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setLevel(int[][] level) {
		this.level = level;
	}
	
	public void setSelectedUnit(Unit selectedUnit) {
		this.selectedUnit = selectedUnit;
	}
	
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
}
