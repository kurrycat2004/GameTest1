package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;

/**
 * The settings screen extends {@link Screen}
 */
public class SettingsScreen extends Screen {
	/**
	 * The screen instance
	 */
	public static final SettingsScreen instance = new SettingsScreen();

	static {
		screens.add(instance);
	}

	/**
	 * Menu {@link Button}
	 */
	private final Button menu = new Button("Menu", 0, 0, 300, 60).copyDesign(Menu.mainDesign);
	/**
	 * Show FPS {@link Button}
	 */
	private final Button showFPS = new Button("", 0, 0, 300, 60).copyDesign(Menu.mainDesign);
	/**
	 * Show box speed {@link Button}
	 */
	private final Button showBoxSpeed = new Button("", 0, 0, 300, 60).copyDesign(Menu.mainDesign);

	/**
	 * Default constructor that adds all clicked callbacks for the buttons
	 */
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

	/**
	 * Window resized event
	 */
	public void windowResized() {
		showFPS.setMiddleOffsetPos(0, -70);
		showBoxSpeed.setMiddleOffsetPos(0, 0);
		menu.setMiddleOffsetPos(0, 140);
	}

	/**
	 * Empty init event
	 */
	public void init() {

	}

	/**
	 * Empty keyPressed event
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyPressed(int keyCode) {

	}

	/**
	 * Empty keyReleased event
	 *
	 * @param keyCode keyCode of the key that got released
	 */
	public void keyReleased(int keyCode) {

	}

	/**
	 * Draw method that calls {@link ScreenHandler#drawBackground(int color)} and {@link ScreenHandler#drawArrows()},
	 * updates the text of the buttons and draws them with {@link #drawButtons()}
	 */
	public void draw() {
		ScreenHandler.drawBackground(0);
		ScreenHandler.drawArrows();

		showFPS.setText("Show FPS: " + (Settings.showFPS ? "On" : "Off"));
		showBoxSpeed.setText("Show Boxspeed:" + (Settings.showBoxSpeed ? " On " : " Off "));

		drawButtons();
	}

	/**
	 * Calls {@link #updateButtons()} and {@link ScreenHandler#updateArrows()}
	 */
	public void update() {
		updateButtons();
		ScreenHandler.updateArrows();
	}
}
