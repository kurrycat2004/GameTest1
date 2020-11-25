package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;

public class SettingsScreen extends Screen {
	public static final SettingsScreen instance = new SettingsScreen();

	static {
		screens.add(instance);
	}

	private final Button menu = new Button("Menu", 0, 0, 300, 60).copyDesign(Menu.mainDesign);
	private final Button showFPS = new Button("", 0, 0, 300, 60).copyDesign(Menu.mainDesign);
	private final Button showBoxSpeed = new Button("", 0, 0, 300, 60).copyDesign(Menu.mainDesign);

	public SettingsScreen() {
		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});

		buttonList.add(showFPS);
		showFPS.setClickedCallback(() -> {
			Settings.showFPS = !Settings.showFPS;
		});

		buttonList.add(showBoxSpeed);
		showBoxSpeed.setClickedCallback(() -> {
			Settings.showBoxSpeed = !Settings.showBoxSpeed;
		});
	}

	public void windowResized() {
		showFPS.setMiddleOffsetPos(0, -70);
		showBoxSpeed.setMiddleOffsetPos(0, 0);
		menu.setMiddleOffsetPos(0, 140);
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

		showFPS.setText("Show FPS: " + (Settings.showFPS ? "On" : "Off"));
		showBoxSpeed.setText("Show Boxspeed:" + (Settings.showBoxSpeed ? " On " : " Off "));

		drawButtons();
	}

	public void update() {
		updateButtons();
		ScreenHandler.updateArrows();
	}
}
