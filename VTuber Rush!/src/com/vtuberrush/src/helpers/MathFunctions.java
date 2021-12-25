package com.vtuberrush.src.helpers;

import java.util.ArrayList;

public class MathFunctions {
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
	
	public static int getDistance(float x1, float y1, float x2, float y2) {
		return (int)Math.hypot(Math.abs(x1-x2), Math.abs(y1-y2));
	}
}
