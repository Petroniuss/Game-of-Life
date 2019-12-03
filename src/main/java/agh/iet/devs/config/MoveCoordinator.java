package agh.iet.devs.config;

import agh.iet.devs.data.Vector;

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
