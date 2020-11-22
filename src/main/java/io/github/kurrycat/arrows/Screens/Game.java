package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.Box;
import io.github.kurrycat.arrows.KEYS;
import io.github.kurrycat.arrows.Screen;
import io.github.kurrycat.arrows.ScreenHandler;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

public class Game extends Screen {
	public static final Game instance = new Game();

	static {
		screens.add(instance);
	}

	public static final PFont scoreFont = new PFont(PFont.findFont("DejaVu Sans Mono"), true);

	public int score = 0;
	public int highscore = 0;
	public int roundHighscore = 0;
	public int frame = 0;

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
		if (score < roundHighscore - 10) {
			stop();
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
			}
		}

		highscore = PApplet.max(score, highscore);
		roundHighscore = PApplet.max(score, roundHighscore);

		for (int i = 0; i < Box.boxes.size(); i++) {
			Box.boxes.get(i).update();
		}

		for (int i = Box.boxes.size() - 1; i >= 0; i--) {
			if (!Box.boxes.get(i).in(-Box.BOX_SIZE, -Box.BOX_SIZE, p.width + Box.BOX_SIZE, p.height + Box.BOX_SIZE)) {
				if (!Box.boxes.get(i).isDisabled())
					score--;
				Box removed = Box.boxes.remove(i);
				if (first == removed) first = null;
			}
		}
	}

	public void draw() {
		frame++;
		p.background(0);
		p.strokeWeight(0.2f);
		p.stroke(255);
		p.noFill();

		/*for (int i = 0; i < p.width / Box.BOX_SIZE; i++) {
			for (int j = 0; j < p.height / Box.BOX_SIZE; j++) {
				p.rect(i * Box.BOX_SIZE, j * Box.BOX_SIZE, Box.BOX_SIZE, Box.BOX_SIZE);
			}
		}*/

		for (int i = 0; i < Box.boxes.size(); i++) {
			Box.boxes.get(i).show(Box.boxes.get(i) == first);
		}

		p.fill(255);

		p.textFont(scoreFont);

		p.textSize(Box.BOX_SIZE / 3f);
		p.text(p.frameRate, Box.BOX_SIZE, Box.BOX_SIZE / 3f);
		p.text(Box.boxSpeed, Box.BOX_SIZE, Box.BOX_SIZE);

		p.textSize(Box.BOX_SIZE / 2f);
		p.text(score, p.width / 2f, Box.BOX_SIZE);

		p.fill(100, 255, 100);
		p.textSize(Box.BOX_SIZE / 3f);
		p.text(highscore, p.width / 2f, Box.BOX_SIZE / 2f);
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
				Box.spawn();
				Box.boxSpeed += 0.02;
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
