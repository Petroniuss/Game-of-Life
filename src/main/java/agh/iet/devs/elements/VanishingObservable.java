package agh.iet.devs.elements;

import agh.iet.devs.map.VanishingListener;
import agh.iet.devs.utils.Observable;

public interface VanishingObservable extends Observable<VanishingListener> {

    void notifyOnVanishListeners();

}
