package io.github.kurrycat.arrows;

import io.github.kurrycat.arrows.Screens.Menu;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.event.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Sketch extends PApplet {
	public static final Sketch p = new Sketch();
	public static int windowWidth;
	public static int windowHeight;

	public static JFrame jframe;

	public static int font = 0;

	//TODO: fullscreen broken (black bar)

	public void settings() {
		ConfigHandler.init();
		ConfigHandler.loadConfig();
		ScreenHandler.loadAllScreens();

		/*if (Settings.fullscreen) fullScreen();
		else*/
		size(displayWidth, displayHeight);

		windowWidth = width;
		windowHeight = height;

		Box.BOX_SIZE = (short) (windowWidth / 25);
		registerMethod("pre", this);
	}

	public void setup() {
		jframe = (javax.swing.JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();

		jframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window Closed");
				ConfigHandler.saveConfig();
			}
		});

		width = jframe.getWidth();
		height = jframe.getHeight();

		updateFullscreen();

		System.out.println(Arrays.toString(PFont.list()));

		frameRate(60);
		textAlign(CENTER, CENTER);

		ScreenHandler.pushScreen(Menu.instance);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				ScreenHandler.getCurrentScreen().update();
			}
		}, 0, 1000 / 60);


		textAlign(PConstants.CENTER, PConstants.CENTER);

		KEYS.init();
	}

	public void updateFullscreen() {
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		if (!Settings.fullscreen) {
			device.setFullScreenWindow(null);
			jframe.dispose();
			jframe.setUndecorated(Settings.fullscreen);
			jframe.setResizable(true);
			jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			jframe.dispose();
			jframe.setUndecorated(Settings.fullscreen);
			jframe.setResizable(false);
			device.setFullScreenWindow(jframe);
		}
		jframe.setVisible(true);
		onResize();

		width = jframe.getWidth();
		height = jframe.getHeight();
		/*jframe.setResizable(!Settings.fullscreen);
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);*/
	}

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

	// window resize handling
	@SuppressWarnings("unused")
	public void pre() {
		if (windowWidth == jframe.getWidth() && windowHeight == jframe.getHeight()) return;
		windowWidth = jframe.getWidth();
		windowHeight = jframe.getHeight();

		onResize();
	}

	public void onResize() {
		Box.BOX_SIZE = (short) (windowWidth / 25);
		KEYS.init();

		for (Screen s : Screen.screens) {
			s.windowResized();
		}
	}

	public void draw() {
		ScreenHandler.getCurrentScreen().draw();
	}

	public void mousePressed() {
		/*font++;
		System.out.println(PFont.list()[Sketch.font % PFont.list().length]);*/
		//io.github.kurrycat.arrows.Box.spawn();
	}


	public void keyPressed() {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (k != null) {
			if (!KEYS.keysPressed.contains(k))
				KEYS.keysPressed.add(k);
			if (!KEYS.nextMoveKeys.contains(k))
				KEYS.nextMoveKeys.add(k);
		}
	}

	public void keyReleased() {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (k != null)
			KEYS.keysPressed.remove(k);
	}

	public static void main(String[] args) {
		String[] processingArgs = {"io.github.kurrycat.arrows.Sketch"};
		PApplet.runSketch(processingArgs, p);
	}
}


	
