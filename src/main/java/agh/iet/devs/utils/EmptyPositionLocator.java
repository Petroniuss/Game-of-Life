package agh.iet.devs.utils;

import agh.iet.devs.data.Vector;
import agh.iet.devs.map.Map;
import agh.iet.devs.map.OnMoveListener;
import agh.iet.devs.map.OnVanishListener;

import java.util.Optional;

public interface EmptyPositionLocator extends OnVanishListener, OnMoveListener {

    Optional<Vector> findWithinRect(Map map, int startX, int endX, int startY, int endY);

}
