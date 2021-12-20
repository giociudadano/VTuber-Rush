package com.vtuberrush.src.main;

public enum GameStates {
	
	PLAYING, MENU, SETTINGS;
	
	public static GameStates gameState = MENU;
	
	public static void setGameState(GameStates state) {
		gameState = state;
	}
}
