package agh.iet.devs.map;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

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
}
