package com.vtuberrush.src.managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.vtuberrush.src.enemies.Enemy;
import com.vtuberrush.src.helpers.LoadSave;
import com.vtuberrush.src.scenes.Playing;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[] enemySprites;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		enemySprites = new BufferedImage[4];
		addEnemy(3, 9);
		loadEnemies();
	}
	
	public void loadEnemies() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		enemySprites[0] = atlas.getSubimage(0, 128, 32, 32);
		enemySprites[1] = atlas.getSubimage(32, 128, 32, 32);
		enemySprites[2] = atlas.getSubimage(64, 128, 32, 32);
		enemySprites[3] = atlas.getSubimage(96, 128, 32, 32);
	}
	
	public void tick() {
		for (Enemy enemy : enemies) {
			enemy.move(0.5f, 0.5f);
		}	
	}
	
	public void addEnemy(int x, int y) {
		enemies.add(new Enemy(32 * x, 32 * y, 0, 0));
	}
	
	public void draw(Graphics graphics) {
		for (Enemy enemy : enemies) {
			drawEnemy(graphics, enemy);
		}	
	}
	
	private void drawEnemy(Graphics graphics, Enemy enemy) {
		graphics.drawImage(enemySprites[0], (int)enemy.getX(), (int)enemy.getY(), null);
	}
}
