package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.event.KeyEvent;

public class Pause extends Screen {
	public static final Pause instance = new Pause();

	private final Button continueButton = Button.middleOffset("Continue", 0, 0, 140, 40).copyDesign(Menu.mainDesign);
	private final Button restart = Button.middleOffset("Restart", 0, 0, 140, 40).copyDesign(Menu.mainDesign);
	private final Button menu = Button.middleOffset("Menu", 0, 0, 140, 40).copyDesign(Menu.mainDesign);

	static {
		screens.add(instance);
	}

	public Pause() {
		buttonList.add(restart);
		restart.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});

		buttonList.add(continueButton);
		continueButton.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.paused = false;
		});

		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});
	}

	@Override
	public void handleKeyEvent(KeyEvent e) {
		if (e.getAction() == 1 && e.getKey() == 27) {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.paused = false;
		}
	}

	public void windowResized() {
		continueButton.setMiddleOffsetPos(0, -50);
		restart.setMiddleOffsetPos(0, 0);
		menu.setMiddleOffsetPos(0, 50);
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		Game.instance.draw();

		Sketch.p.fill(0, 0, 0, 100);
		Sketch.p.rect(0, 0, Sketch.p.width, Sketch.p.height);

		Sketch.p.noStroke();
		Sketch.p.fill(100, 255, 100);
		Sketch.p.textSize(Box.BOX_SIZE / 3f);
		Sketch.p.text(Game.instance.roundHighscore, Sketch.p.width / 2f, Sketch.p.height / 2f - Box.BOX_SIZE * 2f);

		drawButtons();
	}

	public void update() {
		updateButtons();
	}
}
