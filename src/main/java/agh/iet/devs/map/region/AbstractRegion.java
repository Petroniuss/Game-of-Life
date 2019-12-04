package agh.iet.devs.map.region;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.map.MapElementObserver;

import java.util.*;

public abstract class AbstractRegion implements Region, MapElementObserver {
    protected final Map<Vector, Set<MapElement>> elements = new HashMap<>();
    protected final Set<Vector> emptyPositions;

    public AbstractRegion(Collection<Vector> freePositions) {
        this.emptyPositions = new HashSet<>(freePositions);
    }

    @Override
    public Set<MapElement> objectsAt(Vector position) {
        return elements
                .getOrDefault(position, Collections.emptySet());
    }

    @Override
    public Set<Map.Entry<Vector, Set<MapElement>>> objectsInRegion() {
        return elements.entrySet();
    }

    @Override
    public Optional<Vector> emptyPosition() {
        return emptyPositions.stream().findAny();
    }

    @Override
    public void onMove(MapElement e, Vector from) {
        if (isWithin(from)) {
            elements.get(from).remove(e);

            if (elements.get(from).isEmpty())
                emptyPositions.add(from);
        }

        if (isWithin(e.getPosition()))
            addMapElement(e);
    }

    @Override
    public void onVanish(MapElement e) {
        final var position = e.getPosition();

        if (isWithin(position))
            removeElement(e);

        e.detachListener(this); // Garbage collector would have taken care of it anyway..
    }

    @Override
    public void attachElement(MapElement e) {
        if (isWithin(e.getPosition()))
            addMapElement(e);

        e.attachListener(this);
    }

    /**
     * Note that this method does not check if element is within region.
     */
    protected void addMapElement(MapElement e) {
        final var key = e.getPosition();

        if (elements.containsKey(key))
            elements.get(key).add(e);
        else
            elements.put(key, new HashSet<>(Collections.singleton(e)));

        emptyPositions.remove(key);
    }

    protected void removeElement(MapElement e) {
        final var key = e.getPosition();

        if (elements.containsKey(key)) {
            elements.get(key).remove(e);

            if (elements.get(key).isEmpty())
                emptyPositions.add(key);
        }
    }

}
