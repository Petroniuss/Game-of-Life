package agh.iet.devs.elements.food;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;

public class Food extends AbstractMapElement {

    public Food(Vector initialPosition, int initialEnergy) {
        super(initialPosition, initialEnergy);
    }

    public void onDeath() {
        notifyOnVanish(this);
    }

    @Override
    public Icon getIcon() {
        return Icon.GRASS;
    }

    @Override
    protected void update() {
        // ignore!
    }

    @Override
    public String toString() {
        return "Food" +
                "\n" + "CurrentPosition = " + currentPosition +
                "\n" + "CurrentEnergy = " + currentEnergy;
    }
}
