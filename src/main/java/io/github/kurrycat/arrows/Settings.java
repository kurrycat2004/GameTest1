package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.Game;

/**
 * Settings that get saved in config.json every 2min and when the window gets closed.
 */
public class Settings {
	/**
	 * Highscore of the {@link io.github.kurrycat.arrows.Screens.Game.Modes#NORMAL} mode
	 */
	public static int normalHighscore = 0;
	/**
	 * Highscore of the {@link io.github.kurrycat.arrows.Screens.Game.Modes#EASY} mode
	 */
	public static int easyHighscore = 0;
	/**
	 * Highscore of the {@link io.github.kurrycat.arrows.Screens.Game.Modes#HARD} mode
	 */
	public static int hardHighscore = 0;
	/**
	 * Boolean indicating whether the FPS should be shown
	 */
	public static boolean showFPS = false;
	/**
	 * Boolean indicating whether the Box speed should be shown
	 */
	public static boolean showBoxSpeed = false;
	/**
	 * The average time PB in {@link io.github.kurrycat.arrows.Screens.Help}
	 */
	public static double average = 99999;
	/**
	 * The current {@link io.github.kurrycat.arrows.Screens.Game.Modes}
	 */
	public static Game.Modes currentMode = Game.Modes.EASY;
}
