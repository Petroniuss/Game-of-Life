package agh.iet.devs.map.region;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.Optional;

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
     * @return Position not occupied by any object, empty if no such exists.
     */
    Optional<Vector> emptyPosition();

}
