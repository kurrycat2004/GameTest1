package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.Game;

/**
 * Settings that get saved in config.json every 2min and when the window gets closed.
 */
public class Settings {
	public static int normalHighscore = 0;
	public static int easyHighscore = 0;
	public static int hardHighscore = 0;
	public static boolean showFPS = false;
	public static boolean showBoxSpeed = false;
	public static double average = 99999;
	public static Game.Modes currentMode = Game.Modes.EASY;
}
