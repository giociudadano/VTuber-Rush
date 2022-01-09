package com.vtuberrush.src.scenes;

import static com.vtuberrush.src.main.GameStates.MENU;
import static com.vtuberrush.src.main.GameStates.setGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.vtuberrush.src.main.Game;
import com.vtuberrush.src.ui.Button;

/**
 * An <b><i>settings game scene</i></b> is a subclass of a game scene is a <i>future use</i>
 * scene for adjusting the settings, currently being used by the author as acknowledgement to
 * all the players currently playing the game.
 * 
 * @author Gio Carlo Ciudadano
 * @version 0.0.1-alpha.1
 */
public class Settings extends GameScene implements SceneMethods {

	private Button buttonMenu;
	
	/**
	 * Creates a new <b><i>settings game scene</i></b>, and initializes
	 * the button for going back to the main menu.
	 * @param game - The current instance of the game.
	 */
	public Settings(Game game) {
		super(game);
		initButtons();
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, 1280, 720);
		drawButtons(graphics);
		drawText(graphics);
	}
	
	private void drawText(Graphics graphics) {
		graphics.setColor(new Color(211, 186, 145));
		graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 15));
		graphics.drawString("To be added in the future.", 550, 350);
		graphics.drawString("Thank you for supporting the game!", 510, 370);
		graphics.setFont(new Font("MiHoYo_SDK_Web", Font.PLAIN, 12));
		graphics.drawString("Gio Ciudadano", 610, 395);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			setGameState(MENU);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		buttonMenu.setMouseOver(false);
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (buttonMenu.getBounds().contains(x, y)) {
			buttonMenu.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	private void initButtons() {
		buttonMenu = new Button("Menu", 10, 10, 80, 30);
	}
	
	private void drawButtons(Graphics graphics) {
		buttonMenu.draw(graphics);
	}
	
	private void resetButtons() {
		buttonMenu.resetButtons();
	}

}
