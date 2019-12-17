package agh.iet.devs.map;

import agh.iet.devs.data.Vector;

/**
 * Class responsible for adjusting positions of movable objects on a map.
 *
 * Namely,
 * When animal moves to the left of left edge he comes out the right one.
 */
public class MoveCoordinator {
    private final int width;
    private final int height;

    public MoveCoordinator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Vector validateMove(Vector move) {
        var x = move.x % width;
        var y = move.y % height;

        if (move.x < 0)
            x = width - Math.abs(move.x);

        if (move.y < 0)
            y = height - Math.abs(move.y);

        return Vector.create(x, y);
    }

}
