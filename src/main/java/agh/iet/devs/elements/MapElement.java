package agh.iet.devs.elements;

import agh.iet.devs.data.Vector;

public interface MapElement extends MapElementObservable {

    void onUpdate();

    Vector getPosition();

    int getEnergy();

}
