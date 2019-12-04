package agh.iet.devs.map;

import agh.iet.devs.data.Vector;

public interface Region {

    /**
     * @return whether position is within region.
     */
    boolean isWithin(Vector vector);

}
