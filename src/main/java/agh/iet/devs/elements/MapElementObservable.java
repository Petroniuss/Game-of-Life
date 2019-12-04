package agh.iet.devs.elements;

import agh.iet.devs.data.Vector;
import agh.iet.devs.map.MapElementObserver;
import agh.iet.devs.map.OnMoveListener;

public interface MapElementObservable {

    void notifyOnMove(MapElement e, Vector from);

    void notifyOnVanish(MapElement e);

    void attachListener(MapElementObserver observer);

    void detachListener(MapElementObserver observer);
}
