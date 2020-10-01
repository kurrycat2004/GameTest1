import processing.core.PVector;

public class Box {
	public static Sketch p = Sketch.p;

	public enum DIRS {
		UP(new PVector(0, -1)),
		DOWN(new PVector(0, 1)),
		LEFT(new PVector(-1, 0)),
		RIGHT(new PVector(1, 0));

		public final PVector dir;

		DIRS(PVector dir) {
			this.dir = dir;
		}
	}

	private PVector pos;
	private PVector dir;
	private DIRS direction;
	private DIRS arr;

	public Box() {
		this(
				DIRS.values()[(int) p.random(0, 4)],
				DIRS.values()[(int) p.random(0, 4)]
		);
	}

	public Box(DIRS dir, DIRS arr) {
		this.direction = dir;
		this.pos = getStartingPosFromDir(dir);
		this.dir = dir.dir;
		this.arr = arr;
	}

	public void update() {
		this.pos.add(this.dir.copy().mult(Sketch.boxSpeed));
	}

	public void show() {
		p.fill(255);
		p.rect(this.pos.x, this.pos.y, Sketch.BOX_SIZE, Sketch.BOX_SIZE);

		p.fill(0);
		p.textSize(Sketch.BOX_SIZE / 3f);
		p.textAlign(p.CENTER, p.CENTER);
		p.stroke(0);
		p.text(this.arr.name(), this.pos.x + Sketch.BOX_SIZE / 2f, this.pos.y + Sketch.BOX_SIZE / 2f);
	}


	public PVector getPos() {
		return pos;
	}

	public PVector getDir() {
		return dir;
	}

	public DIRS getArr() {
		return arr;
	}

	public DIRS getDirection() {
		return direction;
	}

	public boolean in(PVector pos, PVector size) {
		return this.pos.x >= pos.x && this.pos.x <= pos.x + size.x &&
				this.pos.y >= pos.y && this.pos.y <= pos.y + size.y;
	}

	public boolean in(int posX, int posY, int sizeX, int sizeY) {
		return in(new PVector(posX, posY), new PVector(sizeX, sizeY));
	}

	public static PVector getStartingPosFromDir(DIRS dir) {
		return new PVector(
				(p.width / 2f - Sketch.BOX_SIZE / 2f) - ((p.width / 2f + Sketch.BOX_SIZE / 2f) * dir.dir.x),
				(p.height / 2f - Sketch.BOX_SIZE / 2f) - ((p.height / 2f + Sketch.BOX_SIZE / 2f) * dir.dir.y)
		);
	}
}
