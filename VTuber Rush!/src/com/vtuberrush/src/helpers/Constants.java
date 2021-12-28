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
			case SLIME_GREEN: return 0.3f; 
			case SLIME_BLUE: return 0.3f;
			case SLIME_RED: return 0.3f;
			default: return 0;
			}
		}
		
		public static int getMaxHealth(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 100; 
			case SLIME_BLUE: return 250;
			case SLIME_RED: return 625;
			default: return 0;
			}
		}
		
		public static int getReward(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 5; 
			case SLIME_BLUE: return 7;
			case SLIME_RED: return 10;
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
		
		public static int getCost(int unitType) {
			switch (unitType) {
			case POMU: return 100;
			case FINANA: return 70;
			case ELIRA: return 150;
			default: return 0;
			}
		}
		
		public static String getInfo(int unitType) {
			switch (unitType) {
			case POMU:
				return "Deals moderate damage to enemies.\n";
			case FINANA: 
				return "Deals light damage to enemies. Enemies hit\n"
					+ "are slowed by 50% for 2.5 seconds. Can only\n"
					+ "be placed in water.";
			case ELIRA: 
				return "Deals heavy damage to enemies. Fireballs\n"
					+ "explode on-hit, dealing 50% of the damage to\n"
					+ "surrounding enemies.";
			default:
				return "";
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
		
		public static int getDamage(int unitType) {
			switch (unitType) {
			case POMU: return 35; 
			case FINANA: return 15;
			case ELIRA: return 50;
			default: return 0;
			}
		}
		
		public static float getRange(int unitType) {
			switch (unitType) {
			case POMU: return 320; 
			case FINANA: return 250;
			case ELIRA: return 250;
			default: return 0;
			}
		}
		
		public static float getCooldown(int unitType) {
			switch (unitType) {
			case POMU: return 100; 
			case FINANA: return 200;
			case ELIRA: return 250;
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
			case POMU_PROJ: return 2.5f;
			case FINANA_PROJ: return 4f;
			case ELIRA_PROJ: return 2f;
			default: return 0f;
			}
		}
	}
}
