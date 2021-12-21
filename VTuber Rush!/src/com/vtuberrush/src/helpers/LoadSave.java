package com.vtuberrush.src.helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class LoadSave {
	public static BufferedImage getSpriteAtlas() {
		BufferedImage image = null;
		InputStream imageStream = LoadSave.class.getClassLoader().getResourceAsStream("tileset.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void createLevel(String name, int[] idArray) {
		File level = new File("res/" + name + ".txt");
		if(level.exists()) {
			System.out.println("Level" + name + "already exists!");
			return;
		} else {
			try {
				level.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writeLevel(level, idArray);
		}
	}
	
	private static void writeLevel(File file, int[] idArray) {
		try {
			PrintWriter printWriter = new PrintWriter(file);
			for(Integer i : idArray) {
				printWriter.println(i);
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFromFile() {
		File level = new File("res/levelTemp.txt");
		try {
			Scanner scanner = new Scanner(level);
			while(scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	
	
}
