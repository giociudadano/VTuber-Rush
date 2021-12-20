package com.vtuberrush.src.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.vtuberrush.src.main.Game;

public class Menu extends GameScene implements SceneMethods {

	private BufferedImage image;
	private ArrayList<BufferedImage> sprites = new ArrayList<>();
	private Random random;
	
	public Menu(Game game) {
		super(game);
		random = new Random();
		importImage();
		loadSprites();
	}

	@Override
	public void render(Graphics graphics) {
		for(int y = 0; y < 24; y++) {
			for(int x = 0; x < 40; x++) {
				graphics.drawImage(sprites.get(getRandomInt()), x*32, y*32, null);
			}
		}
	}

	private void importImage() {
		InputStream imageStream = getClass().getResourceAsStream("/PomuRainpuff.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadSprites() {
		for(int y = 0; y < 10; y++) {
			for(int x = 0; x < 10; x++) {
				sprites.add(image.getSubimage(x*32, y*32, 32, 32));
			}
		}
	}
	
	private int getRandomInt() {
		return random.nextInt(100);
	}
}
