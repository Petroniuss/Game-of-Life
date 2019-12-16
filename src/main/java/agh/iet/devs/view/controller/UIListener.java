package agh.iet.devs.view.controller;

import agh.iet.devs.elements.MapElement;
import agh.iet.devs.map.MapElementObserver;

public interface UIListener extends MapElementObserver {

    void attach(MapElement e);

}
