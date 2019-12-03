package agh.iet.devs.data;

import java.util.Random;

public enum Direction {

    NORTH(Vector.create(0, 1)),
    NORTHEAST(Vector.create(1, 1)),
    EAST(Vector.create(1, 0)),
    SOUTHEAST(Vector.create(1, -1)),
    SOUTH(Vector.create(0, -1)),
    SOUTHWEST(Vector.create(-1, -1)),
    WEST(Vector.create(-1, 0)),
    NORTHWEST(Vector.create(-1, 1));

    public final Vector direction;

    Direction(Vector direction) {
        this.direction = direction;
    }

    public Direction turn(int turns) {
        return Direction.values()[(ordinal() + turns) % Direction.values().length];
    }

    public static Direction random() {
        return Direction.values()[new Random().nextInt(Direction.values().length)];
    }

}
