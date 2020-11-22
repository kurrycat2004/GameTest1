package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.core.PFont;

import java.util.ArrayList;

public class Menu extends Screen {
	public static final Button mainDesign = new Button("", 0, 0, 0, 0)
			.setBgColor(255)
			.setHoverBgColor(null)
			.setTextColor(0)
			.setHoverTextColor(255);

	public static final Menu instance = new Menu();
	public final PFont titleFont = new PFont(PFont.findFont("Bookman Old Style Fett Kursiv"), true);


	static {
		screens.add(instance);
	}

	public ArrayList<Star> stars = new ArrayList<>();

	private final Button startGame = new Button("Start Game", Sketch.p.width / 2 - 50, Sketch.p.height / 2 - 20, 200, 60).copyDesign(mainDesign);
	private final Button settings = new Button("Settings", Sketch.p.width / 2 - 50, Sketch.p.height / 2 + 30, 200, 60).copyDesign(mainDesign);
	private final Button help = new RoundButton("?", 0, 0, 15).copyDesign(mainDesign);

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

		buttonList.add(help);
		help.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Help.instance);
		});
/*
		startGame.setBgColor(255)
				.setTextColor(0)
				.setHoverTextColor(255);
		settings.setBgColor(255)
				.setTextColor(0)
				.setHoverTextColor(255);
		help.setBgColor(255)
				.setTextColor(0)
				.setHoverTextColor(255);*/
	}

	public void windowResized() {
		startGame.setMiddleOffsetPos(0, Sketch.p.height / 16);
		settings.setMiddleOffsetPos(0, Sketch.p.height / 16 + 80);
		int titleWidth = Sketch.jframe.getFontMetrics(titleFont.getFont().deriveFont(Sketch.p.height / 6f)).stringWidth("ArrayKeys");
		int titleHeight = Sketch.jframe.getFontMetrics(titleFont.getFont().deriveFont(Sketch.p.height / 6f)).getHeight();
		help.setMiddleOffsetPos((int) (titleWidth / 2f + 10), (int) (-Sketch.p.height / 6f - titleHeight / 2f));
	}

	public void init() {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		ScreenHandler.drawBackground(0);

		for (int i = 0; i < stars.size(); i++) {
			stars.get(i).draw();
		}

		Sketch.p.noStroke();
		//"Forte", "Monospaced.bolditalic", "OCR A Extended"
		Sketch.p.textFont(new PFont(PFont.findFont("Monospaced.bolditalic"), true));
		Sketch.p.textSize(Sketch.p.height / 6f);

		Sketch.p.fill(255);
		Sketch.p.text("ArrowKeys", Sketch.p.width / 2f, Sketch.p.height / 2f - Sketch.p.height / 4f);

		Sketch.p.textSize(Sketch.p.height / 18f);
		Sketch.p.text("Highscore: " + Game.instance.highscore, Sketch.p.width / 2f, Sketch.p.height / 2f - Sketch.p.height / 12f);

		if (Settings.showFPS)
			Sketch.p.showFPS();

		drawButtons();
	}

	public void update() {
		updateButtons();
		for (Star s : stars) s.update();
		if (Math.random() < 0.2) {
			stars.add(new Star(Sketch.p.width + 100, Math.random() * Sketch.p.height, -Math.random() - 2, (Math.random() - 0.5), Math.random() * 20 + 20));
		}
		for (int i = stars.size() - 1; i >= 0; i--) {
			Star s = stars.get(i);
			if (s.x < 0 || s.x > Sketch.p.width + 100 || s.y < 0 || s.y > Sketch.p.height)
				stars.remove(i);
		}
	}
}
