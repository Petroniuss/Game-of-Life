package agh.iet.devs.elements.food;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.map.MapElementVisitor;

public class Food extends AbstractMapElement {

    public Food(Vector initialPosition, int initialEnergy) {
        super(initialPosition, initialEnergy);
    }

    public void onDeath() {
        notifyOnVanish(this);
    }

    @Override
    public void acceptOnMove(MapElementVisitor visitor, Vector from) {
        // ignore!
    }

    @Override
    public void acceptOnVanish(MapElementVisitor visitor) {
        visitor.onFoodVanish(this);
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
