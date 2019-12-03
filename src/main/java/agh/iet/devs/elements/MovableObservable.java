package agh.iet.devs.elements;

import agh.iet.devs.map.MovableListener;
import agh.iet.devs.utils.Observable;

public interface MovableObservable extends Observable<MovableListener> {

     void notifyOnMoveListeners();

}
