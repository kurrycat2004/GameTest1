package io.github.kurrycat.arrows;

import processing.core.PFont;
import processing.core.PShape;
import processing.core.PVector;

import java.util.function.Supplier;

/**
 * Button class
 */
public class Button {
	/**
	 * Default font
	 */
	public static final PFont defaultFont = new PFont(PFont.findFont("Candara"), true);
	/**
	 * Button font being {@link #defaultFont} by default
	 */
	protected PFont font = defaultFont;

	/**
	 * Text to be displayed on the button
	 */
	protected String text;
	/**
	 * PShape to be displayed on the button
	 */
	protected PShape shape;
	/**
	 * x and y position of the button
	 */
	protected int x, y;
	/**
	 * width and height of the button
	 */
	protected int width, height;

	/**
	 * Background color of the button with {@code null} meaning no background with a default value of {@code null}
	 */
	protected Integer bgColor = null;
	/**
	 * Stroke color of the button with {@code null} meaning no stroke with a default value of {@code null}
	 */
	protected Integer strokeColor = null;
	/**
	 * Text color which defaults to black
	 */
	protected Integer textColor = Sketch.p.color(0);
	/**
	 * Stroke weight with {@code 1f} as default value
	 */
	protected Float strokeWeight = 1f;

	/**
	 * Background color of the button when {@link #contains(int x, int y)} returns {@code true} with {@code null} meaning no background with a default value of {@code null}
	 */
	protected Integer hoverBgColor = null;
	/**
	 * Stroke color of the button when {@link #contains(int x, int y)} returns {@code true} with {@code null} meaning no stroke with a default value of gray
	 */
	protected Integer hoverStrokeColor = Sketch.p.color(200);
	/**
	 * Stroke color of the button when {@link #contains(int x, int y)} returns {@code true} with a default value of black
	 */
	protected Integer hoverTextColor = Sketch.p.color(0);
	/**
	 * Stroke weight when {@link #contains(int x, int y)} returns {@code true} with {@code 4f} as default value
	 */
	protected Float hoverStrokeWeight = 4f;

	/**
	 * The clicked callback.
	 * {@code null} by default
	 */
	protected Runnable clicked;
	/**
	 * The draw method to override the old one
	 * {@code null} by default
	 */
	protected Runnable drawMethod;
	/**
	 * The hover method that gets executed instead of {@link #contains(int x, int y)}.
	 * {@code null} by default
	 */
	protected Supplier<Boolean> hoverMethod;

	/**
	 * Indicates whether the user presses a mouse button while {@link #contains(int x, int y)} or {@link #hoverMethod} returns {@code true}
	 */
	protected boolean pressing = false;

	/**
	 * Button constructor
	 *
	 * @param x      x position of the button
	 * @param y      y position of the button
	 * @param width  width of the button
	 * @param height height of the button
	 */
	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Button constructor
	 *
	 * @param text   text to be displayed on the button
	 * @param x      x position of the button
	 * @param y      y position of the button
	 * @param width  width of the button
	 * @param height height of the button
	 */
	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Creates a new button with a position relative to the middle of the screen
	 *
	 * @param text   text to be displayed on the button
	 * @param xOff   x position of the button relative to the center of the screen
	 * @param yOff   y position of the button relative to the center of the screen
	 * @param width  width of the button
	 * @param height height of the button
	 * @return button instance
	 */
	public static Button middleOffset(String text, int xOff, int yOff, int width, int height) {
		return new Button(text, Sketch.p.width / 2 - width / 2 + xOff, Sketch.p.height / 2 - height / 2 + yOff, width, height);
	}


	/**
	 * Builder setter for {@link #clicked}
	 *
	 * @param r {@link Runnable} that should be executed if the player clicks and {@link #contains(int x, int y)} or {@link #hoverMethod} returns {@code true}
	 * @return this
	 */
	public Button setClickedCallback(Runnable r) {
		clicked = r;
		return this;
	}

	/**
	 * Builder setter for {@link #drawMethod}
	 *
	 * @param r {@link Runnable} that should be executed instead of {@link #draw()}
	 * @return this
	 */
	public Button setDrawMethod(Runnable r) {
		drawMethod = r;
		return this;
	}

	/**
	 * Builder setter for {@link #hoverMethod}
	 *
	 * @param r {@link Runnable} that should be used instead of {@link #contains(int x, int y)}
	 * @return this
	 */
	public Button setHoverMethod(Supplier<Boolean> r) {
		hoverMethod = r;
		return this;
	}

	/**
	 * Builder setter for {@link #shape}
	 *
	 * @param shape PShape to be displayed on the button
	 * @return this
	 */
	public Button setShape(PShape shape) {
		this.shape = shape;
		return this;
	}

	/**
	 * Method that returns a boolean indicating if the point {@code x, y} is inside the button
	 *
	 * @param x x coordinate of the point
	 * @param y y coordinate of the point
	 * @return if the point is inside the button
	 */
	public boolean contains(int x, int y) {
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

	/**
	 * Draws the button if {@link #drawMethod} {@code == null}, else executes {@link #drawMethod}
	 */
	public void draw() {
		//Fonts: "Book Antiqua Fett", "Bookman Old Style", Bookman Old Style Fett", "Bookman Old Style Fett"
		//"Bookman Old Style Fett Kursiv", "Candara", "DejaVu Sans Mono"

		/*PFont font = new PFont(PFont.findFont(PFont.list()[io.github.kurrycat.arrows.Sketch.font % PFont.list().length]), true);
		p.textFont(font, 20);*/

		if (drawMethod != null) {
			drawMethod.run();
			return;
		}

		boolean hover;
		if (hoverMethod == null) hover = contains(Sketch.p.mouseX, Sketch.p.mouseY);
		else hover = hoverMethod.get();

		Integer strokeColor = hover ? hoverStrokeColor : this.strokeColor;
		Integer bgColor = hover ? hoverBgColor : this.bgColor;
		Integer textColor = hover ? hoverTextColor : this.textColor;
		Float strokeWeight = hover ? hoverStrokeWeight : this.strokeWeight;
		if (strokeWeight == null) strokeWeight = 1f;

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

		if (text != null)
			Sketch.p.text(text, x + (int) (width / 2D), y + (int) (height / 2D));

		if (shape != null) {
			Sketch.p.shape(shape, x + (int) (width / 2D), y + (int) (height / 2D));
		}
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

	public Button setSize(int width, int height) {
		this.width = width;
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

	public Integer getBgColor() {
		return bgColor;
	}

	public Integer getStrokeColor() {
		return strokeColor;
	}

	public Integer getTextColor() {
		return textColor;
	}

	public Float getStrokeWeight() {
		return strokeWeight;
	}

	public Integer getHoverBgColor() {
		return hoverBgColor;
	}

	public Integer getHoverStrokeColor() {
		return hoverStrokeColor;
	}

	public Integer getHoverTextColor() {
		return hoverTextColor;
	}

	public Float getHoverStrokeWeight() {
		return hoverStrokeWeight;
	}

	public PVector getPos() {
		return new PVector(x, y);
	}
}
