package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Button;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.ScreenHandler;

public class Menu extends Screen {
	public static final Menu instance = new Menu();

	static {
		screens.add(instance);
	}

	private final Button startGame = new Button("Start Game", p.width / 2 - 50, p.height / 2 - 20, 100, 40);
	private final Button settings = new Button("Settings", p.width / 2 - 50, p.height / 2 + 30, 100, 40);

	public Menu() {
		buttonList.add(startGame);
		startGame.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});

		buttonList.add(settings);
		settings.setClickedCallback(() -> {
			ScreenHandler.pushScreen(SettingsScreen.instance);
		});
	}

	public void windowResized() {
		startGame.setX(p.width / 2 - 50).setY(p.height / 2 - 20);
		settings.setX(p.width / 2 - 50).setY(p.height / 2 + 30);
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		p.background(255, 192, 203);
		drawButtons();
	}

	public void update() {
		updateButtons();
	}
}
