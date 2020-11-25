package io.github.kurrycat.arrows;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Box class
 */
public class Box {
	/**
	 * Speed of all boxes
	 */
	public static float boxSpeed = 1f;
	/**
	 * ArrayList of all boxes
	 */
	public static final ArrayList<Box> boxes = new ArrayList<>();
	/**
	 * Size of all boxes
	 */
	public static short BOX_SIZE;

	/**
	 * Position of the box as {@link PVector}
	 */
	protected final PVector pos;

	/**
	 * Indicates whether the box is disabled
	 */
	protected boolean disabled = false;

	/**
	 * Direction the box is moving as {@link PVector}
	 */
	protected final PVector dir;

	/**
	 * The {@link KEYS} instance that should be drawn on screen and which is tested for when the user presses an arrow key
	 */
	protected final KEYS arr;

	/**
	 * Default constructor that just returns a new {@link Box} with a random direction and KEYS instance
	 */
	public Box() {
		this(KEYS.values()[(int) Sketch.p.random(4)], KEYS.values()[(int) Sketch.p.random(8)]);
	}


	/**
	 * Constructor that takes the direction and arrow KEYS instance as arguments
	 *
	 * @param dir direction {@link KEYS} instance
	 * @param arr arrow {@link KEYS} instance
	 */
	public Box(KEYS dir, KEYS arr) {
		this.pos = getStartingPosFromDir(dir);
		this.dir = dir.dir;
		this.arr = arr;
	}

	/**
	 * Constructor that takes the direction KEYS instance, arrow KEYS instance and the box position as arguments
	 *
	 * @param dir direction {@link KEYS} instance
	 * @param arr arrow {@link KEYS} instance
	 * @param pos position {@link PVector} instance
	 */
	public Box(KEYS dir, KEYS arr, PVector pos) {
		this.pos = pos.copy();
		this.dir = dir.dir;
		this.arr = arr;
	}

	/**
	 * {@link #disabled} setter
	 *
	 * @param disabled new disabled state
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * {@link #disabled} getter
	 *
	 * @return {@link #disabled}
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Updates {@link #pos} based on {@link #dir}
	 */
	public void update() {
		if (!disabled)
			this.pos.add(this.dir.copy().mult(boxSpeed));
		else
			this.pos.add(this.arr.dir.copy().mult(boxSpeed * 20));
	}

	/**
	 * Draws the box with the arrow
	 *
	 * @param first fills the box background green if {@code true}, else fills the box white
	 */
	public void show(boolean first) {
		Sketch.p.strokeWeight(1);
		Sketch.p.stroke(0);
		if (!disabled) {
			if (first)
				Sketch.p.fill(50, 200, 50);
			else
				Sketch.p.fill(255);
		} else {
			Sketch.p.fill(100);
		}
		Sketch.p.rect(this.pos.x, this.pos.y, BOX_SIZE, BOX_SIZE, 10, 10, 10, 10);

		Sketch.p.shape(arr.shape, this.pos.x + BOX_SIZE / 2f, this.pos.y + BOX_SIZE / 2f);
	}

	/**
	 * {@link #arr} getter
	 *
	 * @return {@link #arr}
	 */
	public KEYS getArr() {
		return arr;
	}

	/**
	 * Returns whether the box is inside the rectangle pos.x, pos.y, pos.x + size.x, pos.y + size.y
	 *
	 * @param pos  position {@link PVector} of the rectangle
	 * @param size size of the rectangle as {@link PVector}
	 * @return if the box is inside the rectangle
	 */
	public boolean in(PVector pos, PVector size) {
		return this.pos.x >= pos.x && this.pos.x <= pos.x + size.x && this.pos.y >= pos.y
				       && this.pos.y <= pos.y + size.y;
	}

	/**
	 * Calls {@link #in(PVector pos, PVector size)} with the position and size
	 *
	 * @param posX  x position of the rectangle
	 * @param posY  y position of the rectangle
	 * @param sizeX width of the rectangle
	 * @param sizeY height of the rectangle
	 * @return if the box is inside the rectangle
	 */
	public boolean in(int posX, int posY, int sizeX, int sizeY) {
		return in(new PVector(posX, posY), new PVector(sizeX, sizeY));
	}

	/**
	 * Calls {@link #in(int posX, int posY, int sizeX, int sizeY)} with the window position and size as arguments
	 *
	 * @return if the rectangle is inside the window
	 */
	public boolean onScreen() {
		return in(-Box.BOX_SIZE, -Box.BOX_SIZE, Sketch.p.width + Box.BOX_SIZE, Sketch.p.height + Box.BOX_SIZE);
	}

	/**
	 * Returns the starting position based on the {@link #dir} the box is moving
	 *
	 * @param dir the direction of the box
	 * @return the starting position
	 */
	public static PVector getStartingPosFromDir(KEYS dir) {
		int x;
		if (dir == KEYS.ARROW_LEFT) x = Sketch.p.width;
		else if (dir == KEYS.ARROW_RIGHT) x = -BOX_SIZE;
		else x = PApplet.floor(Sketch.p.random(Sketch.p.width - BOX_SIZE) / BOX_SIZE) * BOX_SIZE;

		int y;
		if (dir == KEYS.ARROW_UP) y = Sketch.p.height;
		else if (dir == KEYS.ARROW_DOWN) y = -BOX_SIZE;
		else y = PApplet.floor(Sketch.p.random(Sketch.p.height - BOX_SIZE) / BOX_SIZE) * BOX_SIZE;

		return new PVector(x, y);
	}

	/**
	 * Searches for the first box in {@link #boxes} that is not disabled
	 *
	 * @return the first box in {@link #boxes} that is not disabled
	 */
	public static Box getFirstBox() {
		for (int i = 0; i < boxes.size(); i++)
			if (!boxes.get(i).disabled) return boxes.get(i);
		return null;
	}

	/**
	 * Spawns a new {@link Box} by calling {@link #Box()}
	 */
	public static void spawn() {
		boxes.add(new Box());
	}

	/**
	 * Spawns a new {@link Box} with a random {@link #dir} and a random straight {@link KEYS} instance
	 */
	public static void spawnStraight() {
		boxes.add(new Box(KEYS.values()[(int) Sketch.p.random(4)], KEYS.randomStraight()));
	}

	/**
	 * {@link #pos} builder setter
	 * @param pos the new {@link #pos}
	 * @return this
	 */
	public Box setPos(PVector pos) {
		this.pos.set(pos.copy());
		return this;
	}

	/**
	 * {@link #pos} builder setter
	 *
	 * @param x the x of the new {@link #pos}
	 * @param y the y of the new {@link #pos}
	 * @return this
	 */
	public Box setPos(float x, float y) {
		this.setPos(new PVector(x, y));
		return this;
	}
}
