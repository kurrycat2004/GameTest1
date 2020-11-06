import processing.core.PApplet;

public class GameLoop {
    public static Sketch p = Sketch.p;
    public static int score = 0;
    public static int highscore = 0;
    public static int frame = 0;

    public static void draw() {
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
        if (Math.floor(frame % (Box.BOX_SIZE / Box.boxSpeed)) == 0) {
            Box.spawn();
            Box.boxSpeed += 0.02;
            frame -= PApplet.floor(Box.BOX_SIZE / Box.boxSpeed);
        }

        Box first = null;
        if (Box.boxes.size() > 0) {
            first = Box.getFirstBox();
            if (first != null) {
                if (first.getArr() == KEYS.getKeyPressed()) {
                    first.setDisabled(true);
                    score++;
                } else if (KEYS.getKeyPressed() != null) {
                    score--;
                }
                KEYS.resetPress();
            }
        }


        for (Box b : Box.boxes) {
            b.update();
            b.show(b == first);
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
}
