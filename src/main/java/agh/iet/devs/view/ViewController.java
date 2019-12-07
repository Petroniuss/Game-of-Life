package agh.iet.devs.view;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;

public class ViewController implements UpdateListener {

    private final HashMap<Vector, Tile> nodes = new HashMap<>();
    private final Rect bounds;

    public ViewController (GridPane grid, double gridWidth, double gridHeight) {
        final var config = Config.getInstance();
        final var params = config.params;
        final var jungle = config.jungleBounds();

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
    }

    private void draw(MapElement e) {
        final var node = nodes.get(e.getPosition().reverse());

        node.renderIcon(e.getIcon());
        node.updateTooltip(e.toString());
    }
}
