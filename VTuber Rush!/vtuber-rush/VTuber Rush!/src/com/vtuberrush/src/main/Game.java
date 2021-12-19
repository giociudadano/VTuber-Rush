package com.vtuberrush.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends JFrame{
	
	private GameScreen gameScreen;
	private BufferedImage image;
	
	public Game() {
		
		//Resource Methods
		importImage();
		
		//Window Initialization
		setIconImage(new ImageIcon("../VTuber Rush!/res/WindowIcon.png").getImage());
		setTitle("Vtuber Rush!");
		setSize(1280,720);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		gameScreen = new GameScreen(image);
		add(gameScreen);
		setVisible(true);
	}
	
	private void importImage() {
		InputStream imageStream = getClass().getResourceAsStream("/WindowIcon.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();	
	}
}
