package agh.iet.devs.map;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

public class Jungle implements Region {

    private final Rect rect;

    public Jungle(Rect rect) {
        this.rect = rect;
    }

    @Override
    public boolean isWithin(Vector vector) {
        return vector.withinRect(rect);
    }
}
