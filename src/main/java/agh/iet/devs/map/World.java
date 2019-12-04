package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

public class World implements Map, OnMoveListener, OnVanishListener {

    public World() {
        final var params = Config.getInstance().params;
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
