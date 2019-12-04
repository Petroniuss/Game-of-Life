package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

public class World implements Map, OnMoveListener, OnVanishListener {

    private final int width;
    private final int height;

    public World() {
        final var params = Config.getInstance().params;

        this.height = params.height;
        this.width = params.width;
    }

    @Override
    public boolean isOccupied(Vector position) {
        return false;
    }

    @Override
    public void onMove(MapElement e, Vector from) {

    }

    @Override
    public void onVanish(MapElement element) {

    }
}
