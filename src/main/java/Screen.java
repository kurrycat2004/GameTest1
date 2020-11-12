public abstract class Screen {
	public static Sketch p = Sketch.p;

	public abstract void keyPressed(int keyCode);

	public abstract void keyReleased(int keyCode);

	public abstract void draw();

	public abstract void update();
}
