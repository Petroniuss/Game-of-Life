package agh.iet.devs.utils;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.map.Map;

import java.util.*;


//Todo two different empty position locators for jungle and step
public class EmptyPositionLocatorImpl implements EmptyPositionLocator {

    private final Set<Vector> emptyPositions = new HashSet<>();

    public void addElement(MapElement e) {
        emptyPositions.remove(e.getPosition());
    }

    @Override
    public void onMove(MapElement e, Vector from) {

    }

    @Override
    public void onVanish(MapElement element) {

    }

    @Override
    public Optional<Vector> findWithinRect(Map map, int startX, int endX, int startY, int endY) {
        return Optional.empty();
    }
}
