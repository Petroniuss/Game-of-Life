package agh.iet.devs.map.region;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

import java.util.Collections;

public class Jungle extends AbstractRegion {

    private final Rect rect;

    public Jungle(Rect rect) {
        super(Collections.emptyList());
        this.rect = rect;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(rect);
    }

}
