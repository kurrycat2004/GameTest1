package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;

public class SettingsScreen extends Screen {
	public static final SettingsScreen instance = new SettingsScreen();

	static {
		screens.add(instance);
	}

	private final Button menu = Button.middleOffset("Settings", 0, 50, 100, 40).copyDesign(Menu.mainDesign);

	public SettingsScreen() {
		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});
	}

	public void windowResized() {
		menu.setMiddleOffsetPos(0, 50);
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		ScreenHandler.drawBackground(0);
		ScreenHandler.drawArrows();
		drawButtons();
	}

	public void update() {
		updateButtons();
		ScreenHandler.updateArrows();
	}
}
