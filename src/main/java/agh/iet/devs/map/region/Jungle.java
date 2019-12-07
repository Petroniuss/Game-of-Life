package agh.iet.devs.map.region;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

import java.util.Collection;
import java.util.LinkedList;

public class Jungle extends AbstractRegion {

    private final Rect rect;

    public Jungle(Rect rect) {
        super(rectToVectors(rect));

        this.rect = rect;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(rect);
    }

    private static Collection<Vector> rectToVectors(Rect rect) {
        final var collection = new LinkedList<Vector>();
        rect.iterator().forEachRemaining(collection::addFirst);

        return collection;
    }

}
