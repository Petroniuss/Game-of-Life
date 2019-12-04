package agh.iet.devs.data;

public class Rect {
    public final Vector lowerLeft;
    public final Vector upperRight;

    public Rect(Vector lowerLeft, Vector upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }
}
