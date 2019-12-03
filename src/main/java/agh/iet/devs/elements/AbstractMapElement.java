package agh.iet.devs.elements;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.map.VanishingListener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapElement implements MapElement, VanishingObservable {

    protected Vector currentPosition;
    protected int currentEnergy;

    private Set<VanishingListener> onVanishListenerSet = new HashSet<>();

    public AbstractMapElement(Vector initialPosition, int initialEnergy) {
        this.currentPosition = initialPosition;
        this.currentEnergy = initialEnergy;
    }

    /**
     * Note that this method should be called by all subtypes.
     */
    @Override
    public void onUpdate() {
        this.currentEnergy -= Config.getInstance().params.moveEnergy;

        if (isAlive())
            update();
    }

    abstract void update();

    @Override
    public Vector getPosition() {
        return currentPosition;
    }

    @Override
    public int getEnergy() {
        return this.currentEnergy;
    }

    @Override
    public void attachListener(VanishingListener listener) {
        onVanishListenerSet.add(listener);
    }

    @Override
    public void detachListener(VanishingListener listener) {
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


