package agh.iet.devs.map.region;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

import java.util.Collection;
import java.util.LinkedList;

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

    private static Collection<Vector> space(Rect outer, Rect inner) {
        final var collection = new LinkedList<Vector>();

        for (Vector v : outer) {
            if (!v.withinRect(inner))
                collection.addFirst(v);
        }

        return collection;
    }

}
