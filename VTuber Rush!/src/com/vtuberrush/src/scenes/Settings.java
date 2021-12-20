package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;

import com.vtuberrush.src.main.Game;

public class Settings extends GameScene implements SceneMethods {

	public Settings(Game game) {
		super(game);
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.fillRect(0, 0, 1280, 720);
	}

}
