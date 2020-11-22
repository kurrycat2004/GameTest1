package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Button;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.ScreenHandler;
import io.github.kurrycat.arrows.Sketch;

public class Help extends Screen {
	public static final Help instance = new Help();

	private final Button menu = Button.middleOffset("Menu", 0, 0, 140, 40);

	static {
		screens.add(instance);
	}

	public Help() {
		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});

		menu.setBgColor(Sketch.p.color(100))
				.setHoverBgColor(Sketch.p.color(100))
				.setStrokeColor(null)
				.setHoverStrokeColor(Sketch.p.color(200));
	}

	public void windowResized() {
		menu.setMiddleOffsetPos(0, 0);
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		ScreenHandler.drawBackground(0);
		drawButtons();
	}

	public void update() {
		updateButtons();
	}
}
