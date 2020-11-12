

public class Menu extends Screen {
	private Button startGame = new Button("Start Game", p.width / 2 - 50, p.height / 2 - 20, 100, 40);

	public Menu() {
		startGame.setClickedCallback(() -> {
			Sketch.currentScreen = new Game();
		});
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void draw() {
		p.background(255, 192, 203);
		startGame.draw();
	}

	public void update() {
		startGame.update();
	}
}
