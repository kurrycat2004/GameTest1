package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.Game;
import io.github.kurrycat.arrows.Screens.GameOver;
import io.github.kurrycat.arrows.Screens.Menu;
import io.github.kurrycat.arrows.Screens.SettingsScreen;

import java.util.ArrayList;

public class ScreenHandler {
	private static Screen currentScreen;
	private static final ArrayList<Screen> history = new ArrayList<>();

	public static void loadAllScreens() {
		Screen s = Menu.instance;
		s = Game.instance;
		s = GameOver.instance;
		s = SettingsScreen.instance;
	}

	public static void pushScreen(Screen screen) {
		if (screen != Game.instance)
			Sketch.p.getSurface().showCursor();
		else {
			Sketch.p.getSurface().hideCursor();
			history.clear();
		}
		if (currentScreen != null)
			history.add(currentScreen);
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
}
