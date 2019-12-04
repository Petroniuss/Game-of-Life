package agh.iet.devs.map;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.Optional;

public class Jungle extends AbstractRegion {

    private final Rect rect;

    public Jungle(Rect rect) {
        this.rect = rect;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(rect);
    }

}
