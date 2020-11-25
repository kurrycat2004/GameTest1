package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.event.KeyEvent;

/**
 * GameOver screen
 */
public class GameOver extends Screen {
	/**
	 * Screen instance
	 */
	public static final GameOver instance = new GameOver();

	/**
	 * Restart button
	 */
	private final Button restart = Button.middleOffset("Restart", 0, 0, 140, 40).copyDesign(Menu.mainDesign);
	/**
	 * Menu button
	 */
	private final Button menu = Button.middleOffset("Menu", 0, 0, 140, 40).copyDesign(Menu.mainDesign);

	static {
		screens.add(instance);
	}

	/**
	 * Initializes all button clicked callbacks
	 */
	public GameOver() {
		buttonList.add(restart);
		restart.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});

		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});
	}

	/**
	 * Empty key event handler
	 *
	 * @param e The KeyEvent
	 */
	@Override
	public void handleKeyEvent(KeyEvent e) {

	}

	/**
	 * Window resized event handler
	 * Repositions all button based on new screen size
	 */
	public void windowResized() {
		restart.setMiddleOffsetPos(0, 0);
		menu.setMiddleOffsetPos(0, 50);
	}

	/**
	 * Empty init event handler
	 */
	public void init() {

	}

	/**
	 * Empty keyPressed event handler
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyPressed(int keyCode) {

	}

	/**
	 * Empty keyReleased event handler
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyReleased(int keyCode) {

	}

	/**
	 * Draw method that calls {@link Game#draw()} and draws a black background with alpha and the buttons over it
	 */
	public void draw() {
		Game.instance.draw();

		Sketch.p.fill(0, 0, 0, 100);
		Sketch.p.rect(0, 0, Sketch.p.width, Sketch.p.height);

		Sketch.p.noStroke();
		Sketch.p.fill(100, 255, 100);
		Sketch.p.textSize(Box.BOX_SIZE / 3f);
		Sketch.p.text(Game.instance.roundHighscore, Sketch.p.width / 2f, Sketch.p.height / 2f - Box.BOX_SIZE * 2f);

		drawButtons();
	}

	/**
	 * Calls {@link #updateButtons()}
	 */
	public void update() {
		updateButtons();
	}
}
