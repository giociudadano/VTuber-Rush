package com.vtuberrush.src.scenes;

import com.vtuberrush.src.main.Game;

public class GameScene {
	protected Game game;
	public GameScene(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
}
