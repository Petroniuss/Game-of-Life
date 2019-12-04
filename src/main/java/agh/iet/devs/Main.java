package agh.iet.devs;

import agh.iet.devs.map.World;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        final var world = new World();

        IntStream.range(0, 5).forEach(i -> world.onUpdate());
    }

}
