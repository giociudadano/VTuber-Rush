package com.vtuberrush.src.helpers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {
	public static BufferedImage getSpriteAtlas() {
		BufferedImage image = null;
		InputStream imageStream = LoadSave.class.getClassLoader().getResourceAsStream("Tileset.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
