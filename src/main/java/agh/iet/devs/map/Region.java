package agh.iet.devs.map;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

import java.util.Optional;

public interface Region {

    /**
     * @return whether position is within region.
     */
    boolean isWithin(Vector vector);

    /**
     * @return element on given position if the position is occupied and within Region else empty.
     */
    Optional<MapElement> objectAt(Vector vector);

}
