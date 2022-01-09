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

/**
 * This class is responsible for handling external game resources such as reading and writing level files
 * to the system, and loading in images for the game's icon, background and asset sprites.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class LoadSave {
	
	/**
	 * Defines the dynamic home directory of the user.
	 */
	public static String homePath = System.getProperty("user.home");
	
	/**
	 * Defines the name of the parent folder where the level file is saved.
	 */
	public static String saveFolder = "VTuber Rush!";
	
	/**
	 * Defines the name of the level file.
	 */
	public static String level = "level.txt";
	
	/**
	 * Defines the dynamic file path of the level file by combining {@link #homePath}, {@link #saveFolder}, and {@link #level}.
	 */
	public static String filePath = homePath + File.separator + saveFolder + File.separator + level;
	
	/**
	 * Creates a file object that links to the level file found in the dynamic file path. Used for reading and writing to the file.
	 * @see #filePath
	 */
	private static File levelFile = new File(filePath);
	
	/**
	 * Creates a new folder in the home directory of the user.<br>
	 * This method is called when creating a new level file.
	 */
	public static void createFolder() {
		File folder = new File(homePath + File.separator + saveFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}
	
	/**
	 * Loads in the game icon from resources.<br>
	 * This method is called on startup when the game first creates a window.
	 */
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
	
	/**
	 * Loads in the background from resources which is used in the <b>MENU</b> and <b>GAME OVER</b> scenes.
	 */
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
	
	/**
	 * Loads in a sprite atlas from resources which is used as the sprites for all tiles, projectiles,
	 * units, and enemies.
	 */
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
	
	/**
	 * Loads in a unit icons atlas which are used as clickable buttons in the Action Bar when
	 * selecting and placing a unit.
	 */
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
	
	/**
	 * Creates a new default level made of grass tiles, provided that the level file does
	 * not already exist.
	 * 
	 * @param idArray - Contains information related to which tiles are placed in the level.
	 * Defaults to a grass field if a level file does not already exist.
	 */
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
	
	/**
	 * Converts the changes made in the built-in level editor into a written level format.
	 * 
	 * @param name - Defines the name of the level.
	 * @param idArray - Defines the type of tile and its location in a two-dimensional grid.
	 * @param start - Defines the location where enemies spawn.
	 * @param end - Defines the location where enemies are purged and lives are lost.
	 */
	public static void saveLevel(String name, int[][] idArray, Flag start, Flag end) {
		if(levelFile.exists()) {
			writeLevel(MathFunctions.array2Dto1D(idArray), start, end);
		} else {
			System.out.println("Level " + name + " does not exist!");
			return;
		}
	}
	
	/**
	 * Converts the written level format from {@link #saveLevel(String, int[][], Flag, Flag)} into a file.
	 * 
	 * @param idArray - Contains information related to which tiles are placed in the level.
	 * @param start - Defines the location where enemies spawn.
	 * @param end - Defines the location where enemies are purged and lives are lost.
	 */
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
	
	/**
	 * Converts the read file from {@link #readFile(File)} into a readable level format.
	 * 
	 * @param name - Defines the name of the level.
	 */
	public static int[][] readLevel(String name) {
		if(levelFile.exists()) {
			ArrayList<Integer> array = readFile(levelFile);
			return MathFunctions.array1Dto2D(array, 40, 18);
		} else {
			System.out.println("Level " + name + " does not exist!");
			return null;
		}
		
	}
	
	/**
	 * Reads the location of the start and end flags of the level.
	 * @param name - Defines the name of the level.
	 */
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
	
	/**
	 * Reads the file from memory and converts the lines into a one-dimensional array.
	 * @param file - Defines the file object linked to the level file.
	 */
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
	
}
