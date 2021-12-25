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
		public static final int FINANA = 1;
		public static final int ELIRA = 2;
		
		public static String getName(int unitType) {
			switch (unitType) {
			case POMU: return "Pomu Rainpuff";
			case FINANA: return "Finana Ryugu";
			case ELIRA: return "Elira Pendora";
			default: return "";
			}
		}
		
		public static String getFlavor(int unitType) {
			switch (unitType) {
			case POMU: return "A fairy who lives in a lush forest. Supple like a\nflower in the breeze, cheerful and optimistic.";
			case FINANA: return "A mermaid spending time with tropical fish in\nthe sea of coral reefs. Has a heart as clear\nand pure as the calm and beautiful sea.";
			case ELIRA: return "A sky dragon who came from the heavens closest\nto the sun. She has the kindness that makes the\nlight of a sunny day fall equally on all.";
			default: return "";
			}
		}
		
	}
}
