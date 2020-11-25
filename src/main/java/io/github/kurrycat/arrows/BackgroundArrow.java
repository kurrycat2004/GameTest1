package io.github.kurrycat.arrows;

import processing.core.PConstants;
import processing.core.PShape;

/**
 * Class for the background arrows
 */
public class BackgroundArrow {
	/**
	 * Position, velocity and size of the arrow
	 */
	public double x, y, velX, velY, size;
	/**
	 * The arrow {@link PShape}
	 */
	public PShape shape;

	/**
	 * Creates a new arrow
	 *
	 * @param x    the x position of the arrow
	 * @param y    the y position of the arrow
	 * @param velX the x velocity of the arrow
	 * @param velY the y velocity of the arrow
	 * @param size the size of the arrow
	 */
	public BackgroundArrow(double x, double y, double velX, double velY, double size) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.size = size;
		this.shape = Sketch.p.createShape(PConstants.GROUP);
		this.shape.addChild(KEYS.ARROW_UP.shape);
		this.shape.rotate((float) Math.atan2(velY, velX) + PConstants.HALF_PI);
		this.shape.scale((float) size / Box.BOX_SIZE);
	}

	/**
	 * Draws the arrow as {@link #shape}
	 */
	public void draw() {
		Sketch.p.shape(this.shape, (int) x, (int) y);
	}

	/**
	 * Updates the position of the arrow
	 */
	public void update() {
		x += velX;
		y += velY;
	}
}
