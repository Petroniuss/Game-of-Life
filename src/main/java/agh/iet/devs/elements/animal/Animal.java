package agh.iet.devs.elements.animal;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Direction;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.MapElementVisitor;

public class Animal extends AbstractMapElement {

    private final Genome genome;
    private final int generation;
    private final int bornEpoch;

    private Direction orientation = Direction.random();
    private int children = 0;

    private Animal(Vector initialPosition, int initialEnergy, Animal p1, Animal p2,
                   int generation, int epoch) {
        super(initialPosition, initialEnergy);

        this.genome = new Genome(p1.genome, p2.genome);
        this.generation = generation;
        this.bornEpoch = epoch;

        view.updateTooltip(toString());
    }

    public Animal(Vector initialPosition, int initialEnergy) {
        super(initialPosition, initialEnergy);

        this.genome = new Genome();
        this.bornEpoch = 1;
        this.generation = 1;
    }

    public static Animal cross(Animal p1, Animal p2, int epoch) {
        final var position = p1.currentPosition;
        final var delta1 = p1.currentEnergy / 4;
        final var delta2 = p2.currentEnergy / 4;
        final var gen = Math.max(p1.generation, p2.generation) + 1;

        p1.currentEnergy -= delta1;
        p2.currentEnergy -= delta2;

        final var kiddo = new Animal(position, delta1 + delta2, p1, p2, gen, epoch);

        p1.children += 1;
        p2.children += 1;

        return kiddo;
    }

    public void eat(Food food) {
        this.currentEnergy += food.getEnergy();
        food.onDeath();
    }

    public boolean eligibleForReproduction() {
        return this.currentEnergy >= Config.getInstance().params.startEnergy / 2;
    }

    @Override
    public void onDeath() {
        notifyOnVanish(this);
    }

    @Override
    public void acceptOnMove(MapElementVisitor visitor, Vector from) {
        visitor.onAnimalMove(this, from);
    }

    @Override
    public void acceptOnVanish(MapElementVisitor visitor) {
        visitor.onAnimalVanish(this);
    }

    public int dominatingGene() {
        return genome.dominatingGene();
    }

    @Override
    protected void update() {
        final var prev = currentPosition;

        this.orientation = this.orientation.turn(this.genome.predict());
        this.currentPosition = AbstractMapElement.validate(
                this.currentPosition.add(this.orientation.direction));

        notifyOnMove(this, prev);
    }

    public void markAsDominating() {
        this.view.updateIcon(Icon.DOMINATING_ANIMAL);
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

    public int getChildren() {
        return children;
    }

    public int getLifetime(int currentEpoch) {
        return currentEpoch - bornEpoch;
    }

    public Genome getGenome() {
        return this.genome;
    }

    @Override
    public String toString() {
        return "Animal" +
                "\n" + "Genes = " + genome +
                "\n" + "Orientation = " + orientation +
                "\n" + "Current Position = " + currentPosition +
                "\n" + "Current Energy = " + currentEnergy +
                "\n" + "Children = " + children +
                "\n" + "Generation = " + generation +
                "\n";
    }

}
