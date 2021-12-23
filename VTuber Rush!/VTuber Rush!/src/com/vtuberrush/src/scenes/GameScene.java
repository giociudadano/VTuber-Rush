package com.vtuberrush.src.scenes;

import java.awt.image.BufferedImage;

import com.vtuberrush.src.main.Game;

public class GameScene {
	protected Game game;
	protected int frameDelay, frame;
	
	
	public GameScene(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	protected void tickAnimation() {
		frameDelay = (frameDelay + 1) % 20;
		if (frameDelay == 0) {
			frame = (frame + 1) % 4;
		}
	}
	
	protected boolean isAnimated(int id) {
		return game.getTileManager().isAnimated(id);
	}
	
	protected BufferedImage getSprite(int id) {
		return game.getTileManager().getSprite(id);
	}
	
	protected BufferedImage getSprite(int id, int frame) {
		return game.getTileManager().getSpriteAnimated(id, frame);
	}
}
