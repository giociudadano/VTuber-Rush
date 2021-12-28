package com.vtuberrush.src.main;

public enum GameStates {
	
	PLAYING, EDITING, MENU, SETTINGS, GAME_OVER;
	
	public static GameStates gameState = MENU;
	
	public static void setGameState(GameStates state) {
		gameState = state;
	}
}
