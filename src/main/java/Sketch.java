import processing.core.PApplet;
import processing.core.PConstants;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Sketch extends PApplet {
	public static final Sketch p = new Sketch();
	public static int windowWidth;
	public static int windowHeight;

	public static Screen currentScreen;

	public void settings() {
		//fullScreen();
		size(displayWidth, displayHeight);

		windowWidth = width;
		windowHeight = height;

		Box.BOX_SIZE = (short) (windowWidth / 25);
		registerMethod("pre", this);
	}

	public void setup() {
	    ConfigHandler.init();
	    ConfigHandler.loadConfig();


		frameRate(60);
		textAlign(CENTER, CENTER);

		currentScreen = Menu.instance;

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				currentScreen.update();
			}
		}, 0, 1000 / 60);

		surface.setResizable(true);
		surface.setLocation(0, 0);
		javax.swing.JFrame jframe = (javax.swing.JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
		jframe.setExtendedState(jframe.getExtendedState() | Frame.MAXIMIZED_BOTH);

		textAlign(PConstants.CENTER, PConstants.CENTER);

		KEYS.init();
	}

	// window resize handling
	@SuppressWarnings("unused")
	public void pre() {
		if (windowWidth == width && windowHeight == height) return;
		windowWidth = width;
		windowHeight = height;

		Box.BOX_SIZE = (short) (windowWidth / 25);
		KEYS.init();
	}

	public void draw() {
		currentScreen.draw();
	}

	public void mousePressed() {
		//Box.spawn();
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
		String[] processingArgs = {"Sketch"};
		PApplet.runSketch(processingArgs, p);
	}
}


	
