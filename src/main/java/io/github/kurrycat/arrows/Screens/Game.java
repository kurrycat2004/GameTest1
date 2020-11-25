package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

public class Game extends Screen {
	public static final Game instance = new Game();

	public Modes currentMode = Modes.NORMAL;

	public enum Modes {
		EASY(0.01, Box::spawnStraight),
		NORMAL(0.02, Box::spawn),
		HARD(0.04, Box::spawn);

		public Runnable spawnBox;
		public double boxAcc;

		Modes(double boxAcc, Runnable spawnBox) {
			this.boxAcc = boxAcc;
			this.spawnBox = spawnBox;
		}
	}

	static {
		screens.add(instance);
	}

	public static final PFont scoreFont = new PFont(PFont.findFont("DejaVu Sans Mono"), true);

	public int score = 0;
	public int highscore = 0;
	public int roundHighscore = 0;

	public Box first;
	public Thread spawnBoxThread;

	Game() {

	}

	@Override
	public void handleKeyEvent(KeyEvent e) {

	}

	public void start() {
		reset();
		spawnBoxThread.start();
	}

	public void update() {
		if (score <= roundHighscore - 5) {
			stop();
			ScreenHandler.pushScreen(GameOver.instance);
		}
		//ArrowKeys

		if (first == null || first.isDisabled()) first = Box.getFirstBox();

		if (first != null) {
			KEYS keysPressed = KEYS.getKeyPressed();
			if (keysPressed != null && KEYS.keysPressed.isEmpty()) {
				if (first.getArr() == keysPressed) {
					first.setDisabled(true);
					score++;
				} else {
					score--;
				}
				KEYS.nextMoveKeys.clear();
			} else if (keysPressed == null && KEYS.keysPressed.isEmpty()) {
				KEYS.nextMoveKeys.clear();
			}
		}

		highscore = PApplet.max(score, highscore);
		roundHighscore = PApplet.max(score, roundHighscore);

		for (int i = 0; i < Box.boxes.size(); i++) {
			Box.boxes.get(i).update();
		}

		for (int i = Box.boxes.size() - 1; i >= 0; i--) {
			if (!Box.boxes.get(i).onScreen()) {
				if (!Box.boxes.get(i).isDisabled())
					score--;
				Box removed = Box.boxes.remove(i);
				if (first == removed) first = null;
			}
		}
	}

	public void draw() {
		ScreenHandler.drawBackground(0);

		for (int i = 0; i < Box.boxes.size(); i++) {
			Box.boxes.get(i).show(Box.boxes.get(i) == first);
		}

		Sketch.p.fill(255);

		Sketch.p.textFont(scoreFont);

		Sketch.p.textSize(Box.BOX_SIZE / 3f);

		if (Settings.showFPS)
			Sketch.p.showFPS();

		Sketch.p.text(Sketch.p.frameRate, Box.BOX_SIZE, Box.BOX_SIZE / 3f);
		Sketch.p.text(Box.boxSpeed, Box.BOX_SIZE, Box.BOX_SIZE);

		Sketch.p.textSize(Box.BOX_SIZE / 2f);
		Sketch.p.text(score, Sketch.p.width / 2f, Box.BOX_SIZE);

		Sketch.p.fill(100, 255, 100);
		Sketch.p.textSize(Box.BOX_SIZE / 3f);
		Sketch.p.text(highscore, Sketch.p.width / 2f, Box.BOX_SIZE / 2f);
	}

	public void stop() {
		if (spawnBoxThread != null)
			spawnBoxThread.interrupt();
		spawnBoxThread = null;
	}

	public void reset() {
		stop();
		spawnBoxThread = new Thread(() -> {
			while (spawnBoxThread != null && !spawnBoxThread.isInterrupted()) {
				currentMode.spawnBox.run();
				Box.boxSpeed += currentMode.boxAcc;
				try {
					Thread.sleep((long) (1000 / Box.boxSpeed));
				} catch (InterruptedException ignored) {
				}
			}
		});
		first = null;
		Box.boxes.clear();
		score = 0;
		roundHighscore = 0;
		Box.boxSpeed = 1f;
		KEYS.nextMoveKeys.clear();
	}

	@Override
	public void windowResized() {

	}

	@Override
	public void init() {

	}

	public void keyPressed(int keyCode) {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (!KEYS.keysPressed.contains(k))
			KEYS.keysPressed.add(k);
		if (!KEYS.nextMoveKeys.contains(k))
			KEYS.nextMoveKeys.add(k);
	}

	public void keyReleased(int keyCode) {
		KEYS k = KEYS.fromKeyCode(keyCode);
		KEYS.keysPressed.remove(k);
	}
}
