package io.github.kurrycat.arrows;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Box {
	public static final Sketch p = Sketch.p;
	public static float boxSpeed = 1f;
	public static final ArrayList<Box> boxes = new ArrayList<>();
	public static short BOX_SIZE;

	private final PVector pos;

	private boolean disabled = false;

	private final PVector dir;
	private final KEYS arr;


	public Box() {
		this(KEYS.values()[(int) p.random(4)], KEYS.values()[(int) p.random(8)]);
	}

	public Box(KEYS dir, KEYS arr) {
		this.pos = getStartingPosFromDir(dir);
		this.dir = dir.dir;
		this.arr = arr;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void update() {
		if (!disabled)
			this.pos.add(this.dir.copy().mult(boxSpeed));
		else
			this.pos.add(this.arr.dir.copy().mult(boxSpeed * 10));
	}

	public void show(boolean first) {
		p.stroke(0);
		if (!disabled) {
			if (first)
				p.fill(50, 200, 50);
			else
				p.fill(255);

		} else {
			p.fill(100);
		}
		p.rect(this.pos.x, this.pos.y, BOX_SIZE, BOX_SIZE);

		p.shape(arr.shape, this.pos.x + BOX_SIZE / 2f, this.pos.y + BOX_SIZE / 2f);
	}

	public KEYS getArr() {
		return arr;
	}

	public boolean in(PVector pos, PVector size) {
		return this.pos.x >= pos.x && this.pos.x <= pos.x + size.x && this.pos.y >= pos.y
				&& this.pos.y <= pos.y + size.y;
	}

	public boolean in(int posX, int posY, int sizeX, int sizeY) {
		return in(new PVector(posX, posY), new PVector(sizeX, sizeY));
	}

	public static PVector getStartingPosFromDir(KEYS dir) {
		int x;
		if (dir == KEYS.ARROW_LEFT) x = p.width;
		else if (dir == KEYS.ARROW_RIGHT) x = -BOX_SIZE;
		else x = PApplet.floor(p.random(p.width - BOX_SIZE) / BOX_SIZE) * BOX_SIZE;

		int y;
		if (dir == KEYS.ARROW_UP) y = p.height;
		else if (dir == KEYS.ARROW_DOWN) y = -BOX_SIZE;
		else y = PApplet.floor(p.random(p.height - BOX_SIZE) / BOX_SIZE) * BOX_SIZE;

		return new PVector(x, y);
	}

	public static Box getFirstBox() {
		for (int i = 0; i < boxes.size(); i++)
			if (!boxes.get(i).disabled) return boxes.get(i);
		return null;
	}

	public static void spawn() {
		boxes.add(new Box());
	}
}