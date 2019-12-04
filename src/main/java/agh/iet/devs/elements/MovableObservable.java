package agh.iet.devs.elements;

import agh.iet.devs.data.Vector;
import agh.iet.devs.map.OnMoveListener;

@Deprecated
public interface MovableObservable {

     void notifyOnMoveListeners(MapElement e, Vector from);

    void attachListener(OnMoveListener listener);

    void detachListener(OnMoveListener listener);

}
