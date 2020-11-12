public class Button {
	public static Sketch p = Sketch.p;

	private String text;
	private int x, y;
	private int width, height;

	private Runnable clicked;

	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setClickedCallback(Runnable r) {
		clicked = r;
	}

	public boolean contains(int x, int y) {
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}

	public void draw() {
		p.fill(255);
		if (contains(p.mouseX, p.mouseY))
			p.fill(200);
		p.rect(x, y, width, height);
		p.fill(0);
		p.text(text, x + (int) (width / 2D), y + (int) (height / 2D));
	}

	public void update() {
		if (p.mousePressed && contains(p.mouseX, p.mouseY)) {
			clicked.run();
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
