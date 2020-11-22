package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Box;
import io.github.kurrycat.arrows.Button;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.ScreenHandler;
import processing.event.KeyEvent;

public class GameOver extends Screen {
	public static final GameOver instance = new GameOver();

	private final Button restart = Button.middleOffset("Restart", 0, 0, 140, 40);

	static {
		screens.add(instance);
	}

	public GameOver() {
		buttonList.add(restart);
		restart.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});
	}

	@Override
	public void handleKeyEvent(KeyEvent e) {

	}

	public void windowResized() {
		restart.setMiddleOffsetPos(0, 0);
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		Game.instance.draw();
		p.fill(0, 0, 0, 100);
		p.rect(0, 0, p.width, p.height);

		p.noStroke();
		p.fill(100, 255, 100);
		p.textSize(Box.BOX_SIZE / 3f);
		p.text(Game.instance.roundHighscore, p.width / 2f, p.height / 2f - Box.BOX_SIZE * 2f);

		drawButtons();
	}

	public void update() {
		updateButtons();
	}
}
