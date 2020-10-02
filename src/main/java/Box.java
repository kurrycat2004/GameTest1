import processing.core.PVector;

public class Box {
	public static Sketch p = Sketch.p;

	private PVector pos;
	private final PVector dir;
	private final KEYS direction;
	private final KEYS arr;

	public Box() {
		this(KEYS.values()[1], KEYS.values()[(int) p.random(0, 4)]);
	}

	public Box(KEYS dir, KEYS arr) {
		this.direction = dir;
		this.pos = getStartingPosFromDir(dir);
		this.dir = dir.dir;
		this.arr = arr;
	}

	public Box(KEYS dir, KEYS arr, PVector pos) {
		this.direction = dir;
		this.pos = pos;
		this.dir = dir.dir;
		this.arr = arr;
	}

	public void update() {
		this.pos.add(this.dir.copy().mult(Sketch.boxSpeed));
	}

	public void show() {
		this.show(false);
	}

	public void show(boolean first) {
		if (first)
			p.fill(50, 200, 50);
		else
			p.fill(255);
		p.rect(this.pos.x, this.pos.y, Sketch.BOX_SIZE, Sketch.BOX_SIZE);

		p.shape(arr.shape, this.pos.x + Sketch.BOX_SIZE / 2f, this.pos.y + Sketch.BOX_SIZE / 2f);

		/*p.fill(0);
		p.stroke(0);
		p.textSize(Sketch.BOX_SIZE / 3f);
		p.text(this.arr.getName(), this.pos.x + Sketch.BOX_SIZE / 2f, this.pos.y + Sketch.BOX_SIZE / 2f);*/
	}

	public PVector getPos() {
		return pos;
	}

	public PVector getDir() {
		return dir;
	}

	public KEYS getArr() {
		return arr;
	}

	public KEYS getDirection() {
		return direction;
	}

	public boolean in(PVector pos, PVector size) {
		return this.pos.x >= pos.x && this.pos.x <= pos.x + size.x && this.pos.y >= pos.y
				&& this.pos.y <= pos.y + size.y;
	}

	public boolean in(int posX, int posY, int sizeX, int sizeY) {
		return in(new PVector(posX, posY), new PVector(sizeX, sizeY));
	}

	public static PVector getStartingPosFromDir(KEYS dir) {
		return new PVector((p.width / 2f - Sketch.BOX_SIZE / 2f) - ((p.width / 2f + Sketch.BOX_SIZE / 2f) * dir.dir.x),
				(p.height / 2f - Sketch.BOX_SIZE / 2f) - ((p.height / 2f + Sketch.BOX_SIZE / 2f) * dir.dir.y));
	}

	public static void spawn(){
		Sketch.boxes.add(new Box());
	}
}
