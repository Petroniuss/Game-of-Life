package agh.iet.devs.data;

import java.util.Iterator;

//Todo define iterator over each position within rect!
public class Rect implements Iterable<Vector> {
    public final Vector lowerLeft;
    public final Vector upperRight;

    public Rect(Vector lowerLeft, Vector upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    private static class RectIterator implements Iterator<Vector> {
        private final Vector start;
        private final int max;
        private final int width;

        private int pointer = 0;

        RectIterator(Vector lowerLeft, Vector upperRight) {
            this.width = upperRight.x - lowerLeft.x + 1;
            var height = upperRight.y - lowerLeft.y + 1;

            this.start = lowerLeft;
            this.max = width * height;
        }

        @Override
        public boolean hasNext() {
            return pointer < max;
        }

        @Override
        public Vector next() {
            final var dx = pointer % width;
            final var dy = pointer / width;
            final var dv = Vector.create(dx, dy);

            pointer += 1;

            return start.add(dv);
        }
    }

    @Override
    public Iterator<Vector> iterator() {
        return new RectIterator(lowerLeft, upperRight);
    }
}
