package agh.iet.devs.elements.animal;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Direction;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.MovableObservable;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.OnMoveListener;

import java.util.HashSet;
import java.util.Set;

public class Animal extends AbstractMapElement implements MovableObservable {

    private final Set<OnMoveListener> onMoveListenerSet = new HashSet<>();
    private final Genome genome;

    private Direction orientation = Direction.random();

    private Animal(Vector initialPosition, int initialEnergy, Genome g1, Genome g2) {
        super(initialPosition, initialEnergy);

        this.genome = new Genome(g1, g2);
    }

    public Animal(Vector initialPosition, int initialEnergy) {
        super(initialPosition, initialEnergy);

        this.genome = new Genome();
    }

    public static Animal cross(Animal p1, Animal p2) {
        final var position = p1.currentPosition;
        final var delta1 = p1.currentEnergy / 4;
        final var delta2 = p2.currentEnergy / 4;

        p1.currentEnergy -= delta1;
        p2.currentEnergy -= delta2;

        return new Animal(position, delta1 + delta2, p1.genome, p2.genome);
    }

    public void eat(Food food) {
        this.currentEnergy += food.getEnergy();
    }

    @Override
    protected void update() {
        final var prev = currentPosition;

        this.orientation = this.orientation.turn(this.genome.predict());
        this.currentPosition = Config.getInstance().moveCoordinator
                .validateMove(this.currentPosition.add(this.orientation.direction));

        notifyOnMoveListeners(this, prev);
    }

    @Override
    public void notifyOnMoveListeners(MapElement e, Vector prev) {
        onMoveListenerSet.forEach(listener -> listener.onMove(e, prev));
    }

    @Override
    public void attachListener(OnMoveListener listener) {
        onMoveListenerSet.add(listener);
    }

    @Override
    public void detachListener(OnMoveListener listener) {
        onMoveListenerSet.remove(listener);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "onMoveListenerSet=" + onMoveListenerSet +
                ", genome=" + genome +
                ", orientation=" + orientation +
                ", currentPosition=" + currentPosition +
                ", currentEnergy=" + currentEnergy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;

        Animal animal = (Animal) o;

        if (!onMoveListenerSet.equals(animal.onMoveListenerSet)) return false;
        if (!genome.equals(animal.genome)) return false;
        return orientation == animal.orientation;
    }

    @Override
    public int hashCode() {
        int result = onMoveListenerSet.hashCode();
        result = 31 * result + genome.hashCode();
        result = 31 * result + orientation.hashCode();
        return result;
    }
}
