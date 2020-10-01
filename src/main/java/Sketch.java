import processing.core.PApplet;

import java.util.ArrayList;

public class Sketch extends PApplet {
	public static Sketch p = new Sketch();

	public static final short BOX_SIZE = 100;

	public static float boxSpeed = 2f;
	public static ArrayList<Box> boxes = new ArrayList<>();

	public void settings() {
		fullScreen();
	}

	public void draw() {
		background(0, 0, 0);

		if (boxes.size() > 0 && boxes.get(0).getArr() == Keys.getKeyPressed()) {
			boxes.remove(0);
			Keys.ARROW_UP = false;
			Keys.ARROW_DOWN = false;
			Keys.ARROW_LEFT = false;
			Keys.ARROW_RIGHT = false;
		}

		for (Box b : boxes) {
			b.update();
			b.show();
		}

		for (int i = boxes.size() - 1; i >= 0; i--) {
			if (!boxes.get(i).in(-BOX_SIZE, -BOX_SIZE, width + BOX_SIZE, height + BOX_SIZE)) {
				boxes.remove(i);
			}
		}
	}

	public void mousePressed() {
		boxes.add(new Box());
	}

	public void keyPressed() {
		if (keyCode == Keys.keys.ARROW_UP.keyCode) Keys.ARROW_UP = true;
		else if (keyCode == Keys.keys.ARROW_DOWN.keyCode) Keys.ARROW_DOWN = true;
		else if (keyCode == Keys.keys.ARROW_LEFT.keyCode) Keys.ARROW_LEFT = true;
		else if (keyCode == Keys.keys.ARROW_RIGHT.keyCode) Keys.ARROW_RIGHT = true;
	}

	public void keyReleased() {
		if (keyCode == Keys.keys.ARROW_UP.keyCode) Keys.ARROW_UP = false;
		else if (keyCode == Keys.keys.ARROW_DOWN.keyCode) Keys.ARROW_DOWN = false;
		else if (keyCode == Keys.keys.ARROW_LEFT.keyCode) Keys.ARROW_LEFT = false;
		else if (keyCode == Keys.keys.ARROW_RIGHT.keyCode) Keys.ARROW_RIGHT = false;
	}

	public static void main(String[] args) {
		String[] processingArgs = {"Sketch"};
		PApplet.runSketch(processingArgs, p);
	}
}

class Keys {
	public enum keys {
		ARROW_UP(38),
		ARROW_DOWN(40),
		ARROW_LEFT(37),
		ARROW_RIGHT(39);

		public int keyCode;

		keys(int keyCode) {
			this.keyCode = keyCode;
		}
	}

	public static boolean
			ARROW_UP = false,
			ARROW_DOWN = false,
			ARROW_LEFT = false,
			ARROW_RIGHT = false;

	public static Box.DIRS getKeyPressed() {
		if (ARROW_UP && !ARROW_DOWN && !ARROW_LEFT && !ARROW_RIGHT) return Box.DIRS.UP;
		if (!ARROW_UP && ARROW_DOWN && !ARROW_LEFT && !ARROW_RIGHT) return Box.DIRS.DOWN;
		if (!ARROW_UP && !ARROW_DOWN && ARROW_LEFT && !ARROW_RIGHT) return Box.DIRS.LEFT;
		if (!ARROW_UP && !ARROW_DOWN && !ARROW_LEFT && ARROW_RIGHT) return Box.DIRS.RIGHT;
		return null;
	}
}