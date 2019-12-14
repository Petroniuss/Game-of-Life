package agh.iet.devs.map.region;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

import java.util.ArrayList;
import java.util.List;

public class Grassland extends AbstractRegion {

    private final Rect outer;
    private final Rect inner;

    public Grassland(Rect outer, Rect inner) {
        super(space(outer, inner));

        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(outer) && !vector.withinRect(inner);
    }

    private static List<Vector> space(Rect outer, Rect inner) {
        final var collection = new ArrayList<Vector>();

        for (Vector v : outer) {
            if (!v.withinRect(inner))
                collection.add(v);
        }

        return collection;
    }

}
