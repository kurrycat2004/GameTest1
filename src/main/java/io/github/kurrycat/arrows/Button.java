package io.github.kurrycat.arrows;

import processing.core.PFont;

public class Button {
	public static final PFont defaultFont = new PFont(PFont.findFont("Candara"), true);
	protected PFont font = defaultFont;

	protected String text;
	protected int x, y;
	protected int width, height;

	protected Integer bgColor = null;
	protected Integer strokeColor = null;
	protected Integer textColor = Sketch.p.color(0);
	protected Float strokeWeight = 1f;

	protected Integer hoverBgColor = null;
	protected Integer hoverStrokeColor = Sketch.p.color(200);
	protected Integer hoverTextColor = Sketch.p.color(0);
	protected Float hoverStrokeWeight = 4f;

	protected Runnable clicked;
	protected boolean pressing = false;

	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public static Button middleOffset(String text, int xOff, int yOff, int width, int height) {
		return new Button(text, Sketch.p.width / 2 - width / 2 + xOff, Sketch.p.height / 2 - height / 2 + yOff, width, height);
	}

	public void setClickedCallback(Runnable r) {
		clicked = r;
	}

	public boolean contains(int x, int y) {
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

	public void draw() {
		boolean hover = contains(Sketch.p.mouseX, Sketch.p.mouseY);

		Integer strokeColor = hover ? hoverStrokeColor : this.strokeColor;
		Integer bgColor = hover ? hoverBgColor : this.bgColor;
		Integer textColor = hover ? hoverTextColor : this.textColor;
		Float strokeWeight = hover ? hoverStrokeWeight : this.strokeWeight;
		if (strokeWeight == null) strokeWeight = 1f;

		//Fonts: "Book Antiqua Fett", "Bookman Old Style", Bookman Old Style Fett", "Bookman Old Style Fett"
		//"Bookman Old Style Fett Kursiv", "Candara", "DejaVu Sans Mono"

		/*PFont font = new PFont(PFont.findFont(PFont.list()[io.github.kurrycat.arrows.Sketch.font % PFont.list().length]), true);
		p.textFont(font, 20);*/

		Sketch.p.textFont(font);
		Sketch.p.textSize(height / 2f);

		if (strokeColor == null) Sketch.p.noStroke();
		else Sketch.p.stroke(strokeColor);

		if (bgColor == null) Sketch.p.noFill();
		else Sketch.p.fill(bgColor);

		Sketch.p.strokeWeight(strokeWeight);

		Sketch.p.rect(x, y, width, height, 10, 10, 10, 10);

		if (textColor == null) Sketch.p.noFill();
		else Sketch.p.fill(textColor);
		Sketch.p.text(text, x + (int) (width / 2D), y + (int) (height / 2D));
	}

	public void update() {
		if (contains(Sketch.p.mouseX, Sketch.p.mouseY)) {
			if (Sketch.p.mousePressed)
				pressing = true;
			else if (pressing) {
				if (clicked != null)
					clicked.run();
				pressing = false;
			}
		} else {
			if (pressing && !Sketch.p.mousePressed) pressing = false;
		}
	}

	public Button setText(String text) {
		this.text = text;
		return this;
	}

	public Button setX(int x) {
		this.x = x;
		return this;
	}

	public Button setY(int y) {
		this.y = y;
		return this;
	}

	public Button setPos(int x, int y) {
		setX(x);
		setY(y);
		return this;
	}

	public Button setMiddleOffsetPos(int xOff, int yOff) {
		setX(Sketch.p.width / 2 - width / 2 + xOff);
		setY(Sketch.p.height / 2 - height / 2 + yOff);
		return this;
	}

	public Button setWidth(int width) {
		this.width = width;
		return this;
	}

	public Button setHeight(int height) {
		this.height = height;
		return this;
	}

	public Button setBgColor(Integer bgColor) {
		this.bgColor = bgColor;
		return this;
	}

	public Button setStrokeColor(Integer strokeColor) {
		this.strokeColor = strokeColor;
		return this;
	}

	public Button setTextColor(Integer textColor) {
		this.textColor = textColor;
		return this;
	}

	public Button setStrokeWeight(Float strokeWeight) {
		this.strokeWeight = strokeWeight;
		return this;
	}

	public Button setHoverBgColor(Integer hoverBgColor) {
		this.hoverBgColor = hoverBgColor;
		return this;
	}

	public Button setHoverStrokeColor(Integer hoverStrokeColor) {
		this.hoverStrokeColor = hoverStrokeColor;
		return this;
	}

	public Button setHoverTextColor(Integer hoverTextColor) {
		this.hoverTextColor = hoverTextColor;
		return this;
	}

	public Button setHoverStrokeWeight(Float hoverStrokeWeight) {
		this.hoverStrokeWeight = hoverStrokeWeight;
		return this;
	}

	public Button setFont(PFont font) {
		this.font = font;
		return this;
	}

	public Button copyDesign(Button button) {
		bgColor = button.bgColor;
		hoverBgColor = button.hoverBgColor;
		textColor = button.textColor;
		hoverTextColor = button.hoverTextColor;
		strokeColor = button.strokeColor;
		hoverStrokeColor = button.hoverStrokeColor;
		strokeWeight = button.strokeWeight;
		hoverStrokeWeight = button.hoverStrokeWeight;
		return this;
	}
}
