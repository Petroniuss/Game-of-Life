package agh.iet.devs.map;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.Optional;

public class Grassland implements Region {

    private final Rect outer;
    private final Rect inner;

    public Grassland(Rect outer, Rect inner) {
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(outer) && !vector.withinRect(inner);
    }


    @Override
    public Optional<MapElement> objectAt(Vector vector) {
        return Optional.empty();
    }
}
