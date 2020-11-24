package io.github.kurrycat.arrows;

import processing.event.KeyEvent;

import java.util.ArrayList;

/**
 * Abstract Screen class that every screen extends from.
 */
public abstract class Screen {
	protected static ArrayList<Screen> screens = new ArrayList<>();

	public ArrayList<Button> buttonList = new ArrayList<>();

	/**
	 * Draws all buttons in {@link #buttonList}
	 */
	public void drawButtons() {
		for (Button b : buttonList) b.draw();
	}

	/**
	 * Updates all buttons in {@link #buttonList}
	 */
	public void updateButtons() {
		for (Button b : buttonList) b.update();
	}

	/**
	 * Abstract windowResized method
	 */
	public abstract void windowResized();

	/**
	 * Abstract init method
	 */
	public abstract void init();

	/**
	 * Abstract keyPressed method
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public abstract void keyPressed(int keyCode);

	/**
	 * Abstract keyReleased method
	 *
	 * @param keyCode keyCode of the key that got released
	 */
	public abstract void keyReleased(int keyCode);

	/**
	 * Abstract draw method
	 */
	public abstract void draw();

	/**
	 * Abstract update method
	 */
	public abstract void update();

	/**
	 * Calls {@link ScreenHandler#back()} if escape gets pressed
	 *
	 * @param e The KeyEvent
	 */
	public void handleEscape(KeyEvent e) {
		if (e.getAction() == 1 && e.getKey() == 27)
			ScreenHandler.back();
	}

	/**
	 * handleKeyEvent method that calls {@link #handleEscape(KeyEvent event)} by default
	 *
	 * @param e The KeyEvent
	 */
	public void handleKeyEvent(KeyEvent e) {
		handleEscape(e);
	}
}
