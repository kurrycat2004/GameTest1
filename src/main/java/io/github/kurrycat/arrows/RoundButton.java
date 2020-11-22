package io.github.kurrycat.arrows;

public class RoundButton extends Button {
	protected int radius;

	public RoundButton(String text, int x, int y, int radius) {
		super(text, x, y, radius, radius);
		this.radius = radius;
	}

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

	@Override
	public boolean contains(int x, int y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) < radius;
	}
}
