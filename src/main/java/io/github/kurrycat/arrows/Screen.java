package io.github.kurrycat.arrows;

import processing.event.KeyEvent;

import java.util.ArrayList;

public abstract class Screen {
	public static Sketch p = Sketch.p;
	protected static ArrayList<Screen> screens = new ArrayList<>();

	public ArrayList<Button> buttonList = new ArrayList<>();

	public void drawButtons() {
		for (Button b : buttonList) b.draw();
	}

	public void updateButtons() {
		for (Button b : buttonList) b.update();
	}

	public abstract void windowResized();

	public abstract void init();

	public abstract void keyPressed(int keyCode);

	public abstract void keyReleased(int keyCode);

	public abstract void draw();

	public abstract void update();

	public void handleEscape(KeyEvent e) {
		if (e.getAction() == 1 && e.getKey() == 27)
			ScreenHandler.back();
	}

	public void handleKeyEvent(KeyEvent e) {
		handleEscape(e);
	}
}
