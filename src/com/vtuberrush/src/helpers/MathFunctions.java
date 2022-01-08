package com.vtuberrush.src.helpers;

import java.util.ArrayList;

/**
 * This class is responsible for handling mathematical operations such as calculating the distance
 * of two objects for range calculations and for converting the dimensions of arrays when reading and writing level files.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class MathFunctions {
	
	/**
	 * Converts a one-dimensional array to a two-dimensional array. <br>
	 * Used when converting a level file into a level format for rendering the positions of tiles in two dimensions.
	 * @param array - The one-dimensional array being converted into a two-dimensional array.
	 * @param xSize - The horizontal size of the new two-dimensional array.
	 * @param ySize - The vertical size of the new two-dimensional array.
	 * @return <b>array</b> - The new two-dimensional array with the specified size.
	 */
	public static int[][] array1Dto2D(ArrayList<Integer> array, int xSize, int ySize){
		int[][] newArray = new int[ySize][xSize];
		for(int j = 0; j < newArray.length; j++) {
			for(int i = 0; i < newArray[j].length; i++) {
				int index = (j * xSize) + i;
				newArray[j][i] = array.get(index);
			}
		}
		return newArray;
	}
	
	/**
	 * Converts a two-dimensional array to a one-dimensional array. <br>
	 * Used when converting a new level from the level editor rendering in two dimensions back into a level file.
	 * @param array - The two-dimensional array being converted into a one-dimensional array.
	 * @return <b>array</b> - The new one-dimensional array.
	 */
	public static int[] array2Dto1D(int[][] array) {
		int[] newArray = new int[array.length * array[0].length];
		for(int j = 0; j < array.length; j++) {
			for(int i = 0; i < array[0].length; i++) {
				int index = (j * array[0].length) + i;
				newArray[index] = array[j][i];
			}
		}
		return newArray;
	}
	
	/**
	 * Calculates the distance between two objects, such as for shooting nearby enemies or assisting nearby units. <br>
	 * Uses the Pythagorean Theorem to calculate the shortest path between two objects.
	 * @param x1 - Horizontal position of object 1
	 * @param y1 - Vertical position of object 1
	 * @param x2 - Horizontal position of object 2
	 * @param y2 - Vertical position of object 2
	 * @return <b>integer</b> - The absolute value of the net distance between two objects.
	 */
	public static int getDistance(float x1, float y1, float x2, float y2) {
		return (int)Math.hypot(Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
