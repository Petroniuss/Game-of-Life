package agh.iet.devs.data;

import agh.iet.devs.elements.animal.Genome;

public class Epoch {
    public final Genome dominatingGenome;

    public final double averageEnergy;
    public final double lifeExpectancy;
    public final double averageChildren;

    public final int animalCount;
    public final int foodCount;
    public final int epoch;

    public Epoch(Genome dominatingGenome,
                 double averageEnergy, double lifeExpectancy, double averageChildren,
                 int animalCount, int foodCount, int epoch) {
        this.dominatingGenome = dominatingGenome;
        this.averageEnergy = averageEnergy;
        this.lifeExpectancy = lifeExpectancy;
        this.averageChildren = averageChildren;
        this.animalCount = animalCount;
        this.foodCount = foodCount;
        this.epoch = epoch;
    }

}
