package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Button;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.ScreenHandler;
import io.github.kurrycat.arrows.Settings;

/**
 * Modes screen extends {@link Screen}
 */
public class Modes extends Screen {
	/**
	 * Screen instance
	 */
	public static final Modes instance = new Modes();

	static {
		screens.add(instance);
	}

	/**
	 * Menu button
	 */
	private final Button menu = new Button("Menu", 0, 0, 200, 60).copyDesign(Menu.mainDesign);
	/**
	 * Start Game button
	 */
	private final Button startGame = Button.middleOffset("Start Game", 0, 50, 200, 60).copyDesign(Menu.mainDesign);

	/**
	 * Easy mode button
	 */
	private final Button easyMode = new Button("Easy", 0, 0, 200, 60)
			                                .copyDesign(menu)
			                                .setClickedCallback(() -> {
				                                Settings.currentMode = Game.Modes.EASY;
			                                }).setHoverMethod(() -> Settings.currentMode == Game.Modes.EASY);
	/**
	 * Normal mode button
	 */
	private final Button normalMode = new Button("Normal", 0, 0, 200, 60)
			                                  .copyDesign(menu)
			                                  .setClickedCallback(() -> {
				                                  Settings.currentMode = Game.Modes.NORMAL;
			                                  }).setHoverMethod(() -> Settings.currentMode == Game.Modes.NORMAL);
	/**
	 * Hard mode button
	 */
	private final Button hardMode = new Button("Hard", 0, 0, 200, 60)
			                                .copyDesign(menu)
			                                .setClickedCallback(() -> {
				                                Settings.currentMode = Game.Modes.HARD;
			                                }).setHoverMethod(() -> Settings.currentMode == Game.Modes.HARD);

	/**
	 * Initializes all button clicked callbacks
	 */
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

	/**
	 * Window resized event handler
	 * Repositions all the buttons based on the new screen size
	 */
	public void windowResized() {
		menu.setMiddleOffsetPos(0, 70);
		startGame.setMiddleOffsetPos(0, 140);

		easyMode.setMiddleOffsetPos(0, -240);
		normalMode.setMiddleOffsetPos(0, -170);
		hardMode.setMiddleOffsetPos(0, -100);
	}

	/**
	 * Empty init event handler
	 */
	public void init() {

	}

	/**
	 * Empty keyPressed event handler
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyPressed(int keyCode) {

	}

	/**
	 * Empty keyReleased event handler
	 *
	 * @param keyCode keyCode of the key that got released
	 */
	public void keyReleased(int keyCode) {

	}

	/**
	 * Draw method that calls {@link ScreenHandler#drawBackground(int color)}, {@link ScreenHandler#drawArrows()} and {@link #drawButtons()}
	 */
	public void draw() {
		ScreenHandler.drawBackground(0);
		ScreenHandler.drawArrows();
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
