package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

/**
 * Game screen extends {@link Screen}
 */
public class Game extends Screen {
	/**
	 * Screen instance
	 */
	public static final Game instance = new Game();

	/**
	 * Enum containing the different Game modes with every game mode having a different {@link Modes#boxAcc} and {@link Modes#spawnBox}
	 */
	public enum Modes {
		/**
		 * Easy Mode
		 */
		EASY(0.01, Box::spawnStraight),
		/**
		 * Normal Mode
		 */
		NORMAL(0.02, Box::spawn),
		/**
		 * Hard Mode
		 */
		HARD(0.04, Box::spawn);

		/**
		 * The method that is executed to spawn a new {@link Box}
		 */
		public Runnable spawnBox;
		/**
		 * The amount {@link Box#boxSpeed} should be increased every time a new {@link Box} spawns
		 */
		public double boxAcc;

		/**
		 * Enum constructor
		 *
		 * @param boxAcc   amount {@link Box#boxSpeed} should be increased every time a new {@link Box} spawns
		 * @param spawnBox method that is executed to spawn a new {@link Box}
		 */
		Modes(double boxAcc, Runnable spawnBox) {
			this.boxAcc = boxAcc;
			this.spawnBox = spawnBox;
		}
	}

	static {
		screens.add(instance);
	}

	/**
	 * Score {@link PFont} instance
	 */
	public static final PFont scoreFont = new PFont(PFont.findFont("DejaVu Sans Mono"), true);

	/**
	 * Current score
	 */
	public int score = 0;
	/**
	 * Round highscore
	 */
	public int roundHighscore = 0;

	/**
	 * Boolean indicating whether the game is paused
	 */
	public boolean paused = false;

	/**
	 * First/green {@link Box}
	 */
	public Box first;
	/**
	 * {@link Thread} spawning a box every {@code 1000/}{@link Box#boxSpeed} ms
	 */
	public Thread spawnBoxThread;

	/**
	 * Key event handler pausing the game if the user presses escape
	 *
	 * @param e The KeyEvent
	 */
	@Override
	public void handleKeyEvent(KeyEvent e) {
		if (e.getAction() == 1 && e.getKey() == 27) {
			paused = true;
			ScreenHandler.pushScreen(Pause.instance);
		}
	}

	/**
	 * Calls {@link #reset()} and {@link #spawnBoxThread start()}
	 */
	public void start() {
		reset();
		spawnBoxThread.start();
	}

	/**
	 * Updates all {@link Box#boxes} and the highscores and removes all {@link Box#boxes} off screen
	 */
	public void update() {
		if (score <= roundHighscore - 5) {
			spawnBoxThread = null;
			ScreenHandler.pushScreen(GameOver.instance);
		}

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

	/**
	 * Calls {@link ScreenHandler#drawBackground(int color)}, draws all {@link Box#boxes} and draws {@link #score} on screen
	 */
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

	/**
	 * Creates a new {@link #spawnBoxThread} that overwrites the old one if there is any
	 */
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

	/**
	 * Resets the Game screen
	 */
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

	/**
	 * Empty window resized event handler
	 */
	@Override
	public void windowResized() {

	}

	/**
	 * Empty init event handler
	 */
	@Override
	public void init() {

	}

	/**
	 * Empty keyPressed event handler
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyPressed(int keyCode) {
		KEYS k = KEYS.fromKeyCode(keyCode);
		if (!KEYS.keysPressed.contains(k))
			KEYS.keysPressed.add(k);
		if (!KEYS.nextMoveKeys.contains(k))
			KEYS.nextMoveKeys.add(k);
	}

	/**
	 * Empty keyReleased event handler
	 *
	 * @param keyCode keyCode of the key that got pressed
	 */
	public void keyReleased(int keyCode) {
		KEYS k = KEYS.fromKeyCode(keyCode);
		KEYS.keysPressed.remove(k);
	}
}
