package io.github.kurrycat.arrows.Screens;

import io.github.kurrycat.arrows.*;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Help extends Screen {
	public static final Help instance = new Help();

	private final Button menu = new Button("Menu", 0, 0, 160, 50).copyDesign(Menu.mainDesign);
	private final Button startGame = new Button("Start Game", 0, 0, 160, 50).copyDesign(Menu.mainDesign);

	private final Button arrowUp = new Button(0, 0, 0, 0)
			                               .setHoverMethod(() -> KEYS.keysPressed.contains(KEYS.ARROW_UP))
			                               .copyDesign(Menu.mainDesign);
	private final Button arrowDown = new Button(0, 0, 0, 0)
			                                 .setHoverMethod(() -> KEYS.keysPressed.contains(KEYS.ARROW_DOWN))
			                                 .copyDesign(Menu.mainDesign);
	private final Button arrowLeft = new Button(0, 0, 0, 0)
			                                 .setHoverMethod(() -> KEYS.keysPressed.contains(KEYS.ARROW_LEFT))
			                                 .copyDesign(Menu.mainDesign);
	private final Button arrowRight = new Button(0, 0, 0, 0)
			                                  .setHoverMethod(() -> KEYS.keysPressed.contains(KEYS.ARROW_RIGHT))
			                                  .copyDesign(Menu.mainDesign);

	private final Button currentArrowButton = new Button(0, 0, 0, 0)
			                                          .setHoverMethod(() -> false)
			                                          .copyDesign(Menu.mainDesign)
			                                          .setBgColor(Sketch.p.color(50, 200, 50));

	private final ArrayList<Box> arrows = new ArrayList<>();
	private final ArrayList<OffsetDateTime> times = new ArrayList<>();

	private static final int highlightColor = 180;

	static {
		screens.add(instance);
	}

	public Help() {
		buttonList.add(menu);
		menu.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Menu.instance);
		});

		buttonList.add(startGame);
		startGame.setClickedCallback(() -> {
			ScreenHandler.pushScreen(Game.instance);
			Game.instance.start();
		});
	}

	public void windowResized() {
		menu.setMiddleOffsetPos(0, Sketch.p.height / 4);
		startGame.setMiddleOffsetPos(0, Sketch.p.height / 4 + 60);

		int topYOffset = Box.BOX_SIZE;

		arrowUp.setShape(KEYS.ARROW_UP.shape)
				.setSize(Box.BOX_SIZE, Box.BOX_SIZE)
				.setMiddleOffsetPos(0, topYOffset);
		arrowDown.setShape(KEYS.ARROW_DOWN.shape)
				.setSize(Box.BOX_SIZE, Box.BOX_SIZE)
				.setMiddleOffsetPos(0, topYOffset + Box.BOX_SIZE + 5);
		arrowLeft.setShape(KEYS.ARROW_LEFT.shape)
				.setSize(Box.BOX_SIZE, Box.BOX_SIZE)
				.setMiddleOffsetPos(-(Box.BOX_SIZE + 5), topYOffset + Box.BOX_SIZE + 5);
		arrowRight.setShape(KEYS.ARROW_RIGHT.shape)
				.setSize(Box.BOX_SIZE, Box.BOX_SIZE)
				.setMiddleOffsetPos(Box.BOX_SIZE + 5, topYOffset + Box.BOX_SIZE + 5);

		currentArrowButton
				.setSize(Box.BOX_SIZE, Box.BOX_SIZE)
				.setMiddleOffsetPos(0, topYOffset - Box.BOX_SIZE * 2);
		if (arrows.size() > 0) {
			currentArrowButton.setShape(arrows.get(arrows.size() - 1).getArr().shape);
			arrows.get(arrows.size() - 1).setPos(currentArrowButton.getPos());
		}
	}

	public void init() {
		arrows.clear();
		times.clear();
		newArrow();
	}

	public void newArrow() {
		KEYS newKey = KEYS.random();
		Box newArrow = new Box(newKey, newKey, currentArrowButton.getPos()) {
			@Override
			public void update() {
				if (disabled) this.pos.add(this.dir.copy().mult(20));
			}
		};
		currentArrowButton.setShape(newArrow.getArr().shape);
		arrows.add(newArrow);
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		ScreenHandler.drawBackground(0);
		ScreenHandler.drawArrows();

		for (int i = 0; i < arrows.size(); i++) arrows.get(i).show(i == arrows.size() - 1);

		Sketch.p.textFont(Game.scoreFont);
		Sketch.p.textSize(Box.BOX_SIZE / 4f);
		Sketch.p.fill(255);

		float xOff = Sketch.p.width / 2f;
		float yOff = Sketch.p.height / 2f - Box.BOX_SIZE * 6f;
		float lineHeight = Box.BOX_SIZE / 2f;

		double average = 0;
		ArrayList<Long> timeDiffs = getTimesDifferences();
		for (int i = 0; i < timeDiffs.size(); i++) {
			average += timeDiffs.get(i);

			float x = xOff + (i / 5 == 0 ? -Box.BOX_SIZE : Box.BOX_SIZE);
			float y = yOff + (i % 5) * lineHeight;
			Sketch.p.text(i + 1 + "." + timeDiffs.get(i).toString() + "ms", x, y);
		}

		average /= timeDiffs.size();
		if (timeDiffs.size() == 10)
			Settings.average = Math.min(Settings.average, average);

		String avg = timeDiffs.size() != 10 ? "Not enough times" : String.format(Locale.US, "%.1f", average) + "ms";

		Sketch.p.textSize(Box.BOX_SIZE / 3.5f);
		Sketch.p.text("Average of 10: " + avg, xOff, yOff + Box.BOX_SIZE * 3f);

		Sketch.p.textSize(Box.BOX_SIZE / 3.5f);
		Sketch.p.text("PB: " + String.format(Locale.US, "%.1f", Settings.average), xOff, yOff + Box.BOX_SIZE * 3.5f);


		KEYS currKey = arrows.get(arrows.size() - 1).getArr();
		List<Integer> keyCodes = Arrays.stream(currKey.keyCode).boxed().collect(Collectors.toList());

		if (keyCodes.contains(KEYS.ARROW_UP.keyCode[0])) arrowUp.setBgColor(highlightColor);
		else arrowUp.setBgColor(Menu.mainDesign.getBgColor());
		if (keyCodes.contains(KEYS.ARROW_DOWN.keyCode[0])) arrowDown.setBgColor(highlightColor);
		else arrowDown.setBgColor(Menu.mainDesign.getBgColor());
		if (keyCodes.contains(KEYS.ARROW_LEFT.keyCode[0])) arrowLeft.setBgColor(highlightColor);
		else arrowLeft.setBgColor(Menu.mainDesign.getBgColor());
		if (keyCodes.contains(KEYS.ARROW_RIGHT.keyCode[0])) arrowRight.setBgColor(highlightColor);
		else arrowRight.setBgColor(Menu.mainDesign.getBgColor());

		drawButtons();
		arrowUp.draw();
		arrowDown.draw();
		arrowLeft.draw();
		arrowRight.draw();

		currentArrowButton.draw();

		if (Settings.showFPS) Sketch.p.showFPS();
	}

	public void update() {
		updateButtons();
		ScreenHandler.updateArrows();

		KEYS keysPressed = KEYS.getKeyPressed();
		if (keysPressed != null && KEYS.keysPressed.isEmpty()) {
			if (arrows.get(arrows.size() - 1).getArr() == keysPressed) {
				arrows.get(arrows.size() - 1).setDisabled(true);
				newArrow();
				times.add(OffsetDateTime.now());
			}
			KEYS.nextMoveKeys.clear();
		} else if (keysPressed == null && KEYS.keysPressed.isEmpty()) {
			KEYS.nextMoveKeys.clear();
		}

		for (int i = arrows.size() - 1; i >= 0; i--) {
			arrows.get(i).update();
			if (!arrows.get(i).onScreen() && arrows.get(i).isDisabled())
				arrows.remove(i);
		}

		if (times.size() > 11)
			times.remove(0);
	}

	public ArrayList<Long> getTimesDifferences() {
		ArrayList<Long> timeDiffs = new ArrayList<>();

		if (times.size() <= 0) return timeDiffs;
		OffsetDateTime current = times.get(0);
		for (int i = 1; i < times.size(); i++) {
			timeDiffs.add(Duration.between(current, times.get(i)).toMillis());
			current = times.get(i);
		}

		return timeDiffs;
	}
}
