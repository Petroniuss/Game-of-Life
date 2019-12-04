package agh.iet.devs.map;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.Map;
import java.util.*;

public abstract class AbstractRegion implements Region, OnMoveListener, OnVanishListener {
    protected Map<Vector, Set<MapElement>> elements = new HashMap<>();

    @Override
    public Set<MapElement> objectsAt(Vector position) {
        return elements
                .getOrDefault(position, Collections.emptySet());
    }

    @Override
    public Set<MapElement> objectsInRegion() {
        return elements.values().stream()
                .reduce(new HashSet<>(), (a, e) -> {
                    a.addAll(e);
                    return a;
                });
    }

    @Override
    public void onMove(MapElement e, Vector from) {
        if (isWithin(from))
            elements.get(from).remove(e);

        if (isWithin(e.getPosition()))
            addMapElement(e);

    }

    @Override
    public void onVanish(MapElement e) {
        final var position = e.getPosition();

        if (isWithin(position))
            removeElement(e);

    }

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
