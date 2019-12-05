package agh.iet.devs.view;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.MapElementObserver;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
public class ViewController implements MapElementObserver, OnAttachListener {

    private final HashMap<Vector, Tile> nodes = new HashMap<>();

    public ViewController (GridPane grid, double gridWidth, double gridHeight) {
        final var config = Config.getInstance();
        final var params = config.params;
        final var jungle = config.jungleBounds();

        final var width = gridWidth / params.width;
        final var height = gridHeight / params.height;

        for (Vector v : config.outerBounds()) {
            final var rectangle = new Tile(width, height,
                    v.withinRect(jungle) ? Tile.TileType.JUNGLE : Tile.TileType.GRASSLAND);

            grid.add(rectangle, v.y, v.x, 1, 1);
            nodes.put(v, rectangle);
        }
    }

//    FIXME - BAAD CODE!

    @Override
    public void onMove(MapElement e, Vector from) {
        nodes.get(from).clear();
        nodes.get(e.getPosition()).renderIcon(Tile.Icon.Animal);
    }

    @Override
    public void onVanish(MapElement element) {
        nodes.get(element.getPosition()).clear();
    }

    @Override
    public void onAttach(MapElement e) {
        final var node = nodes.get(e.getPosition());

        if (e instanceof Animal) {
            node.renderIcon(Tile.Icon.Animal);
        } else if (e instanceof Food) {
            node.renderIcon(Tile.Icon.Food);
        }
    }
}
