package agh.iet.devs.map.region;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.map.MapElementObserver;
import agh.iet.devs.utils.GeneralUtils;

import java.util.*;

/**
 * Class implements most of the functionality of a region.
 *
 * The point of this class is to implement finding random position within some area (specified by subclass) in O(1).
 * Basically every operation here, except for initialization is O(1).
 */
public abstract class AbstractRegion implements Region, MapElementObserver {
    protected final Map<Vector, OccupancyValue> positionsOccupancyMap;
    protected final List<Vector> emptyPositions;

    public AbstractRegion(List<Vector> freePositions) {
        this.positionsOccupancyMap = new HashMap<>(freePositions.size());
        this.emptyPositions = new ArrayList<>(freePositions.size());

        freePositions.forEach(this::addEmptyPosition);
    }

    @Override
    public Optional<Vector> emptyPosition() {
        return GeneralUtils.randomFromList(this.emptyPositions);
    }

    @Override
    public List<Vector> emptyPositions() {
        return this.emptyPositions;
    }

    @Override
    public void onMove(MapElement e, Vector from) {
        if (isWithin(from))
            updateAbandonedPosition(from);

        if (isWithin(e.getPosition()))
            updateEnteredPosition(e.getPosition());
    }

    @Override
    public void onVanish(MapElement e) {
        if (isWithin(e.getPosition()))
            updateAbandonedPosition(e.getPosition());

        e.detachListener(this);
    }

    @Override
    public void attachElement(MapElement e) {
        if (isWithin(e.getPosition()))
            updateEnteredPosition(e.getPosition());

        e.attachListener(this);
    }

    /**
     * Should be called when position has been entered.
     */
    private void updateEnteredPosition(Vector position) {
        incrementTotalAt(position);

        if (totalAt(position) == 1)
            removeEmptyPosition(position);
    }

    /**
     * Should be called when position has been left.
     */
    private void updateAbandonedPosition(Vector position) {
        decrementTotalAt(position);

        if (totalAt(position) == 0)
            addEmptyPosition(position);
    }

    private int totalAt(Vector position) {
        return this.positionsOccupancyMap.get(position).total;
    }

    private void decrementTotalAt(Vector position) {
        this.positionsOccupancyMap.computeIfPresent(position,
                (k, v) -> OccupancyValue.create(v.index, v.total - 1));
    }

    private void incrementTotalAt(Vector position) {
        this.positionsOccupancyMap.computeIfPresent(position,
                (k, v) -> OccupancyValue.create(v.index, v.total + 1));
    }

    private void addEmptyPosition(Vector position) {
        final var i = this.emptyPositions.size();

        this.emptyPositions.add(position);
        this.positionsOccupancyMap.put(position, OccupancyValue.create(i, 0));
    }

    /**
     * Removes emptyPosition and updates occupancyMap
     */
    private void removeEmptyPosition(Vector wasEmpty) {
        final var lastI = this.emptyPositions.size() - 1;
        final var last = this.emptyPositions.get(lastI);

        final var i = this.positionsOccupancyMap.get(wasEmpty).index;

        Collections.swap(this.emptyPositions, i, lastI);

        this.emptyPositions.remove(lastI);
        this.positionsOccupancyMap.computeIfPresent(last,
                (k, v) -> OccupancyValue.create(i, v.total));

    }

    /**
     * Inner class used mainly for keeping things simple ;)
     */
    private static class OccupancyValue {
        final int index; // index in emptyPositions
        final int total; // how many mapElements occupy given position

        OccupancyValue(int index, int total) {
            this.index = index;
            this.total = total;
        }

        static OccupancyValue create(int index, int total) {
            return new OccupancyValue(index, total);
        }

        @Override
        public String toString() {
            return "OccupancyValue{" +
                    "index=" + index +
                    ", total=" + total +
                    '}';
        }
    }

}
