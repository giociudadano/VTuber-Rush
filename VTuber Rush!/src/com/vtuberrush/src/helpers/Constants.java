package com.vtuberrush.src.helpers;

public class Constants {
	public static class Direction {
		public static final int NONE = -1;
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class Tiles {
		public static final int WATER = 0;
		public static final int GRASS = 1;
		public static final int ROAD = 2;
	}
	
	public static class Enemies {
		public static final int SLIME_GREEN = 0;
		public static final int SLIME_BLUE = 1;
		public static final int SLIME_RED = 2;
		
		public static float getSpeed(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 0.5f; 
			case SLIME_BLUE: return 0.7f;
			case SLIME_RED: return 0.3f;
			default: return 0;
			}
		}
	}
	
	public static class Units {
		public static final int POMU = 0;
	}
}
