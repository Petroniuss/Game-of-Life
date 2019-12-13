package agh.iet.devs.elements.animal;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Direction;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.food.Food;

public class Animal extends AbstractMapElement {

    private final Genome genome;
    private final int generation;

    private Direction orientation = Direction.random();

    private Animal(Vector initialPosition, int initialEnergy, Genome g1, Genome g2,
                   int generation) {
        super(initialPosition, initialEnergy);

        this.genome = new Genome(g1, g2);
        this.generation = generation;
    }

    public Animal(Vector initialPosition, int initialEnergy) {
        super(initialPosition, initialEnergy);

        this.genome = new Genome();
        this.generation = 1;
    }

    public static Animal cross(Animal p1, Animal p2) {
        final var position = p1.currentPosition;
        final var delta1 = p1.currentEnergy / 4;
        final var delta2 = p2.currentEnergy / 4;
        final var gen = Math.max(p1.generation, p2.generation) + 1;

        p1.currentEnergy -= delta1;
        p2.currentEnergy -= delta2;

        return new Animal(position, delta1 + delta2, p1.genome, p2.genome, gen);
    }

    public void eat(Food food) {
        this.currentEnergy += food.getEnergy();
        food.onDeath();
    }

    public boolean eligibleForReproduction() {
        return this.currentEnergy >= Config.getInstance().params.startEnergy / 2;
    }

    public boolean hasDominatingGenome(Genome dominating) {
        return this.genome.equals(dominating);
    }

    @Override
    protected void update() {
        final var prev = currentPosition;

        this.orientation = this.orientation.turn(this.genome.predict());
        this.currentPosition = Config.getInstance().moveCoordinator
                .validateMove(this.currentPosition.add(this.orientation.direction));

        notifyOnMove(this, prev);
    }

    @Override
    public Icon getIcon() {
        final var startEnergy = Config.getInstance().params.startEnergy;
        Icon icon = null;

        if (currentEnergy < startEnergy / 2)
            icon = Icon.DYING_ANIMAL;
        else if (currentEnergy < startEnergy)
            icon = Icon.FERTILE_ANIMAL;
        else
            icon = Icon.HEALTHY_ANIMAL;

        return icon;
    }

    @Override
    public String toString() {
        return "Animal" +
                "\n" + genome +
                "\n" + "Orientation = " + orientation +
                "\n" + "Current Position = " + currentPosition +
                "\n" + "Current Energy = " + currentEnergy +
                "\n" + "Generation = " + generation +
                "\n";
    }

}
