package agh.iet.devs.elements;

import agh.iet.devs.map.OnVanishListener;

public interface VanishingObservable {

    void notifyOnVanishListeners();

    void attachListener(OnVanishListener listener);

    void detachListener(OnVanishListener listener);

}
