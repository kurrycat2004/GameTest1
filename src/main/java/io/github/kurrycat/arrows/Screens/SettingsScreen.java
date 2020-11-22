package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Button;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.Settings;
import io.github.kurrycat.arrows.Sketch;

public class SettingsScreen extends Screen {
	public static final SettingsScreen instance = new SettingsScreen();

	static {
		screens.add(instance);
	}

	private final Button fullscreen = new Button("Fullscreen", p.width / 2 - 70, p.height / 2 - 20, 140, 40);

	public SettingsScreen() {
		buttonList.add(fullscreen);
		fullscreen.setClickedCallback(() -> {
			Settings.fullscreen = !Settings.fullscreen;

			Sketch.p.updateFullscreen();
			//Sketch.jframe.getGraphicsConfiguration().getDevice().setFullScreenWindow(Settings.fullscreen ? Sketch.jframe : null);
			//Sketch.jframe.pack();
		});
	}

	public void windowResized() {
		fullscreen.setX(p.width / 2 - 70).setY(p.height / 2 - 20);
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		p.background(0);
		drawButtons();
	}

	public void update() {
		updateButtons();
		fullscreen.setText("Fullscreen: " + (Settings.fullscreen ? "On" : "Off"));
	}
}
