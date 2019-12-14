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
 *
 * FIXME - there's clearly a bug!
 */
public abstract class AbstractRegion implements Region, MapElementObserver {
    protected final Map<Vector, OccupancyValue> positionsOccupancyMap;
    protected final List<Vector> emptyPositions;

    public AbstractRegion(List<Vector> freePositions) {
        this.positionsOccupancyMap = new HashMap<>(freePositions.size());
        this.emptyPositions = new ArrayList<>(freePositions.size());

        for (int i = 0; i < freePositions.size(); i++) {
            final var key = freePositions.get(i);

            this.emptyPositions.add(i, key);
            this.positionsOccupancyMap.put(key, OccupancyValue.create(i, 0));
        }

        System.out.println(this.emptyPositions);
        System.out.println(this.positionsOccupancyMap);
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
        this.positionsOccupancyMap.computeIfPresent(position,
                (k, v) -> OccupancyValue.create(v.index, v.total + 1));

        System.out.println("ENTERED " + position);
        System.out.println(this.emptyPositions);
        System.out.println(this.positionsOccupancyMap);

        if (this.positionsOccupancyMap.get(position).total == 1) {
            final var lastIndex = this.emptyPositions.size() - 1;
            final var last = this.emptyPositions.get(lastIndex);
            final var index = this.positionsOccupancyMap.get(position).index;

            Collections.swap(this.emptyPositions, index, lastIndex);

            this.emptyPositions.remove(lastIndex);
            positionsOccupancyMap.computeIfPresent(last, (k, v) -> OccupancyValue.create(index, v.total));
        }
    }

    /**
     * Should be called when position has been left.
     */
    private void updateAbandonedPosition(Vector position) {
        final var index = this.emptyPositions.size();

        this.positionsOccupancyMap.computeIfPresent(position,
                (key, v) -> OccupancyValue.create(-1, v.total - 1));

        if (this.positionsOccupancyMap.get(position).total == 0) {
            this.positionsOccupancyMap.put(position, OccupancyValue.create(index, 0));
            this.emptyPositions.add(index, position);
        }
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
