package com.vtuberrush.src.helpers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageBuilder {
	public static BufferedImage rotate(BufferedImage image, int rotation) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		Graphics2D graphics2d = newImage.createGraphics();
		
		graphics2d.rotate(Math.toRadians(rotation), width/2, height/2);
		graphics2d.drawImage(image, 0, 0, null);
		graphics2d.dispose();
		
		return newImage;
	}
	
	public static BufferedImage stack(BufferedImage[] images) {
		int width = images[0].getWidth();
		int height = images[0].getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
		Graphics2D graphics2d = newImage.createGraphics();
		
		for(BufferedImage image: images) {
			graphics2d.drawImage(image, 0, 0, null);
		}
		graphics2d.dispose();
		
		return newImage;
	}
	
	public static BufferedImage stackRotate(BufferedImage[] images, int rotation, int index) {
		int width = images[0].getWidth();
		int height = images[0].getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
		Graphics2D graphics2d = newImage.createGraphics();
		
		for(int i = 0; i < images.length; i++) {
			if(index == i) {
				graphics2d.rotate(Math.toRadians(rotation), width/2, height/2);
			}
			graphics2d.drawImage(images[i], 0, 0, null);
			if(index == i) {
				graphics2d.rotate(Math.toRadians(rotation * -1), width/2, height/2);
			}
			
		}
		
		graphics2d.dispose();
		
		return newImage;
	}
}
