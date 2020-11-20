import processing.core.PApplet;

public class Game extends Screen {
    public static final Game instance = new Game();

    public final Sketch p = Sketch.p;
    public int score = 0;
    public int highscore = 0;
    public int frame = 0;

    public Box first;
    public Thread spawnBoxThread;

    Game() {
        spawnBoxThread = new Thread(() -> {
            while (!spawnBoxThread.isInterrupted()) {
                Box.spawn();
                Box.boxSpeed += 0.02;
                try {
                    Thread.sleep((long) (1000 / Box.boxSpeed));
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    public void start() {
        spawnBoxThread.start();
    }

    public void update() {
        if (first == null || first.isDisabled()) first = Box.getFirstBox();

        if (first != null) {
            KEYS keysPressed = KEYS.getKeyPressed();
            if (keysPressed != null && KEYS.keysPressed.isEmpty()) {
                if (first.getArr() == keysPressed) {
                    first.setDisabled(true);
                    score++;
                } else {
                    score--;
                }
                KEYS.nextMoveKeys.clear();
            }
        }

        for (int i = 0; i < Box.boxes.size(); i++) {
            Box.boxes.get(i).update();
        }
    }

    public void draw() {
        p.getSurface().hideCursor();
        frame++;
        p.background(0, 0, 0);
        p.strokeWeight(0.2f);
        p.stroke(255);
        p.noFill();

        for (int i = 0; i < p.width / Box.BOX_SIZE; i++) {
            for (int j = 0; j < p.height / Box.BOX_SIZE; j++) {
                p.rect(i * Box.BOX_SIZE, j * Box.BOX_SIZE, Box.BOX_SIZE, Box.BOX_SIZE);
            }
        }

        for (int i = 0; i < Box.boxes.size(); i++) {
            Box.boxes.get(i).show(Box.boxes.get(i) == first);
        }

        for (int i = Box.boxes.size() - 1; i >= 0; i--) {
            if (!Box.boxes.get(i).in(-Box.BOX_SIZE, -Box.BOX_SIZE, p.width + Box.BOX_SIZE, p.height + Box.BOX_SIZE)) {
                if (!Box.boxes.get(i).isDisabled())
                    score--;
                Box.boxes.remove(i);
            }
        }

        highscore = PApplet.max(score, highscore);

        p.fill(255);

        p.textSize(Box.BOX_SIZE / 3f);
        p.text(p.frameRate, Box.BOX_SIZE, Box.BOX_SIZE / 3f);
        p.text(Box.boxSpeed, Box.BOX_SIZE, Box.BOX_SIZE);

        p.textSize(Box.BOX_SIZE / 2f);
        p.text(score, p.width / 2f, Box.BOX_SIZE);

        p.fill(100, 255, 100);
        p.textSize(Box.BOX_SIZE / 3f);
        p.text(highscore, p.width / 2f, Box.BOX_SIZE / 2f);
    }

    public void keyPressed(int keyCode) {
        KEYS k = KEYS.fromKeyCode(keyCode);
        if (!KEYS.keysPressed.contains(k))
            KEYS.keysPressed.add(k);
        if (!KEYS.nextMoveKeys.contains(k))
            KEYS.nextMoveKeys.add(k);
    }

    public void keyReleased(int keyCode) {
        KEYS k = KEYS.fromKeyCode(keyCode);
        KEYS.keysPressed.remove(k);
    }
}
