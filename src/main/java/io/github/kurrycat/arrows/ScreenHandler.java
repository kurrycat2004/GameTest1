package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.*;

import java.util.ArrayList;

public class ScreenHandler {
	private static Screen currentScreen;
	private static final ArrayList<Screen> history = new ArrayList<>();
	private static int alpha = 255;

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
		if (currentScreen != null)
			history.add(currentScreen);
		if (screen == Game.instance || screen == Menu.instance)
			history.clear();
		currentScreen = screen;
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
		Sketch.p.noStroke();
		Sketch.p.fill(color, alpha);
		Sketch.p.rect(0, 0, Sketch.p.width, Sketch.p.height);
	}

	public static void update() {
		if (alpha < 255) {
			alpha += 1;
		} else if (alpha != 255) alpha = 255;
	}
}
