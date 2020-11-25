package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

public class Game extends Screen {
	public static final Game instance = new Game();

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
	public int roundHighscore = 0;

	public boolean paused = false;

	public Box first;
	public Thread spawnBoxThread;

	@Override
	public void handleKeyEvent(KeyEvent e) {
		if (e.getAction() == 1 && e.getKey() == 27) {
			paused = true;
			ScreenHandler.pushScreen(Pause.instance);
		}
	}

	public void start() {
		reset();
		spawnBoxThread.start();
	}

	public void update() {
		if (score <= roundHighscore - 5) {
			spawnBoxThread = null;
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

		if (Settings.currentMode == Modes.EASY)
			Settings.easyHighscore = PApplet.max(score, Settings.easyHighscore);
		else if (Settings.currentMode == Modes.NORMAL)
			Settings.normalHighscore = PApplet.max(score, Settings.normalHighscore);
		else if (Settings.currentMode == Modes.HARD)
			Settings.hardHighscore = PApplet.max(score, Settings.hardHighscore);

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
		int highscore = 0;
		if (Settings.currentMode == Modes.EASY) highscore = Settings.easyHighscore;
		else if (Settings.currentMode == Modes.NORMAL) highscore = Settings.normalHighscore;
		else if (Settings.currentMode == Modes.HARD) highscore = Settings.hardHighscore;

		ScreenHandler.drawBackground(0);

		for (int i = 0; i < Box.boxes.size(); i++) {
			Box.boxes.get(i).show(Box.boxes.get(i) == first);
		}

		Sketch.p.fill(255);

		Sketch.p.textFont(scoreFont);
		Sketch.p.textSize(Box.BOX_SIZE / 3f);

		Sketch.p.textSize(Box.BOX_SIZE / 2f);
		Sketch.p.text(score, Sketch.p.width / 2f, Box.BOX_SIZE);

		Sketch.p.fill(100, 255, 100);
		Sketch.p.textSize(Box.BOX_SIZE / 3f);
		Sketch.p.text(highscore, Sketch.p.width / 2f, Box.BOX_SIZE / 2f);

		if (Settings.showBoxSpeed) {
			Sketch.p.textFont(scoreFont);
			Sketch.p.textSize(Box.BOX_SIZE / 3f);
			Sketch.p.text(Box.boxSpeed, Box.BOX_SIZE, Box.BOX_SIZE);
		}
	}

	public void newSpawnBoxThread() {
		spawnBoxThread = new Thread() {
			@Override
			public void run() {
				while (spawnBoxThread != null && getId() == spawnBoxThread.getId()) {
					if (paused) {
						try {
							Thread.sleep((long) (1000 / Box.boxSpeed));
						} catch (InterruptedException ignored) {
						}
						continue;
					}
					Settings.currentMode.spawnBox.run();
					Box.boxSpeed += Settings.currentMode.boxAcc;
					try {
						Thread.sleep((long) (1000 / Box.boxSpeed));
					} catch (InterruptedException ignored) {
					}
				}
				System.out.println("Finished Thread: " + getId());
			}
		};
	}

	public void reset() {
		paused = false;
		newSpawnBoxThread();
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
