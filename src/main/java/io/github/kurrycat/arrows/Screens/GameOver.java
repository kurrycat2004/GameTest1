package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.event.KeyEvent;

public class GameOver extends Screen {
	public static final GameOver instance = new GameOver();

	private final Button restart = Button.middleOffset("Restart", 0, 0, 140, 40).copyDesign(Menu.mainDesign);
	private final Button menu = Button.middleOffset("Menu", 0, 0, 140, 40).copyDesign(Menu.mainDesign);

	static {
		screens.add(instance);
	}

	public GameOver() {
		buttonList.add(restart);
		restart.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});

		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});
	}

	@Override
	public void handleKeyEvent(KeyEvent e) {

	}

	public void windowResized() {
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
