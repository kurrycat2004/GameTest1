import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Box {
    public static final Sketch p = Sketch.p;
    public static float boxSpeed = 2f;
    public static final ArrayList<Box> boxes = new ArrayList<>();
    public static short BOX_SIZE;

    private final PVector pos;

    private boolean disabled = false;

    private final PVector dir;
    private final KEYS arr;


    public Box() {
        this(KEYS.values()[(int) p.random(4)], KEYS.values()[(int) p.random(8)]);
    }

    public Box(KEYS dir, KEYS arr) {
        this.pos = getStartingPosFromDir(dir);
        this.dir = dir.dir;
        this.arr = arr;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void update() {
        if (!disabled)
            this.pos.add(this.dir.copy().mult(boxSpeed));
        else
            this.pos.add(this.arr.dir.copy().mult(boxSpeed * 10));
    }

    public void show(boolean first) {
        p.stroke(0);
        if (!disabled) {
            if (first)
                p.fill(50, 200, 50);
            else
                p.fill(255);

        } else {
            p.fill(100);
        }
        p.rect(this.pos.x, this.pos.y, BOX_SIZE, BOX_SIZE);

        p.shape(arr.shape, this.pos.x + BOX_SIZE / 2f, this.pos.y + BOX_SIZE / 2f);
    }

    public KEYS getArr() {
        return arr;
    }

    public boolean in(PVector pos, PVector size) {
        return this.pos.x >= pos.x && this.pos.x <= pos.x + size.x && this.pos.y >= pos.y
                && this.pos.y <= pos.y + size.y;
    }

    public boolean in(int posX, int posY, int sizeX, int sizeY) {
        return in(new PVector(posX, posY), new PVector(sizeX, sizeY));
    }

    public static PVector getStartingPosFromDir(KEYS dir) {
        switch (dir) {
            case ARROW_UP:
                return new PVector(
                        PApplet.floor(p.random(p.width - BOX_SIZE) / BOX_SIZE) * BOX_SIZE,
                        p.height
                );
            case ARROW_DOWN:
                return new PVector(
                        PApplet.floor(p.random(p.width - BOX_SIZE) / BOX_SIZE) * BOX_SIZE,
                        -BOX_SIZE
                );
            case ARROW_LEFT:
                return new PVector(
                        p.width,
                        PApplet.floor(p.random(p.height - BOX_SIZE) / BOX_SIZE) * BOX_SIZE
                );
            case ARROW_RIGHT:
                return new PVector(
                        -BOX_SIZE,
                        PApplet.floor(p.random(p.height - BOX_SIZE) / BOX_SIZE) * BOX_SIZE
                );
            default:
                return new PVector(0, 0);
        }
    }

    public static Box getFirstBox() {
        for (Box b : boxes)
            if (!b.disabled) return b;
        return null;
    }

    public static void spawn() {
        boxes.add(new Box());
    }
}
