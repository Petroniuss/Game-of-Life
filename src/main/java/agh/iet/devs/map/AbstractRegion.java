package agh.iet.devs.map;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.*;
import java.util.Map;

public abstract class AbstractRegion implements Region {

    protected Map<Vector, Set<MapElement>> elements = new HashMap<>();


    protected void addMapElement(MapElement e) {
        final var key = e.getPosition();

        if (elements.containsKey(key))
            elements.get(key).add(e);
        else
            elements.put(key, new HashSet<>(Collections.singleton(e)));
    }

    protected void removeElement(MapElement e) {
        final var key = e.getPosition();

        if (elements.containsKey(key))
            elements.get(key).remove(e);
    }

}
