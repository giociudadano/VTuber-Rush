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

import static com.vtuberrush.src.helpers.Constants.Tiles.WATER;
import static com.vtuberrush.src.helpers.Constants.Tiles.GRASS;

import static com.vtuberrush.src.helpers.Constants.Units.FINANA;

import static com.vtuberrush.src.main.GameStates.GAME_OVER;
import static com.vtuberrush.src.main.GameStates.setGameState;

/**
 * A <b><i>playing game scene</i></b> is a subclass of a game scene that facilitates the
 * game loop.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
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
	
	/**
	 * Creates a new <b><i>playing game scene</i></b>, attempts to load an
	 * existing game level, initializes a new instance of all game managers,
	 * and creates new buttons for upgrading and selling a displayed unit.
	 * @param game - The current instance of the game.
	 */
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
		level = LoadSave.readLevel("level");
		ArrayList<Flag> flags = LoadSave.readFlags("level");
		start = flags.get(0);
		end = flags.get(1);
	}

	/**
	 * Facilitates the spawning and pathfinding of new waves and enemies <b>(WaveManager, EnemyManager)</b>;
	 * the creation, deletion, and targeting of all units <b>(UnitManager)</b>; and the creation, deletion, and
	 * velocity of projectiles <b>(ProjectileManager)</b>.
	 * Grants additional passive gold to the player in set intervals. <p>
	 * 
	 * <i>For more information regarding the function of each game manager, please see the associated
	 * package and classes.</i>
	 */
	public void tick() {
		if (!gamePaused) {
			tickAnimation();
			waveManager.tick();
			tickGold++;
			if (tickGold % 180 == 0) {
				actionBar.addGold(1);
			}
			if (isAddWaveNext()) {
				if (isWaveNext()) {
					waveManager.delayWaveStart();
					if(isWaveDelayEnd()) {
						waveManager.initNextWave();
						enemyManager.getEnemies().clear();
					}	
				}
			} else {
				setGameState(GAME_OVER);
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
				graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 11));
				if (displayedUnit.getLevel() < 3) {
					buttonUpgrade.draw(graphics);
					graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 10));
					graphics.setColor(new Color(192, 252, 64));
					graphics.drawString("Upgrade \uFFE5" + getUpgradePrice(actionBar.getDisplayedUnit()), 158 + (100 * i++), 60);
				} else {
					buttonUpgradeMax.draw(graphics);
				}
				
				//Sell	
				graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 11));
				buttonSell.draw(graphics);
				graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 10));
				graphics.setColor(new Color(250, 95, 64));
				graphics.drawString("Sell \uFFE5" + getSellPrice(actionBar.getDisplayedUnit()), 158 + (100 * i++), 60);
				
			

			}
		}
	}

	private void addEnemy() {
		enemyManager.addEnemy(waveManager.getNextEnemy());
	}
	
	/**
	 * Creates a projectile in the location of the passed unit, which travels to the passed enemy based on the
	 * unit's existing projectile speed.
	 * @param unit - The unit dealing damage. The amount of damage is dependent on the unit source.
	 * @param enemy - The enemy receiving the damage.
	 */
	public void shootEnemy(Unit unit, Enemy enemy) {
		projectileManager.addProjectile(unit, enemy);
	}
	
	/**
	 * Deals damage to the passed enemy that is near the passed unit.<br>
	 * Called when a <b><i>Petra</i></b> unit spots an enemy within range.
	 * @param unit - The unit dealing damage. The amount of damage is dependent on the unit source.
	 * @param enemy - The enemy receiving the damage.
	 */
	public void blastEnemy(Unit unit, Enemy enemy) {
		enemy.takeDamage(unit.getDamage());
	}
	
	/**
	 * Adds gold to the user depending on the passed enemy type. <br>
	 * Called when an enemy was killed.
	 * @param type - The enemy type that was killed.
	 */
	public void addGold(int type) {
		actionBar.addGold(Enemies.getReward(type));
	}
	
	private void subtractGold(int type) {
		actionBar.subtractGold(Units.getCost(type));
	}
	
	/**
	 * Reduces the amount of lives of the player by the passed amount. <br>
	 * @param amount - The amount of lives being subtracted. Dependent on the enemy type.
	 */
	public void subtractLives(int amount) {
		actionBar.subtractLives(amount);
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
	
	private boolean isPurchasable(Unit unit) {
		return actionBar.isPurchasable(unit);
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
	
	/**
	 * Resets all game managers, selections, pauses, gold, and mouse coordinates.
	 */
	public void resetGame() {
		actionBar.resetGame();
		enemyManager.resetGame();
		unitManager.resetGame();
		waveManager.resetGame();
		projectileManager.resetGame();
		mouseX = 0;
		mouseY = 0;
		selectedUnit = null;
		tickGold = 0;
		gamePaused = false;
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

	/**
	 * Dynamically checks if the user has pressed <b>ESCAPE</b> which unselects and
	 * undisplays the currently selected and displayed unit.
	 * @param e - The event indicating a key was pressed.
	 */
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
	
	/**
	 * Returns the current instance of the unit manager.
	 * @return {@link #unitManager}
	 */
	public UnitManager getUnitManager() {
		return unitManager;
	}
	
	/**
	 * Returns the current instance of the wave manager.
	 * @return {@link #waveManager}
	 */
	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	/**
	 * Returns the current instance of the enemy manager.
	 * @return {@link #enemyManager}
	 */
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	/**
	 * Returns the currently selected unit.
	 * @return {@link #selectedUnit}
	 */
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
	
	/**
	 * Returns the type of the tile in the passed coordinates.
	 * @param x - Defines the horizontal position of the unit from the left of the window in pixels.
	 * @param y - Defines the vertical position of the unit from the top of the window in pixels.
	 * @return <b>type</b> - The type of the tile based on the passed coordinates.
	 */
	public int getTileType(int x, int y) {
		x = x / 32;
		y = y / 32;
		if (x < 0 || y < 0 || x > 39 || y > 17) {
			return 0;
		}
		int id = level[y][x];
		return game.getTileManager().getTile(id).getTileType();
	}
	
	/**
	 * Returns <b><i>true</i></b> if the game is currently paused.
	 * @return {@link #gamePaused}
	 */
	public boolean isGamePaused() {
		return gamePaused;
	}

	/**
	 * Sets the playing field equal to the passed two-dimensional level format which was
	 * converted using <b>MathFunctions</b> from reading a level file in <b>LoadSave</b>.
	 * @param level - The converted level file format.
	 */
	public void setLevel(int[][] level) {
		this.level = level;
	}
	
	/**
	 * Modifies the currently selected unit to be the unit either clicked from the playing field
	 * or clicked from the associated unit button in the action bar.
	 * @param selectedUnit - The new unit that is selected.
	 */
	public void setSelectedUnit(Unit selectedUnit) {
		this.selectedUnit = selectedUnit;
	}
	
	/**
	 * Pauses or unpauses the game.
	 * @param gamePaused - Defines whether the game is currently paused.
	 */
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
}
