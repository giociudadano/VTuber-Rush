package com.vtuberrush.src.helpers;

/**
 * This class is a collection of in-game numeric values used for game balance and
 * difficulty but converted to a readable format for future authors.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Constants {
	
	/**
	 * Defines the current direction, used in pathfinding
	 * and setting the velocity of an <b><i>enemy</i></b>, 
	 * 
	 * @author Gio Carlo Ciudadano
	 * @version 0.0.1-alpha.1
	 */
	public static class Direction {
		
		/** This <b><i>enemy</i></b> is going left. */
		public static final int LEFT = 0;
		
		/** This <b><i>enemy</i></b> is going up. */
		public static final int UP = 1;
		
		/** This <b><i>enemy</i></b> is going right. */
		public static final int RIGHT = 2;
		
		/** This <b><i>enemy</i></b> is going down. */
		public static final int DOWN = 3;
	}
	
	/**
	 * Defines the type of tile, used in <b><i>enemy</i></b> pathfinding and
	 * placing down a <b><i>unit</i></b> in the playing field.<p>
	 * 
	 * @author Gio Carlo Ciudadano
	 * @version 0.0.1-alpha.1
	 */
	public static class Tiles {
		
		/** Users can place aquatic units only and enemies cannot path to this tile. */
		public static final int WATER = 0;
		
		/** Users can place non-aquatic units only and enemies cannot path to this tile. */
		public static final int GRASS = 1;
		
		/** Users cannot place units in this tile and enemies can path to this tile. */
		public static final int ROAD = 2;
	}
	
	/**
	 * Defines the type of <b><i>enemy</i></b> as well as its speed, maximum health,
	 * and reward when killed.
	 * 
	 * Each additional level of an enemy increases its health by 250% and increases its reward by ~141%
	 * but decreases its speed by 25%.
	 * 
	 * @author Gio Carlo Ciudadano
	 * @version 0.0.1-alpha.1
	 */
	public static class Enemies {
		
		/** A <b><i>Green Slime</i> (Level 1 Slime)</b> has 100 health, 36 speed, and rewards 4 gold. */
		public static final int SLIME_GREEN = 0;
		
		/** A <b><i>Blue Slime</i> (Level 2 Slime)</b> has 250 health, 27 speed, and rewards 6 gold. */
		public static final int SLIME_BLUE = 1;
		
		/** A <b><i>Red Slime</i> (Level 3 Slime)</b> has 625 health, 20.28 speed, and rewards 8 gold. */
		public static final int SLIME_RED = 2;
		
		/** A <b><i>Purple Slime</i> (Level 4 Slime)</b> has 1562 health, 15.12 speed, and rewards 11 gold. */
		public static final int SLIME_PURPLE = 3;
		
		/** A <b><i>White Slime</i> (Level 5 Slime)</b> has 3906 health, 11.14 speed, and rewards 16 gold. */
		public static final int SLIME_WHITE = 4;
		
		/**
		 * Returns how fast the enemy travels per tick in pixels based on their type. <p>
		 * <i>For the amount of pixels the enemy travels per second, multiply the existing speed float by 120.</i><br>
		 * <i>Each additional level decreases the speed of an enemy by 25%.</i>
		 * @param enemyType - The type of enemy inheriting a pre-defined speed constant.
		 * @return <b>speed</b> - The speed of the enemy based on their type.
		 */
		public static float getSpeed(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 0.3f; 
			case SLIME_BLUE: return 0.225f;
			case SLIME_RED: return 0.169f;
			case SLIME_PURPLE: return 0.126f;
			case SLIME_WHITE: return 0.095f;
			default: return 0;
			}
		}
		
		/**
		 * Returns how much health the enemy has based on their type. <p>
		 * <i>Each additional increases the health of an enemy by 250%.</i>
		 * @param enemyType - The type of enemy inheriting a pre-defined health constant.
		 * @return <b>health</b> - The amount of health the enemy has based on their type.
		 */
		public static int getMaxHealth(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 100; 
			case SLIME_BLUE: return 250;
			case SLIME_RED: return 625;
			case SLIME_PURPLE: return 1562;
			case SLIME_WHITE: return 3906;
			default: return 0;
			}
		}
		
		/**
		 * Returns how much gold is rewarded for killing the specified enemy type. <p>
		 * <i>Each additional level increases the gold reward by ~141%.</i>
		 * @param enemyType - The type of enemy inheriting a pre-defined gold reward constant.
		 * @return <b>gold</b> - The amount of gold rewarded for killing the enemy based on their type.
		 */
		public static int getReward(int enemyType) {
			switch (enemyType) {
			case SLIME_GREEN: return 4; 
			case SLIME_BLUE: return 6;
			case SLIME_RED: return 8;
			case SLIME_PURPLE: return 11;
			case SLIME_WHITE: return 16;
			default: return 0;
			}
		}
	}
	
	/**
	 * Defines each <b><i>unit</i></b> including their name, information, flavor,
	 * base range, and base cooldown.
	 * 
	 * @author Gio Carlo Ciudadano
	 * @version 0.0.1-alpha.1
	 */
	public static class Units {
		
		/**
		 * Shoots at an enemy, dealing damage.
		 */
		public static final int POMU = 0;
		
		/**
		 * Shoots at an enemy, dealing damage and applying <b><i>slowing</i></b>.
		 */
		public static final int FINANA = 1;
		
		/**
		 * Shoots at an enemy, dealing damage and creating a secondary explosion at
		 * the location of the collision dealing secondary damage to surrounding enemies.
		 */
		public static final int ELIRA = 2;
		
		/**
		 * Temporarily increases the attack speed of all nearby allies on a short cooldown
		 * when an enemy is in range.
		 */
		public static final int ROSEMI = 3;
		
		/**
		 * Deals damage to all surrounding enemies at a set interval, applying <b><i>freezing</i></b>
		 * if they have not been recently <i>frozen</i> and <b><i>chilling</i></b> if otherwise.
		 */
		public static final int PETRA = 4;
		
		/**
		 * Shoots at an enemy, dealing damage and applying <b><i>burning</i></b>.
		 */
		public static final int SELEN = 5;

		/**
		 * Returns the full name of each VTuber. <p>
		 * @param unitType - The type of unit inheriting a name constant.
		 * @return <b>name</b> - The full name of the VTuber.
		 */
		public static String getName(int unitType) {
			switch (unitType) {
			case POMU: return "Pomu Rainpuff";
			case FINANA: return "Finana Ryugu";
			case ELIRA: return "Elira Pendora";
			case ROSEMI: return "Rosemi Lovelock";
			case PETRA: return "Petra Gurin";
			case SELEN: return "Selen Tatsuki";
			default: return "";
			}
		}
		
		/**
		 * Returns how much gold would be subtracted from purchasing the VTuber. <p>
		 * @param unitType - The type of unit inheriting a pre-defined name constant.
		 * @return <b>name</b> - The full name of the VTuber.
		 */
		public static int getCost(int unitType) {
			switch (unitType) {
			case POMU: return 100;
			case FINANA: return 75;
			case ELIRA: return 150;
			case ROSEMI: return 150;
			case PETRA: return 75;
			case SELEN: return 150;
			default: return 0;
			}
		}
		
		/**
		 * Returns a summary of how each unit type functions. <p>
		 * @param unitType - The type of unit inheriting a pre-defined information summary.
		 * @return <b>info</b> - The summary of how the passed unit functions.
		 */
		public static String getInfo(int unitType) {
			switch (unitType) {
			case POMU:
				return "Deals moderate damage to enemies.\n";
			case FINANA: 
				return "Deals light damage to enemies. Enemies hit\n"
					+ "are slowed by 60% for 2.5 seconds. Can only\n"
					+ "be placed in water.";
			case ELIRA: 
				return "Deals heavy damage to enemies. Fireballs\n"
					+ "explode on-hit, dealing 50% of the damage to\n"
					+ "nearby enemies.";
			case ROSEMI:
				return "Grants 50% attack speed to nearby allies for\n"
					+ "3 seconds when an enemy is in range (5\n"
					+ "second cooldown per unit).";
			case PETRA:
				return "Deals very light damage to enemies. Enemies hit\n"
					+ "are stunned for 0.75 seconds (5s cooldown\n"
					+ "per unit). Nearby enemies are slowed by 25%.";
			case SELEN:
				return "Deals heavy damage to enemies. Enemies hit\n"
					+ "burn a portion of their maximum health for\n"
					+ "4 seconds.";
			default:
				return "";
			}
		}
		
		/**
		 * Returns a short characterization of each unit type. <p>
		 * @param unitType - The type of unit inheriting a pre-defined flavor text.
		 * @return <b>flavor</b> - The short characterization of the passed unit type.
		 */
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
			case ROSEMI:
				return "A rose maiden raised in a beautiful garden. Pretty\n"
					+ "as a rose in a vase, but a little sharp to the touch.\n";
			case PETRA:
				return "A penguin who spent much time alone in the frosty\n"
					+ "sea. Like the waves during a storm, she is the bearer\n"
					+ "of both complexity and charm.";
			case SELEN:
				return "A sky dragon descended from the moon. She has a\n"
					+ "gentle heart hidden underneath her mischievous\n"
					+ "nature like the glow of the moon on a cloudy night.";
			default:
				return "";
			}
		}
		
		/**
		 * Returns how much damage is dealt from a <i>projectile</i> or <i>blast <b>(Petra)</b></i>.
		 * @param unitType - The type of unit inheriting a pre-defined damage constant.
		 * @return <b>damage</b> - The amount of damage dealt by the passed unit type.
		 */
		public static int getDamage(int unitType) {
			switch (unitType) {
			case POMU: return 35; 
			case FINANA: return 15;
			case ELIRA: return 50;
			case PETRA: return 4;
			case SELEN: return 75;
			default: return 0;
			}
		}
		
		/**
		 * Returns the maximum distance needed for a unit type to <i>see</i> an enemy.
		 * @param unitType - The type of unit inheriting a pre-defined range constant.
		 * @return <b>range</b> - The maximum distance needed for a unit type to <i>see</i> an enemy.
		 */
		public static float getRange(int unitType) {
			switch (unitType) {
			case POMU: return 320; 
			case FINANA: return 250;
			case ELIRA: return 250;
			case ROSEMI: return 400;
			case PETRA: return 160;
			case SELEN: return 250;
			default: return 0;
			}
		}
		
		/**
		 * Returns the amount of time needed for a unit type to <i>attack</i> an enemy again.
		 * @param unitType - The type of unit inheriting a pre-defined cooldown constant.
		 * @return <b>cooldown</b> - The amount of time needed for a unit type to <i>attack</i> an enemy again.
		 */
		public static float getCooldown(int unitType) {
			switch (unitType) {
			case POMU: return 100; 
			case FINANA: return 200;
			case ELIRA: return 250;
			case ROSEMI: return 180;
			case PETRA: return 120;
			case SELEN: return 350;
			default: return 0;
			}
		}
		
	}

	/**
	 * Defines each <b><i>projectile</i></b> including their owner, special properties,
	 * and speed.
	 * 
	 * @author Gio Carlo Ciudadano
	 * @version 0.0.1-alpha.1
	 */
	public static class Projectiles {
		/**
		 * Fired by <b><i>Pomu</i></b>.
		 */
		public static final int POMU_PROJ = 0;
		/**
		 * Fired by <b><i>Finana</i></b>, applying a <i>slow</i> to the enemy when hit.
		 */
		public static final int FINANA_PROJ = 1;
		/**
		 * Fired by <b><i>Elira</i></b>, creating an <i>explosion</i> on the location of the enemy when hit.
		 */
		public static final int ELIRA_PROJ = 2;
		/**
		 * Fired by <b><i>Selen</i></b>, applying a <i>burn</i> to the enemy when hit.
		 */
		public static final int SELEN_PROJ = 5;
		
		/**
		 * Returns how fast the projectile travels per tick in pixels based on their source. <p>
		 * <i>For the amount of pixels the enemy travels per second, multiply the existing speed float by 120.</i><br>
		 * @param projType - The type of projectile inheriting a pre-defined speed constant.
		 * @return <b>speed</b> - The speed of the projectile based on their type.
		 */
		public static float getSpeed(int projType) {
			switch(projType) {
			case POMU_PROJ: return 2.5f;
			case FINANA_PROJ: return 4f;
			case ELIRA_PROJ: return 2f;
			case SELEN_PROJ: return 0.8f;
			default: return 0f;
			}
		}
	}
}
