package agh.iet.devs.map.region;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

import java.util.Collections;

public class Grassland extends AbstractRegion {

    private final Rect outer;
    private final Rect inner;

    public Grassland(Rect outer, Rect inner) {
        super(Collections.emptyList());
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(outer) && !vector.withinRect(inner);
    }

}
