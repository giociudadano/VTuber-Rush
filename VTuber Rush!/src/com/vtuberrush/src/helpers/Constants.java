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
		
		public static int getMaxHealth(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 100; 
			case SLIME_BLUE: return 60;
			case SLIME_RED: return 250;
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
			case POMU:
				return "A fairy who lives in a lush forest. Supple like a\n"
					+ "flower in the breeze, cheerful and optimistic.";
			case FINANA: 
				return "A mermaid spending time with tropical fish in\n"
					+ "the sea of coral reefs. Has a heart as clear\n"
					+ "and pure as the calm and beautiful sea.";
			case ELIRA: 
				return "A sky dragon who came from the heavens closest\n"
					+ "to the sun. She has the kindness that makes the\n"
					+ "light of a sunny day fall equally on all.";
			default:
				return "";
			}
		}
		
		public static float getDamage(int unitType) {
			switch (unitType) {
			case POMU: return 15; 
			case FINANA: return 15;
			case ELIRA: return 15;
			default: return 0;
			}
		}
		
		public static float getRange(int unitType) {
			switch (unitType) {
			case POMU: return 220; 
			case FINANA: return 300;
			case ELIRA: return 180;
			default: return 0;
			}
		}
		
		public static float getCooldown(int unitType) {
			switch (unitType) {
			case POMU: return 10; 
			case FINANA: return 10;
			case ELIRA: return 10;
			default: return 0;
			}
		}
		
	}

	public static class Projectiles {
		public static final int POMU_PROJ = 0;
		public static final int FINANA_PROJ = 1;
		public static final int ELIRA_PROJ = 2;
		
		public static float getSpeed(int projType) {
			switch(projType) {
			case POMU_PROJ: return 2f;
			case FINANA_PROJ: return 3f;
			case ELIRA_PROJ: return 1f;
			default: return 0f;
			}
		}
	}
}
