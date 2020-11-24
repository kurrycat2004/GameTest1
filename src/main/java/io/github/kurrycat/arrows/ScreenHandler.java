package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.*;

import java.util.ArrayList;

/**
 * ScreenHandler class that handles the different {@link Screen} subclasses.
 */
public class ScreenHandler {
	private static Screen beforeScreen;
	private static Screen currentScreen;
	private static final ArrayList<Screen> history = new ArrayList<>();
	private static int alpha = 255;

	public static ArrayList<BackgroundArrow> backgroundArrows = new ArrayList<>();

	/**
	 * Method that makes sure that all static codeblocks in the Screens get executed.
	 */
	public static void loadAllScreens() {
		Screen s = Menu.instance;
		s = Game.instance;
		s = GameOver.instance;
		s = SettingsScreen.instance;
		s = Help.instance;
	}

	/**
	 * Method to push a Screen subclass instance.<br>
	 * Calls {@link #pushScreen(Screen screen, boolean alpha)} with {@code alpha = true}.<br>
	 *
	 * @param screen The screen to be pushed.
	 */
	public static void pushScreen(Screen screen) {
		pushScreen(screen, true);
	}

	/**
	 * Method to push a Screen subclass instance.<br>
	 * Pushes the new screen with a smooth transition if {@code alpha} is {@code true} and adds it to {@link #history}
	 *
	 * @param screen The screen to be pushed.
	 * @param alpha  boolean that decides if the screen should be pushed with a transition.
	 */
	public static void pushScreen(Screen screen, boolean alpha) {
		if (alpha) ScreenHandler.alpha = 0;
		if (screen == Game.instance)
			Sketch.p.getSurface().hideCursor();
		else {
			Sketch.p.getSurface().showCursor();
		}
		if (currentScreen != null) {
			history.add(currentScreen);
		}
		if (screen == Game.instance || screen == Menu.instance)
			history.clear();

		pushScreenNoHistory(screen, alpha);
	}

	/**
	 * Method to push a Screen subclass instance.<br>
	 * Pushes the new screen with a smooth transition if {@code alpha} is {@code true}
	 *
	 * @param screen The screen to be pushed.
	 * @param alpha  boolean that decides if the screen should be pushed with a transition.
	 */
	private static void pushScreenNoHistory(Screen screen, boolean alpha) {
		if (alpha) ScreenHandler.alpha = 0;
		if (currentScreen != null) {
			beforeScreen = currentScreen;
		}
		currentScreen = screen;
		currentScreen.init();
	}

	/**
	 * Draws {@link #currentScreen} and {@link #beforeScreen} if in transition.
	 */
	public static void drawCurrentScreen() {
		if (alpha < 255 && beforeScreen != null) beforeScreen.draw();
		currentScreen.draw();
	}

	/**
	 * {@link #currentScreen} getter
	 *
	 * @return {@link #currentScreen}
	 */
	public static Screen getCurrentScreen() {
		return currentScreen;
	}

	/**
	 *
	 */
	public static void back() {
		if (history.size() > 0) {
			Screen screenToPush = history.get(history.size() - 1);
			Sketch.p.getSurface().showCursor();
			pushScreenNoHistory(screenToPush, true);
			history.remove(history.size() - 1);
		}
	}


	/**
	 * Draws the background with alpha for transitions
	 *
	 * @param color The color of the background
	 */
	public static void drawBackground(int color) {
		int alpha = ScreenHandler.alpha;
		Class<?> caller = null;
		try {
			caller = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
		} catch (ClassNotFoundException ignored) {
		}
		if (caller != null && beforeScreen != null && caller == beforeScreen.getClass()) {
			alpha = 255 - alpha;
		}
		Sketch.p.noStroke();
		Sketch.p.fill(color, alpha);
		Sketch.p.rect(0, 0, Sketch.p.width, Sketch.p.height);
	}

	/**
	 * Updates the alpha
	 */
	public static void update() {
		if (alpha < 255) {
			alpha += 10;
		} else if (alpha != 255) alpha = 255;
	}

	/**
	 * Draws the background arrows
	 */
	public static void drawArrows() {
		for (int i = 0; i < ScreenHandler.backgroundArrows.size(); i++) {
			ScreenHandler.backgroundArrows.get(i).draw();
		}
	}

	/**
	 * Moves the background arrows
	 */
	public static void updateArrows() {
		for (BackgroundArrow s : backgroundArrows) s.update();
		if (Math.random() < 0.3) {
			backgroundArrows.add(new BackgroundArrow(Sketch.p.width + 100, Math.random() * Sketch.p.height, -Math.random() - 2, (Math.random() - 0.5), Math.random() * 20 + 20));
		}
		for (int i = backgroundArrows.size() - 1; i >= 0; i--) {
			BackgroundArrow s = backgroundArrows.get(i);
			if (s.x < 0 || s.x > Sketch.p.width + 100 || s.y < 0 || s.y > Sketch.p.height)
				backgroundArrows.remove(i);
		}
	}
}
