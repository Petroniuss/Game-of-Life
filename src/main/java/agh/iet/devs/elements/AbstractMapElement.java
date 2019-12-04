package agh.iet.devs.elements;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.map.MapElementObserver;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapElement implements MapElement {

    protected Vector currentPosition;
    protected int currentEnergy;

    private Set<MapElementObserver> observers = new HashSet<>();

    public AbstractMapElement(Vector initialPosition, int initialEnergy) {
        this.currentPosition = initialPosition;
        this.currentEnergy = initialEnergy;
    }

    @Override
    public void onUpdate() {
        this.currentEnergy -= Config.getInstance().params.moveEnergy;

        if (isAlive())
            update();
    }

    /**
     * This method should specify behaviour of a subclass
     * Note that it will only be executed if element is alive.
     */
    protected abstract void update();

    @Override
    public Vector getPosition() {
        return currentPosition;
    }

    @Override
    public int getEnergy() {
        return this.currentEnergy;
    }

    @Override
    public void notifyOnMove(MapElement e, Vector from) {
        observers.forEach(observer -> observer.onMove(e, from));
    }

    @Override
    public void notifyOnVanish(MapElement e) {
        observers.forEach(observer -> observer.onVanish(e));
    }

    @Override
    public void attachListener(MapElementObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachListener(MapElementObserver observer) {
        observers.remove(observer);
    }

    /**
     * Method is responsible for checking whether element should be detached and if so detaching it.
     */
    private boolean isAlive() {
        if (this.currentEnergy <= 0) {
            notifyOnVanish(this);
        }

        return this.currentEnergy > 0;
    }

}


