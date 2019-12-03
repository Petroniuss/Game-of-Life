package agh.iet.devs.map;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;

public interface Map {

    void attachElement(MapElement element);

    boolean isOccupied(Vector position);

}
