package agh.iet.devs.elements.animal;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Direction;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.MapElementVisitor;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement {

    private final Genome genome;
    private final int generation;
    private final int bornEpoch;
    private int deathEpoch = -1;

    private int children = 0;
    private int progeny = 0;

    private final Animal p1;
    private final Animal p2;

    private Direction orientation = Direction.random();

    private Animal(Vector initialPosition, int initialEnergy, Animal p1, Animal p2,
                   int generation, int epoch) {
        super(initialPosition, initialEnergy);

        this.bornEpoch = epoch;
        this.genome = new Genome(p1.genome, p2.genome);
        this.generation = generation;

        this.p1 = p1;
        this.p2 = p2;
    }

    public Animal(Vector initialPosition, int initialEnergy) {
        super(initialPosition, initialEnergy);

        this.bornEpoch = 1;
        this.genome = new Genome();
        this.generation = 1;

        this.p1 = null;
        this.p2 = null;
    }

    public static Animal cross(Animal p1, Animal p2, int epoch) {
        final var position = p1.currentPosition;
        final var delta1 = p1.currentEnergy / 4;
        final var delta2 = p2.currentEnergy / 4;
        final var gen = Math.max(p1.generation, p2.generation) + 1;

        p1.currentEnergy -= delta1;
        p2.currentEnergy -= delta2;

        final var kiddo = new Animal(position, delta1 + delta2, p1, p2, gen, epoch);

        // FIXME -- here i should update parents children stats.

        return kiddo;
    }

    public void eat(Food food) {
        this.currentEnergy += food.getEnergy();
        food.onDeath();
    }

    public boolean eligibleForReproduction() {
        return this.currentEnergy >= Config.getInstance().params.startEnergy / 2;
    }

    public Genome getGenome() {
        return this.genome;
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

    public int getLifetime() {
        return deathEpoch - bornEpoch;
    }

    public int getBornEpoch() {
        return bornEpoch;
    }

    public int getDeathEpoch() {
        return deathEpoch;
    }

    public void setDeathEpoch(int deathEpoch) {
        this.deathEpoch = deathEpoch;
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
                "\n" + "Genes = " + genome +
                "\n" + "Orientation = " + orientation +
                "\n" + "Current Position = " + currentPosition +
                "\n" + "Current Energy = " + currentEnergy +
                "\n" + "Generation = " + generation +
                "\n";
    }

}
