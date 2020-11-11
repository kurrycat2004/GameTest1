import processing.core.PApplet;
import processing.core.PConstants;

public class Sketch extends PApplet {
    public static Sketch p = new Sketch();
    public static int windowWidth;
    public static int windowHeight;

    public void settings() {
        //fullScreen();
        size(displayWidth, displayHeight);

        windowWidth = width;
        windowHeight = height;

        Box.BOX_SIZE = (short) (windowWidth / 25);
        registerMethod("pre", this);
    }


    public void setup() {
        surface.setResizable(true);
        //surface.hideCursor();
        textAlign(PConstants.CENTER, PConstants.CENTER);

        KEYS.init();
    }

    // window resize handling
    public void pre() {
        if (windowWidth == width && windowHeight == height) return;
        windowWidth = width;
        windowHeight = height;

        Box.BOX_SIZE = (short) (windowWidth / 25);
        KEYS.init();
    }

    public void draw() {
        GameLoop.draw();
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


	
