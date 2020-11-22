package io.github.kurrycat.arrows;

import processing.core.PConstants;
import processing.core.PShape;

public class Star {
	public double x, y, velX, velY, size;
	public PShape shape;

	public Star(double x, double y, double velX, double velY, double size) {
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

	public void draw() {
		Sketch.p.shape(this.shape, (int) x, (int) y);
		/*Sketch.p.stroke(255);
		Sketch.p.strokeWeight((float) (size));
		Sketch.p.point((int) x, (int) y);*/
	}

	public void update() {
		x += velX;
		y += velY;
	}
}
