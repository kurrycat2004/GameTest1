import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class Sketch extends PApplet {
    public static Sketch p = new Sketch();

    public static short BOX_SIZE;

    public static float boxSpeed = 2f;
    public static int score = 0;
    public static int highscore = 0;
    public static int frame = 0;
    public static ArrayList<Box> boxes = new ArrayList<>();

    public void settings() {
        fullScreen();
        BOX_SIZE = (short) (displayWidth / 25);
    }

    public void setup() {
        textAlign(PConstants.CENTER, PConstants.CENTER);
        textSize(Sketch.BOX_SIZE / 3f);

        KEYS.init();
    }

    public void draw() {
        frame++;
        background(0, 0, 0);
        strokeWeight(0.2f);
        stroke(255);
        noFill();
        for (int i = 0; i < width / BOX_SIZE; i++) {
            for (int j = 0; j < height / BOX_SIZE; j++) {
                rect(i * BOX_SIZE, j * BOX_SIZE, BOX_SIZE, BOX_SIZE);
            }
        }
        if (frame % floor(BOX_SIZE / boxSpeed) == 0) {
            Box.spawn();
            boxSpeed += 0.02;
            frame -= floor(BOX_SIZE / boxSpeed);
        }

        if (boxes.size() > 0) {
            Box first = Box.getFirstBox();
            if (first != null) {
                if (KEYS.getKeyPressed() != null) System.out.println(KEYS.getKeyPressed());
                if (first.getArr() == KEYS.getKeyPressed()) {
                    first.setDisabled(true);
                    score++;
                } else if (KEYS.getKeyPressed() != null) {
                    score--;
                }
                KEYS.resetPress();
            }
        }


        for (Box b : boxes) {
            b.update();
            b.show(b == Box.getFirstBox());
        }

        for (int i = boxes.size() - 1; i >= 0; i--) {
            if (!boxes.get(i).in(-BOX_SIZE, -BOX_SIZE, width + BOX_SIZE, height + BOX_SIZE)) {
                if (!boxes.get(i).isDisabled())
                    score--;
                boxes.remove(i);
            }
        }

        highscore = max(score, highscore);

        fill(255);
        textSize(Sketch.BOX_SIZE / 3f);
        text(frameRate, Sketch.BOX_SIZE, Sketch.BOX_SIZE / 3f);
        text(boxSpeed, Sketch.BOX_SIZE, Sketch.BOX_SIZE);

        fill(255);
        textSize(Sketch.BOX_SIZE / 2f);
        text(score, width / 2f, Sketch.BOX_SIZE);
        fill(100, 255, 100);
        textSize(Sketch.BOX_SIZE / 3f);
        text(highscore, width / 2f, Sketch.BOX_SIZE / 2f);
    }

    public void mousePressed() {
        //Box.spawn();
    }

    public void keyPressed() {
        KEYS k = KEYS.fromKeyCode(keyCode);
        if (k != null) {
            k.pressed = true;
        }
    }

    public void keyReleased() {
        KEYS k = KEYS.fromKeyCode(keyCode);
        if (k != null) {
            k.pressed = false;
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {"Sketch"};
        PApplet.runSketch(processingArgs, p);
    }
}


	
