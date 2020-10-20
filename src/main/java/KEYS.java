import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public enum KEYS {
	ARROW_UP(38, new PVector(0f, -1f)),

	ARROW_DOWN(40, new PVector(0f, 1f)),

	ARROW_LEFT(37, new PVector(-1f, 0f)),

	ARROW_RIGHT(39, new PVector(1f, 0f));

	public static Sketch p = Sketch.p;
	public static float ARROW_SIZE = Sketch.BOX_SIZE / 100f;

	public int keyCode;
	public PVector dir;
	public PShape shape;
	public boolean pressed = false;

	KEYS(int keyCode, PVector dir) {
		this.keyCode = keyCode;
		this.dir = dir;
	}

	public static void init() {
		for (KEYS k : KEYS.values()) {
			k.shape = k.getShape();
		}
	}

	private PShape getShape() {
		float arrW = 5f * ARROW_SIZE;
		float arrH = 25f * ARROW_SIZE;

		float arrT = 45f * ARROW_SIZE;
		float arrR = 20f * ARROW_SIZE;

		//BODY
		PShape body = p.createShape();
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
		PShape arrow = p.createShape();
		arrow.beginShape();
		arrow.fill(255, 0, 0);
		arrow.noStroke();

		arrow.translate(0, 10f * ARROW_SIZE);

		arrow.vertex(arrR, -arrH);
		arrow.vertex(0, -arrT);
		arrow.vertex(-arrR, -arrH);

		arrow.endShape(PConstants.CLOSE);

		PShape s = p.createShape(PConstants.GROUP);
		s.addChild(arrow);
		s.addChild(body);

		if (this.dir.y == 0) s.rotate(-PConstants.HALF_PI);
		if (this.dir.x + this.dir.y > 0) s.rotate(PConstants.PI);

		return s;
	}

	public String getName() {
		return this.name().split("_")[1];
	}

	public static KEYS fromKeyCode(int keyCode) {
		for (KEYS k : values())
			if (k.keyCode == keyCode)
				return k;
		return null;
	}

	public static void resetPress() {
		ARROW_UP.pressed = false;
		ARROW_DOWN.pressed = false;
		ARROW_LEFT.pressed = false;
		ARROW_RIGHT.pressed = false;
	}

	public static KEYS getKeyPressed() {
		if (ARROW_UP.pressed && !ARROW_DOWN.pressed && !ARROW_LEFT.pressed && !ARROW_RIGHT.pressed)
			return ARROW_UP;
		if (!ARROW_UP.pressed && ARROW_DOWN.pressed && !ARROW_LEFT.pressed && !ARROW_RIGHT.pressed)
			return ARROW_DOWN;
		if (!ARROW_UP.pressed && !ARROW_DOWN.pressed && ARROW_LEFT.pressed && !ARROW_RIGHT.pressed)
			return ARROW_LEFT;
		if (!ARROW_UP.pressed && !ARROW_DOWN.pressed && !ARROW_LEFT.pressed && ARROW_RIGHT.pressed)
			return ARROW_RIGHT;
		return null;
	}
}