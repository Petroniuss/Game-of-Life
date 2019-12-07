package agh.iet.devs.data;

public class Vector {

    public final int x;
    public final int y;

    private Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector create(int x, int y) {
        return new Vector(x, y);
    }

    public static int compareByY(Vector u, Vector v) {
        if (u.y > v.y || (u.y == v.y && u.x > v.x))
            return 1;
        else if (u.y == v.y && u.x == v.x)
            return 0;
        else
            return -1;
    }

    public static int compareByX(Vector u, Vector v) {
        if (u.x > v.x || (u.x == v.x && u.y > v.y))
            return 1;
        else if (u.y == v.y && u.x == v.x)
            return 0;
        else
            return -1;
    }

    /**
     * @return Vector consisting of x = max(u.x, v.x), y = max(u.y, v.y)
     */
    public static Vector max(Vector u, Vector v) {
        return u.apply(v, Math::max);
    }

    /**
     * @return Vector consisting of x = min(u.x, v.x), y = min(u.y, v.y)
     */
    public static Vector min(Vector u, Vector v) {
        return u.apply(v, Math::min);
    }

    public boolean precedes(Vector other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector upperRight(Vector other) {
        return apply(other,  Math::max);
    }

    public Vector lowerLeft(Vector other) {
        return apply(other, Math::min);
    }

    public Vector add(Vector other) {
        return apply(other, Integer::sum);
    }

    public Vector subtract(Vector other) {
        return apply(other, (a, b) -> a - b);
    }

    public Vector opposite() {
        return apply(i -> -i);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector reverse() {
        return Vector.create(this.y, this.x);
    }

    public boolean withinRect(Rect rect) {
        return withinRect(rect.lowerLeft, rect.upperRight);
    }

    private boolean withinRect(Vector lowerLeft, Vector upperRight) {
        return lowerLeft.x <= this.x && this.x <= upperRight.x
                && lowerLeft.y <= this.y && this.y <= upperRight.y;
    }

    public Vector apply(IntFunction function) {
        return Vector.create(function.apply(this.x), function.apply(this.y));
    }

    private Vector apply(Vector other, BinaryIntFunction function) {
        final int x = function.apply(this.x, other.x);
        final int y = function.apply(this.y, other.y);

        return Vector.create(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;

        Vector vector = (Vector) o;

        if (x != vector.x) return false;
        return y == vector.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public interface BinaryIntFunction {
        int apply(int arg1, int arg2);
    }

    public interface IntFunction {
        int apply(int arg);
    }
}
