package agh.iet.devs.map.region;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Region {

    /**
     * Add new element to region.
     */
    void attachElement(MapElement e);

    /**
     * @return Whether position is within region.
     */
    boolean isWithin(Vector position);

    /**
     * @return Set with elements occupying position, empty if none.
     */
    Set<MapElement> objectsAt(Vector position);


    /**
     * @return Set of all objects on the region.
     * Note that it should return a shallow copy so that iterator wouldn't fail during updates.
     */
    Set<Map.Entry<Vector, Set<MapElement>>> objectsInRegion();

    /**
     * @return Position not occupied by any object, empty if no such exists.
     */
    Optional<Vector> emptyPosition();

}
