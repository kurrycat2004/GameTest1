package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.core.PFont;

/**
 * Menu class extends {@link Screen}
 */
public class Menu extends Screen {
	/**
	 * static main Button design
	 */
	public static final Button mainDesign = new Button("", 0, 0, 0, 0)
			                                        .setBgColor(255)
			                                        .setHoverBgColor(null)
			                                        .setTextColor(0)
			                                        .setHoverTextColor(255);

	/**
	 * Screen instance
	 */
	public static final Menu instance = new Menu();
	/**
	 * Title font {@link PFont} instance
	 */
	public static final PFont titleFont = new PFont(PFont.findFont("Bookman Old Style Fett Kursiv"), true);

	static {
		screens.add(instance);
	}

	/**
	 * Start Game button
	 */
	private final Button startGame = new Button("Start Game", 0, 0, 200, 60).copyDesign(mainDesign);
	/**
	 * Select Mode button
	 */
	private final Button mode = new Button("Select Mode", 0, 0, 200, 60).copyDesign(mainDesign);
	/**
	 * Settings button
	 */
	private final Button settings = new Button("Settings", 0, 0, 200, 60).copyDesign(mainDesign);
	/**
	 * Help button
	 */
	private final Button help = new RoundButton("?", 0, 0, 15).copyDesign(mainDesign);

	/**
	 * Initializes all button clicked callbacks
	 */
	public Menu() {
		buttonList.add(startGame);
		startGame.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});

		buttonList.add(mode);
		mode.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Modes.instance);
		});

		buttonList.add(settings);
		settings.setClickedCallback(() -> {
			ScreenHandler.pushScreen(SettingsScreen.instance);
		});

		buttonList.add(help);
		help.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Help.instance);
		});
	}

	/**
	 * Window resized event
	 */
	public void windowResized() {
		startGame.setMiddleOffsetPos(0, Sketch.p.height / 16);
		mode.setMiddleOffsetPos(0, Sketch.p.height / 16 + 80);
		settings.setMiddleOffsetPos(0, Sketch.p.height / 16 + 160);
		int titleWidth = Sketch.jframe.getFontMetrics(titleFont.getFont().deriveFont(Sketch.p.height / 6f)).stringWidth("ArrayKeys");
		int titleHeight = Sketch.jframe.getFontMetrics(titleFont.getFont().deriveFont(Sketch.p.height / 6f)).getHeight();
		help.setMiddleOffsetPos((int) (titleWidth / 2f + 10), (int) (-Sketch.p.height / 6f - titleHeight / 2f));
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
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyReleased(int keyCode) {

	}

	/**
	 * Calls {@link ScreenHandler#drawBackground(int color)}, {@link ScreenHandler#drawArrows()}, draws the logo and highscore and calls {@link #drawButtons()}
	 */
	public void draw() {
		ScreenHandler.drawBackground(0);

		ScreenHandler.drawArrows();

		Sketch.p.noStroke();
		//"Forte", "Monospaced.bolditalic", "OCR A Extended"
		Sketch.p.textFont(new PFont(PFont.findFont("Monospaced.bolditalic"), true));
		Sketch.p.textSize(Sketch.p.height / 6f);

		Sketch.p.fill(255);
		Sketch.p.text("ArrowKeys", Sketch.p.width / 2f, Sketch.p.height / 2f - Sketch.p.height / 4f);

		Sketch.p.textSize(Sketch.p.height / 18f);

		int highscore = 0;
		if (Settings.currentMode == Game.Modes.EASY) highscore = Settings.easyHighscore;
		else if (Settings.currentMode == Game.Modes.NORMAL) highscore = Settings.normalHighscore;
		else if (Settings.currentMode == Game.Modes.HARD) highscore = Settings.hardHighscore;

		Sketch.p.text("Highscore: " + highscore, Sketch.p.width / 2f, Sketch.p.height / 2f - Sketch.p.height / 12f);

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
