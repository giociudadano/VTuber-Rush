package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;

import com.vtuberrush.src.main.Game;

public class Playing extends GameScene implements SceneMethods {

	public Playing(Game game) {
		super(game);
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, 1280, 720);
	}

}
