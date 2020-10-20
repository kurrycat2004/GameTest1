import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class Sketch extends PApplet {
	public static Sketch p = new Sketch();

	public static short BOX_SIZE;

	public static float boxSpeed = 2f;
	public static int score = 0;
	public static int highscore = 0;
	public static int frame = 0;
	public static ArrayList<Box> boxes = new ArrayList<>();

	public void settings() {
		fullScreen();
		BOX_SIZE = (short) (displayWidth / 25);
	}

	public void setup() {
		textAlign(PConstants.CENTER, PConstants.CENTER);
		textSize(Sketch.BOX_SIZE / 3f);

		KEYS.init();

		/*boxes.add(new Box(KEYS.ARROW_UP, KEYS.ARROW_UP, new PVector(width / 2f + width / 4f, height / 2f + height / 4f)));
		boxes.add(new Box(KEYS.ARROW_UP, KEYS.ARROW_DOWN, new PVector(width / 4f, height / 2f + height / 4f)));
		boxes.add(new Box(KEYS.ARROW_UP, KEYS.ARROW_LEFT, new PVector(width / 2f + width / 4f, height / 4f)));
		boxes.add(new Box(KEYS.ARROW_UP, KEYS.ARROW_RIGHT, new PVector(width / 4f, height / 4f)));*/
	}

	public void draw() {
		frame++;
		background(0, 0, 0);
		strokeWeight(0.2f);
		stroke(255);
		noFill();
		for (int i = 0; i < width / BOX_SIZE; i++) {
			for (int j = 0; j < height / BOX_SIZE; j++) {
				rect(i * BOX_SIZE, j * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}
		//boxSpeed += 0.01;
		if (frame % floor(BOX_SIZE / boxSpeed) == 0) {
			Box.spawn();
			boxSpeed += 0.02;
			frame -= floor(BOX_SIZE / boxSpeed);
		}

		if (boxes.size() > 0) {
			if (boxes.get(0).getArr() == KEYS.getKeyPressed()) {
				boxes.remove(0);
				score++;
			} else if (KEYS.getKeyPressed() != null) {
				score--;
			}
			KEYS.resetPress();
		}


		for (Box b : boxes) {
			b.update();
			b.show(b == boxes.get(0));
		}

		for (int i = boxes.size() - 1; i >= 0; i--) {
			if (!boxes.get(i).in(-BOX_SIZE, -BOX_SIZE, width + BOX_SIZE, height + BOX_SIZE)) {
				boxes.remove(i);
				score--;
			}
		}

		highscore = max(score, highscore);

		fill(255);
		textSize(Sketch.BOX_SIZE / 3f);
		text(frameRate, Sketch.BOX_SIZE, Sketch.BOX_SIZE / 3f);
		text(boxSpeed, Sketch.BOX_SIZE, Sketch.BOX_SIZE);

		fill(255);
		textSize(Sketch.BOX_SIZE / 2f);
		text(score, width / 2f, Sketch.BOX_SIZE);
		fill(100, 255, 100);
		textSize(Sketch.BOX_SIZE / 3f);
		text(highscore, width / 2f, Sketch.BOX_SIZE / 2f);
	}

	public void mousePressed() {
		//Box.spawn();
	}

	public void keyPressed() {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (k != null) {
			k.pressed = true;
		}
	}

	public void keyReleased() {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (k != null) {
			k.pressed = false;
		}
	}

	public static void main(String[] args) {
		String[] processingArgs = {"Sketch"};
		PApplet.runSketch(processingArgs, p);
	}
}


	
