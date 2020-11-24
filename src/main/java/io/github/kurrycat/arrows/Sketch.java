package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.Game;
import io.github.kurrycat.arrows.Screens.Menu;
import processing.core.PApplet;
import processing.event.KeyEvent;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Sketch class that extends {@link PApplet} so that it can be executed as a processing sketch.
 */
public class Sketch extends PApplet {
	public static Sketch p = new Sketch();
	public static int windowWidth;
	public static int windowHeight;

	public static JFrame jframe;

	/**
	 * Gets executed once for window creation.
	 */
	public void settings() {
		ConfigHandler.init();
		ConfigHandler.loadConfig();
		ScreenHandler.loadAllScreens();

		size(displayWidth, displayHeight);

		windowWidth = width;
		windowHeight = height;

		Box.BOX_SIZE = (short) (windowWidth / 25);
		registerMethod("pre", this);
	}

	/**
	 * Gets executed once.<br>
	 * Initialization of jframe var and update threads.
	 */
	public void setup() {
		jframe = (javax.swing.JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();

		jframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window Closed");
				ConfigHandler.saveConfig();
			}
		});

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(true);
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frameRate(360);
		textAlign(CENTER, CENTER);

		ScreenHandler.pushScreen(Menu.instance, false);

		KEYS.init();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				ScreenHandler.getCurrentScreen().update();
				ScreenHandler.update();
			}
		}, 0, 1000 / 60);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				ConfigHandler.saveConfig();
			}
		}, 0, 120_000 /* 2min */);
	}

	/**
	 * Utility function to open message popup window with the specified {@code message}
	 *
	 * @param message The message to be displayed.
	 */
	public static void message(String message) {
		JOptionPane.showMessageDialog(Sketch.jframe, message);
	}

	@Override
	protected void handleKeyEvent(KeyEvent event) {
		ScreenHandler.getCurrentScreen().handleKeyEvent(event);
		//prevent closing window with escape
		if (event.getAction() == 1 && event.getKey() == 27) return;
		super.handleKeyEvent(event);
	}

	/**
	 * Window resize handling
	 * Gets called before draw and calls {@link #onResize()} if window size changed.
	 */
	@SuppressWarnings("unused")
	public void pre() {
		if (windowWidth == width && windowHeight == height) return;
		windowWidth = width;
		windowHeight = height;

		onResize();
	}

	/**
	 * Resize event getting called in {@link #pre()} when window size changes.<br>
	 * Updates {@link Box#BOX_SIZE} and arrow shapes and calls {@link Screen#windowResized()} on every screen.
	 */
	public void onResize() {
		Box.BOX_SIZE = (short) (windowWidth / 25);
		KEYS.init();

		for (Screen s : Screen.screens) {
			s.windowResized();
		}
	}

	/**
	 * Game loop that gets executed every frame.<br>
	 * Just executes {@link ScreenHandler#drawCurrentScreen()}.
	 */
	public void draw() {
		ScreenHandler.drawCurrentScreen();
	}

	/**
	 * Draws the current FPS on screen.
	 */
	public void showFPS() {
		Sketch.p.fill(255);
		Sketch.p.textFont(Game.scoreFont);
		Sketch.p.textSize(Box.BOX_SIZE / 3f);
		Sketch.p.text(Sketch.p.frameRate, Box.BOX_SIZE, Box.BOX_SIZE / 3f);
	}

	/**
	 * Mouse pressed event.
	 */
	public void mousePressed() {

	}

	/**
	 * Key pressed event that updates the {@link KEYS#keysPressed} arraylist and the {@link KEYS#nextMoveKeys} arraylist.
	 */
	public void keyPressed() {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (k != null) {
			if (!KEYS.keysPressed.contains(k))
				KEYS.keysPressed.add(k);
			if (!KEYS.nextMoveKeys.contains(k))
				KEYS.nextMoveKeys.add(k);
		}
	}

	/**
	 * Key released event that updates the {@link KEYS#keysPressed} arraylist and the {@link KEYS#nextMoveKeys} arraylist.
	 */
	public void keyReleased() {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (k != null)
			KEYS.keysPressed.remove(k);
	}

	/**
	 * Main method just executes the Sketch.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String[] processingArgs = {"io.github.kurrycat.arrows.Sketch"};
		PApplet.runSketch(processingArgs, p);
	}
}


	
