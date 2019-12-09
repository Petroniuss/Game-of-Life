package agh.iet.devs.map.region;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.map.MapElementObserver;
import agh.iet.devs.utils.GeneralUtils;

import java.util.*;

/**
 * Class implements most of the functionality of a region.
 *
 * Note that many methods' signatures contain synchronized keyword.
 * This is due to the fact that we shuffle emptyPositions to "add even more randomness".
 * To make sure that ui thread does not interfere with shuffle task, we synchronize methods.
 *
 * FIXME it would be better if we stored positions in hashset then finding random element would be done in O(n),
 * FIXME but adding and removing element (which we do multiple time during each frame) would be in O(1)
 */
public abstract class AbstractRegion implements Region, MapElementObserver {
    protected final Map<Vector, Set<MapElement>> elements = new HashMap<>();
    protected final List<Vector> emptyPositions;

    public AbstractRegion(Collection<Vector> freePositions) {
        this.emptyPositions = new ArrayList<>(freePositions);

        final var shuffleTask = new TimerTask() {
            @Override
            public void run() {
                AbstractRegion.this.shuffle();
            }
        };
        final var timer = new Timer("Shuffler!");

        timer.schedule(shuffleTask, 250, 500);
    }

    @Override
    public Set<MapElement> objectsAt(Vector position) {
        return elements.getOrDefault(position, Collections.emptySet());
    }

    @Override
    public Set<Map.Entry<Vector, Set<MapElement>>> objectsInRegion() {
        return new HashSet<>(elements.entrySet());
    }

    @Override
    public synchronized Optional<Vector> emptyPosition() {
        return GeneralUtils.randomFromList(this.emptyPositions);
    }

    @Override
    public synchronized void onMove(MapElement e, Vector from) {
        if (isWithin(from)) {
            elements.get(from).remove(e);

            if (elements.get(from).isEmpty())
                emptyPositions.add(from);
        }

        if (isWithin(e.getPosition()))
            addMapElement(e);
    }

    @Override
    public synchronized void onVanish(MapElement e) {
        final var position = e.getPosition();

        if (isWithin(position))
            removeElement(e);
    }

    @Override
    public synchronized void attachElement(MapElement e) {
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

    private synchronized void shuffle() {
        Collections.shuffle(this.emptyPositions);
    }

}