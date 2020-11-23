package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.*;

import java.util.ArrayList;

public class ScreenHandler {
	private static Screen beforeScreen;
	private static Screen currentScreen;
	private static final ArrayList<Screen> history = new ArrayList<>();
	private static int alpha = 255;

	public static ArrayList<BackgroundArrow> backgroundArrows = new ArrayList<>();

	public static void loadAllScreens() {
		Screen s = Menu.instance;
		s = Game.instance;
		s = GameOver.instance;
		s = SettingsScreen.instance;
		s = Help.instance;
	}

	public static void pushScreen(Screen screen) {
		pushScreen(screen, true);
	}

	public static void pushScreen(Screen screen, boolean alpha) {
		if (alpha) ScreenHandler.alpha = 0;
		if (screen == Game.instance)
			Sketch.p.getSurface().hideCursor();
		else {
			Sketch.p.getSurface().showCursor();
		}
		if (currentScreen != null) {
			history.add(currentScreen);
			beforeScreen = currentScreen;
		}
		if (screen == Game.instance || screen == Menu.instance)
			history.clear();
		currentScreen = screen;
		currentScreen.init();
	}

	public static void drawCurrentScreen() {
		if (alpha < 255 && beforeScreen != null) beforeScreen.draw();
		currentScreen.draw();
	}

	public static Screen getCurrentScreen() {
		return currentScreen;
	}

	public static void back() {
		if (history.size() > 0) {
			Sketch.p.getSurface().showCursor();
			currentScreen = history.get(history.size() - 1);
			history.remove(history.size() - 1);
		}
	}

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

	public static void update() {
		if (alpha < 255) {
			alpha += 10;
		} else if (alpha != 255) alpha = 255;
	}

	public static void drawArrows() {
		for (int i = 0; i < ScreenHandler.backgroundArrows.size(); i++) {
			ScreenHandler.backgroundArrows.get(i).draw();
		}
	}

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
