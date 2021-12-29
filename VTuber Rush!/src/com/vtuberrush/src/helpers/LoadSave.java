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

import com.vtuberrush.src.objects.Flag;

public class LoadSave {
	public static String homePath = System.getProperty("user.home");
	public static String saveFolder = "VTuber Rush!";
	public static String level = "level.txt";
	public static String filePath = homePath + File.separator + saveFolder + File.separator + level;
	private static File levelFile = new File(filePath);
	
	public static void createFolder() {
		File folder = new File(homePath + File.separator + saveFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}
	
	public static BufferedImage getGameIcon() {
		BufferedImage image = null;
		InputStream imageStream = LoadSave.class.getClassLoader().getResourceAsStream("icon.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage getBackground() {
		BufferedImage image = null;
		InputStream imageStream = LoadSave.class.getClassLoader().getResourceAsStream("background.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
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
	
	public static BufferedImage getUnitIconsAtlas() {
		BufferedImage image = null;
		InputStream imageStream = LoadSave.class.getClassLoader().getResourceAsStream("uniticons.png");
		try {
			image = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void createLevel(int[] idArray) {
		if(levelFile.exists()) {
			System.out.println("Level " + levelFile + " already exists!");
			return;
		} else {
			try {
				levelFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writeLevel(idArray, new Flag(0, 0), new Flag(0, 0));
		}
	}
	
	private static void writeLevel(int[] idArray, Flag start, Flag end) {
		try {
			PrintWriter printWriter = new PrintWriter(levelFile);
			for(Integer i : idArray) {
				printWriter.println(i);
			}
			
			printWriter.println(start.getX());
			printWriter.println(start.getY());
			printWriter.println(end.getX());
			printWriter.println(end.getY());
			
			printWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static int[][] readLevel(String name) {
		if(levelFile.exists()) {
			ArrayList<Integer> array = readFile(levelFile);
			return MathFunctions.array1Dto2D(array, 40, 18);
		} else {
			System.out.println("Level " + name + " does not exist!");
			return null;
		}
		
	}
	
	public static ArrayList<Flag> readFlags(String name) {
		if(levelFile.exists()) {
			ArrayList<Integer> array = readFile(levelFile);
			ArrayList<Flag> flags = new ArrayList<>();
			flags.add(new Flag(array.get(720), array.get(721))); //Reads starting coordinates
			flags.add(new Flag(array.get(722), array.get(723))); //Reads ending coordinates
			return flags;
		} else {
			System.out.println("Level " + name + " does not exist!");
			return null;
		}
	}
	

	private static ArrayList<Integer> readFile(File file) {
		ArrayList<Integer> array = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				array.add(Integer.parseInt(scanner.nextLine()));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public static void saveLevel(String name, int[][] idArray, Flag start, Flag end) {
		if(levelFile.exists()) {
			writeLevel(MathFunctions.array2Dto1D(idArray), start, end);
		} else {
			System.out.println("Level " + name + " does not exist!");
			return;
		}
	}
}
