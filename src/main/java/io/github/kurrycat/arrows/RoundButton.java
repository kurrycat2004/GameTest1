package io.github.kurrycat.arrows;

/**
 * RoundButton class that extends {@link Button}
 */
public class RoundButton extends Button {
	/**
	 * Radius of the round button
	 */
	protected int radius;

	/**
	 * RoundButton constructor
	 *
	 * @param text   text that gets displayed on the button
	 * @param x      x position of the button
	 * @param y      y position of the button
	 * @param radius radius of the button
	 */
	public RoundButton(String text, int x, int y, int radius) {
		super(text, x, y, radius, radius);
		this.radius = radius;
	}

	/**
	 * Draw method that overrides {@link Button#draw()} to draw a round button
	 */
	@Override
	public void draw() {
		boolean hover = contains(Sketch.p.mouseX, Sketch.p.mouseY);

		Integer strokeColor = hover ? hoverStrokeColor : this.strokeColor;
		Integer bgColor = hover ? hoverBgColor : this.bgColor;
		Integer textColor = hover ? hoverTextColor : this.textColor;
		Float strokeWeight = hover ? hoverStrokeWeight : this.strokeWeight;
		if (strokeWeight == null) strokeWeight = 1f;

		Sketch.p.textFont(font);
		Sketch.p.textSize(height / 0.8f);

		if (strokeColor == null) Sketch.p.noStroke();
		else Sketch.p.stroke(strokeColor);

		if (bgColor == null) Sketch.p.noFill();
		else Sketch.p.fill(bgColor);

		Sketch.p.strokeWeight(strokeWeight);

		Sketch.p.ellipse(x, y, radius * 2, radius * 2);

		if (textColor == null) Sketch.p.noFill();
		else Sketch.p.fill(textColor);
		Sketch.p.text(text, x, y);
	}

	/**
	 * Contains method that overrides {@link Button#contains(int x, int y)} to check if the point is inside the round button
	 *
	 * @param x x position of the point that gets checked
	 * @param y y position of the point that gets checked
	 * @return if x and y are inside the button
	 */
	@Override
	public boolean contains(int x, int y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) < radius;
	}
}
