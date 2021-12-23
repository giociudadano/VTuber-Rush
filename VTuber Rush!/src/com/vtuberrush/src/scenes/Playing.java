package com.vtuberrush.src.scenes;

import java.awt.Graphics;
import java.util.ArrayList;

import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.managers.EnemyManager;
import com.vtuberrush.src.managers.UnitManager;
import com.vtuberrush.src.objects.Flag;
import com.vtuberrush.src.ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

	private int[][] level;
	private EnemyManager enemyManager;
	private ActionBar bottomBar;
	
	private UnitManager unitManager;
	private int mouseX, mouseY;
	
	private Flag start, end;
	
	public Playing(Game game) {
		super(game);
		loadLevelDefault();
		bottomBar = new ActionBar(0,550,1280,200,this);
		enemyManager = new EnemyManager(this, start, end);
		unitManager = new UnitManager(this);
	}

	private void loadLevelDefault() {
		level = LoadSave.readLevel("new_level");
		ArrayList<Flag> flags = LoadSave.readFlags("new_level");
		start = flags.get(0);
		end = flags.get(1);
	}

	public void tick() {
		tickAnimation();
		enemyManager.tick();
		unitManager.tick();
	}
	
	@Override
	public void render(Graphics graphics) {
		drawLevel(graphics);
		bottomBar.draw(graphics);
		enemyManager.draw(graphics);
		unitManager.draw(graphics);
	}
	
	private void drawLevel(Graphics graphics) {
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

	public int getTileType(int x, int y) {
		x = x / 32;
		y = y / 32;
		if (x < 0 || y < 0 || x > 39 || y > 17) {
			return 0;
		}
		int id = level[y][x];
		return game.getTileManager().getTile(id).getTileType();
	}
	
	//Mouse Methods
	@Override
	public void mouseClicked(int x, int y) {
		if (y > 550) {
			bottomBar.mouseClicked(x, y);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y > 550) {
			bottomBar.mouseMoved(x, y);
		} else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y > 550) {
			bottomBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		bottomBar.mouseReleased(x, y);
	}
	
	@Override
	public void mouseDragged(int x, int y) {
	}

	//Setters
	public void setLevel(int[][] level) {
		this.level = level;
	}

	
	public UnitManager getUnitManager() {
		return unitManager;
	}
}
