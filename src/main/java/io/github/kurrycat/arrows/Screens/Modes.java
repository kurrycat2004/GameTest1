package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Button;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.ScreenHandler;
import io.github.kurrycat.arrows.Settings;

public class Modes extends Screen {
	public static final Modes instance = new Modes();

	static {
		screens.add(instance);
	}

	private final Button menu = new Button("Menu", 0, 0, 200, 60).copyDesign(Menu.mainDesign);
	private final Button startGame = Button.middleOffset("Start Game", 0, 50, 200, 60).copyDesign(Menu.mainDesign);

	private final Button easyMode = new Button("Easy", 0, 0, 200, 60)
			                                .copyDesign(menu)
			                                .setClickedCallback(() -> {
				                                Settings.currentMode = Game.Modes.EASY;
			                                }).setHoverMethod(() -> Settings.currentMode == Game.Modes.EASY);
	private final Button normalMode = new Button("Normal", 0, 0, 200, 60)
			                                  .copyDesign(menu)
			                                  .setClickedCallback(() -> {
				                                  Settings.currentMode = Game.Modes.NORMAL;
			                                  }).setHoverMethod(() -> Settings.currentMode == Game.Modes.NORMAL);
	private final Button hardMode = new Button("Hard", 0, 0, 200, 60)
			                                .copyDesign(menu)
			                                .setClickedCallback(() -> {
				                                Settings.currentMode = Game.Modes.HARD;
			                                }).setHoverMethod(() -> Settings.currentMode == Game.Modes.HARD);

	public Modes() {
		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});

		buttonList.add(startGame);
		startGame.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});

		buttonList.add(easyMode);
		buttonList.add(normalMode);
		buttonList.add(hardMode);
	}

	public void windowResized() {
		menu.setMiddleOffsetPos(0, 70);
		startGame.setMiddleOffsetPos(0, 140);

		easyMode.setMiddleOffsetPos(0, -240);
		normalMode.setMiddleOffsetPos(0, -170);
		hardMode.setMiddleOffsetPos(0, -100);
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
