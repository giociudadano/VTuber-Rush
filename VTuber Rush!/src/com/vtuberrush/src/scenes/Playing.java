package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.managers.EnemyManager;
import com.vtuberrush.src.managers.ProjectileManager;
import com.vtuberrush.src.managers.UnitManager;
import com.vtuberrush.src.managers.WaveManager;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.objects.Unit;
import com.vtuberrush.src.ui.ActionBar;

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
	
	private Flag start, end;

	
	public Playing(Game game) {
		super(game);
		loadLevelDefault();
		actionBar = new ActionBar(0,550,1280,200,this);
		enemyManager = new EnemyManager(this, start, end);
		unitManager = new UnitManager(this);
		waveManager = new WaveManager(this);
		projectileManager = new ProjectileManager(this);
	}

	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
		ArrayList<Flag> flags = LoadSave.readFlags("new_level");
		start = flags.get(0);
		end = flags.get(1);
	}

	public void tick() {
		tickAnimation();
		waveManager.tick();
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
	
	private void addEnemy() {
		enemyManager.addEnemy(waveManager.getNextEnemy());
	}
	
	public void shootEnemy(Unit unit, Enemy enemy) {
		projectileManager.addProjectile(unit, enemy);
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
	
	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		drawHighlights(graphics);
		enemyManager.draw(graphics);
		unitManager.draw(graphics);
		projectileManager.draw(graphics);
		actionBar.draw(graphics);
		drawSelectedUnit(graphics);
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
	
	public void setSelectedUnit(Unit selectedUnit) {
		this.selectedUnit = selectedUnit;
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
	
	//Mouse Methods
	@Override
	public void mouseClicked(int x, int y) {
		if (y > 540) {
			actionBar.mouseClicked(x, y);
		} else {
			if (getSelectedUnit() != null) {
				if (isPlacable(getSelectedUnit().getUnitType(), mouseX, mouseY)) {
					if (getUnitAt(mouseX, mouseY) == null) {
						unitManager.addUnit(getSelectedUnit(), mouseX, mouseY);
						selectedUnit = null;
					}
				}
			} else {
				Unit unit = getUnitAt(mouseX, mouseY);
				actionBar.setDisplayedUnit(unit);
			}
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		if (y > 540) {
			actionBar.mouseMoved(x, y);
		} else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			selectedUnit = null;
		}
	}
	
	@Override
	public void mousePressed(int x, int y) {
		if (y > 540) {
			actionBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
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
	
	public void setLevel(int[][] level) {
		this.level = level;
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

	public Unit getSelectedUnit() {
		return selectedUnit;
	}


}
