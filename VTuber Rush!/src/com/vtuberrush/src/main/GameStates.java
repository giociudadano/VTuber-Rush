package com.vtuberrush.src.main;

/**
 * Enumerates all possible game states which is used for detecting game logic,
 * rendering, user interface, and other objects related to that game state.<p>
 * Defaults to the {@link #MENU} when first initialized by the program.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public enum GameStates {
	
	/**
	 * Builds the game level and action bar, allowing the user to place units on the level map.<br>
	 * Set when the player presses <b>PLAY</b> from the main menu screen.
	 * or <b>TRY AGAIN</b> from the game over screen.
	 * */
	PLAYING,
	
	/**
	 * Builds the game level and tool bar, allowing the user to edit tiles on the level map.<br>
	 * Set when the player presses <b>EDIT</b> from the main menu screen.
	 */
	EDITING,
	
	/**
	 * Builds a set of buttons in the window responsible for switching to other game states.<br>
	 * Set as default or when the player presses <b>MENU</b> from other game screens.
	 */
	MENU,
	
	/**
	 * Builds temporary text related to the author of the program.<br>
	 * Set when the player presses <b>SETTINGS</b> from the main menu screen.
	 */	
	SETTINGS,
	
	/**
	 * Builds buttons to return to the main menu or trying again.<br> 
	 * Set when the player loses lives or finishes all waves in the game.
	 */
	GAME_OVER;
	
	/**
	 * Initializes the menu as the default game state.
	 */
	public static GameStates gameState = MENU;
	
	/**
	 * Sets the current {@link #gameState} of the program, called when pressing buttons
	 * or when game end conditions are met. 
	 * @param state - The current game state being set, equivalent to one of the enumerated names.
	 */
	public static void setGameState(GameStates state) {
		gameState = state;
	}
}
