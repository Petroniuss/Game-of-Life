package agh.iet.devs.view.controller;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.config.SimulationState;
import agh.iet.devs.view.node.Tile;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewController implements UpdateListener {
    private final HashMap<Vector, Tile> nodes = new HashMap<>();
    private final Rect bounds;

    private final AtomicInteger animalCounter;
    private final AtomicInteger foodCounter;

    public ViewController (GridPane grid, double gridWidth, double gridHeight, SimulationState state) {
        final var config = Config.getInstance();
        final var params = config.params;
        final var jungle = config.jungleBounds();

        this.animalCounter = state.animalCount;
        this.foodCounter = state.foodCount;

        final var width = gridWidth / params.width;
        final var height = gridHeight / params.height;

        this.bounds = config.outerBounds();

        for (Vector v : config.outerBounds()) {
            final var rectangle = new Tile(width, height,
                    v.withinRect(jungle) ? Tile.TileType.JUNGLE : Tile.TileType.GRASSLAND);

            grid.add(rectangle, v.y, v.x, 1, 1);
            nodes.put(v, rectangle);
        }
    }

    @Override
    public void onUpdate(List<MapElement> updated) {
        for (Vector key : this.bounds)
            nodes.get(key).clear();

        updated.forEach(this::draw);
        final var elementsCount = updated.stream()
                .reduce(IntTuple.apply(0, 0), (acc, e) -> {
                    if (e instanceof Animal)
                        return IntTuple.apply(acc.first + 1, acc.second);
                    else
                        return IntTuple.apply(acc.first, acc.second + 1);
                }, (a, b) -> IntTuple.apply(a.first + b.first, a.second + b.second));

        animalCounter.set(elementsCount.first);
        foodCounter.set(elementsCount.second);
    }

    private void draw(MapElement e) {
        final var node = nodes.get(e.getPosition().reverse());

        node.renderIcon(e.getIcon());
        node.updateTooltip(e.toString());
    }

    private static class IntTuple {
        public final int first;
        public final int second;

         public IntTuple(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public static IntTuple apply(int first, int second) {
            return new IntTuple(first, second);
        }
    }
}
