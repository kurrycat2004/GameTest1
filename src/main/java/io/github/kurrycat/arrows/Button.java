package io.github.kurrycat.arrows;

import processing.core.PFont;

public class Button {
	public static Sketch p = Sketch.p;
	public PFont font = new PFont(PFont.findFont("Candara"), true);

	private String text;
	private int x, y;
	private int width, height;

	private Runnable clicked;
	private boolean pressing = false;

	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public static Button middleOffset(String text, int xOff, int yOff, int width, int height) {
		return new Button(text, p.width / 2 - width / 2 + xOff, p.height / 2 - height / 2 + yOff, width, height);
	}

	public void setClickedCallback(Runnable r) {
		clicked = r;
	}

	public boolean contains(int x, int y) {
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

	public void draw() {
		//Fonts: "Book Antiqua Fett", "Bookman Old Style", Bookman Old Style Fett", "Bookman Old Style Fett"
		//"Bookman Old Style Fett Kursiv", "Candara", "DejaVu Sans Mono"

		/*PFont font = new PFont(PFont.findFont(PFont.list()[io.github.kurrycat.arrows.Sketch.font % PFont.list().length]), true);
		p.textFont(font, 20);*/

		p.textFont(font);
		p.textSize(height / 2f);

		p.noStroke();
		p.fill(255);
		if (contains(p.mouseX, p.mouseY))
			p.fill(200);
		p.rect(x, y, width, height, 10, 10, 10, 10);
		p.fill(0);
		p.text(text, x + (int) (width / 2D), y + (int) (height / 2D));
	}

	public void update() {
		if (contains(p.mouseX, p.mouseY)) {
			if (p.mousePressed)
				pressing = true;
			else if (pressing) {
				if (clicked != null)
					clicked.run();
				pressing = false;
			}
		} else {
			if (pressing && !p.mousePressed) pressing = false;
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
		setX(p.width / 2 - width / 2 + xOff);
		setY(p.height / 2 - height / 2 + yOff);
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
}
