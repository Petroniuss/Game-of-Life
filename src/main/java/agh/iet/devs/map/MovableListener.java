package agh.iet.devs.map;

import agh.iet.devs.data.Vector;

public interface MovableListener {

//    Or void onMove(IMapElement e, Vector from)

    void onMove(Vector from, Vector to);

}
