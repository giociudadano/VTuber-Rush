package com.vtuberrush.src.helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	
	public static int[][] readLevel(String name) {
		File level = new File("res/" + name + ".txt");
		if(level.exists()) {
			ArrayList<Integer> array = ReadFile(level);
			return ArrayBuilder.array1Dto2D(array,20, 9);
		} else {
			System.out.println("Level" + name + "does not exist!");
			return null;
		}
		
	}

	private static ArrayList<Integer> ReadFile(File file) {
		ArrayList<Integer> array = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				array.add(Integer.parseInt(scanner.nextLine()));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	public static void saveLevel(String name, int[][] idArray) {
		File level = new File("res/" + name + ".txt");
		if(level.exists()) {
			writeLevel(level, ArrayBuilder.array2Dto1D(idArray));
		} else {
			System.out.println("Level" + name + "does not exist!");
			return;
		}
	}
}
