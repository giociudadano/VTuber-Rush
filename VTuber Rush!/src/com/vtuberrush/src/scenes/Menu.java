package com.vtuberrush.src.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.ui.Button;
import static com.vtuberrush.src.main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

	private BufferedImage image;
	private ArrayList<BufferedImage> sprites = new ArrayList<>();
	
	private Button buttonPlaying, buttonSettings, buttonQuit;
	
	public Menu(Game game) {
		super(game);
		importImage();
		loadSprites();
		initButtons();
	}

	@Override
	public void render(Graphics graphics) {
		drawButtons(graphics);
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		if (buttonPlaying.getBounds().contains(x, y)) {
			setGameState(PLAYING);
		} else if (buttonSettings.getBounds().contains(x, y)) {
			setGameState(SETTINGS);
		} else if (buttonQuit.getBounds().contains(x, y)) {
			System.exit(0);
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		buttonPlaying.setMouseOver(false);
		if (buttonPlaying.getBounds().contains(x, y)) {
			buttonPlaying.setMouseOver(true);
		} else if (buttonSettings.getBounds().contains(x, y)) {
			buttonSettings.setMouseOver(true);
		} else if (buttonQuit.getBounds().contains(x, y)) {
			buttonQuit.setMouseOver(true);
		}
	}
	
	@Override
	public void mousePressed(int x, int y) {
		if (buttonPlaying.getBounds().contains(x, y)) {
			buttonPlaying.setMousePressed(true);
		} else if (buttonSettings.getBounds().contains(x, y)) {
			buttonSettings.setMousePressed(true);
		} else if (buttonQuit.getBounds().contains(x, y)) {
			buttonQuit.setMousePressed(true);
		}
		
	}
	
	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	
	private void initButtons() {
		buttonPlaying = new Button("Play", 550, 250, 200, 60);
		buttonSettings = new Button("Settings", 550, 320, 200, 60);
		buttonQuit = new Button("Exit", 550, 400, 200, 60);
	}

	private void drawButtons(Graphics graphics) {
		buttonPlaying.draw(graphics);
		buttonSettings.draw(graphics);
		buttonQuit.draw(graphics);
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
	
	private void resetButtons() {
		buttonPlaying.resetButtons();
		buttonSettings.resetButtons();
		buttonQuit.resetButtons();
	}

	
}
