package io.github.kurrycat.arrows;

import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

public enum KEYS {
	ARROW_UP(new int[]{38}, new PVector(0f, -1f)),
	ARROW_DOWN(new int[]{40}, new PVector(0f, 1f)),
	ARROW_LEFT(new int[]{37}, new PVector(-1f, 0f)),
	ARROW_RIGHT(new int[]{39}, new PVector(1f, 0f)),
	ARROW_UP_RIGHT(new int[]{38, 39}, new PVector(1f, -1f)),
	ARROW_UP_LEFT(new int[]{38, 37}, new PVector(-1f, -1f)),
	ARROW_DOWN_RIGHT(new int[]{40, 39}, new PVector(1f, 1f)),
	ARROW_DOWN_LEFT(new int[]{40, 37}, new PVector(-1f, 1f));

	public static final ArrayList<KEYS> keysPressed = new ArrayList<>();
	public static final ArrayList<KEYS> nextMoveKeys = new ArrayList<>();

	public final int[] keyCode;
	public final PVector dir;
	public PShape shape;

	KEYS(int[] keyCode, PVector dir) {
		this.keyCode = keyCode;
		this.dir = dir;
	}

	public static void init() {
		for (KEYS k : KEYS.values()) {
			k.shape = k.getShape();
		}
	}

	private PShape getShape() {
		float ARROW_SIZE = Box.BOX_SIZE / 100f;

		float arrW = 5f * ARROW_SIZE;
		float arrH = 25f * ARROW_SIZE;

		float arrT = 45f * ARROW_SIZE;
		float arrR = 20f * ARROW_SIZE;

		//BODY
		PShape body = Sketch.p.createShape();
		body.beginShape();
		body.fill(255, 0, 0);
		body.noStroke();

		body.translate(0, 10f * ARROW_SIZE - 1);

		body.vertex(-arrW, -arrH);
		body.vertex(-arrW, arrH);
		body.vertex(arrW, arrH);
		body.vertex(arrW, -arrH);

		body.endShape(PConstants.CLOSE);

		//ARROW
		PShape arrow = Sketch.p.createShape();
		arrow.beginShape();
		arrow.fill(255, 0, 0);
		arrow.noStroke();

		arrow.translate(0, 10f * ARROW_SIZE);

		arrow.vertex(arrR, -arrH);
		arrow.vertex(0, -arrT);
		arrow.vertex(-arrR, -arrH);

		arrow.endShape(PConstants.CLOSE);

		PShape s = Sketch.p.createShape(PConstants.GROUP);
		s.addChild(arrow);
		s.addChild(body);

		if (Math.abs(dir.x) == 1 && Math.abs(dir.y) == 1) {
			s.rotate(dir.x * (dir.y + 2) * PConstants.QUARTER_PI);
		} else {
			if (this.dir.y == 0) s.rotate(-PConstants.HALF_PI);
			if (this.dir.x + this.dir.y > 0) s.rotate(PConstants.PI);
		}

		return s;
	}

	public static KEYS fromKeyCode(int keyCode) {
		return fromKeyCode(new int[]{keyCode});
	}

	public static KEYS fromKeyCode(int[] keyCode) {
		out:
		for (KEYS k : values()) {
			if (keyCode.length != k.keyCode.length) continue;
			for (int i = 0; i < keyCode.length; i++) {
				if (keyCode[i] != k.keyCode[i]) continue out;
			}
			return k;
		}
		return null;
	}

	public static KEYS getKeyPressed() {
		ArrayList<KEYS> nextKeys = new ArrayList<>(nextMoveKeys);
		if (nextKeys.isEmpty()) return null;

		int up = nextKeys.contains(ARROW_UP) ? 1 : 0,
				down = nextKeys.contains(ARROW_DOWN) ? 1 : 0,
				left = nextKeys.contains(ARROW_LEFT) ? 1 : 0,
				right = nextKeys.contains(ARROW_RIGHT) ? 1 : 0;

		PVector dir = new PVector(
				right - left,
				down - up
		);

		for (KEYS k : KEYS.values()) {
			if (k.dir.equals(dir)) return k;
		}
		return null;
	}

	public static KEYS random() {
		KEYS[] keys = values();
		int index = (int) (Math.random() * keys.length);
		return keys[index];
	}

	public static KEYS randomStraight() {
		KEYS[] keys = new KEYS[]{ARROW_UP, ARROW_DOWN, ARROW_LEFT, ARROW_RIGHT};
		int index = (int) (Math.random() * keys.length);
		return keys[index];
	}

	public static KEYS randomDiagonal() {
		KEYS[] keys = new KEYS[]{ARROW_UP_LEFT, ARROW_UP_RIGHT, ARROW_DOWN_LEFT, ARROW_DOWN_RIGHT};
		int index = (int) (Math.random() * keys.length);
		return keys[index];
	}
}