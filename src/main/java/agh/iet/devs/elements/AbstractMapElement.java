package agh.iet.devs.elements;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.map.OnVanishListener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapElement implements MapElement, VanishingObservable {

    protected Vector currentPosition;
    protected int currentEnergy;

    private Set<OnVanishListener> onVanishListenerSet = new HashSet<>();

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
    public void attachListener(OnVanishListener listener) {
        onVanishListenerSet.add(listener);
    }

    @Override
    public void detachListener(OnVanishListener listener) {
        onVanishListenerSet.remove(listener);
    }

    @Override
    public void notifyOnVanishListeners() {
        onVanishListenerSet.forEach(listener -> listener.onVanish(this));
    }

    /**
     * Method is responsible for checking whether element should be detached and if so detaching it.
     */
    private boolean isAlive() {
        if (this.currentEnergy <= 0) {
            notifyOnVanishListeners();
        }

        return this.currentEnergy > 0;
    }

}


