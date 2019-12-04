package agh.iet.devs.map.region;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

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
