package agh.iet.devs.config;

import agh.iet.devs.data.Epoch;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.animal.Genome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationState {
    public final Set<Animal> animals = new HashSet<>();

    // Current state
    public final AtomicInteger animalCount = new AtomicInteger(Config.getInstance().params.animalsAtStart);
    public final AtomicInteger foodCount = new AtomicInteger(0);
    public final AtomicLong dayCount = new AtomicLong(1);

    public Genome dominatingGenome = Genome.NIL;
    public double averageEnergy = Double.NaN;
    public double lifeExpectancy = Double.NaN;
    public double averageChildren = Double.NaN;

    public final List<Epoch> history = new ArrayList<>(List.of(new Epoch(
       dominatingGenome, averageEnergy, lifeExpectancy, averageChildren, animalCount.get(), foodCount.get(), dayCount.intValue()
    )));

    public void update(int foodCount, int animalCount, double averageEnergy, Genome dominatingGenome){
        this.dayCount.incrementAndGet();
        this.foodCount.set(foodCount);
        this.animalCount.set(animalCount);
        this.averageEnergy = averageEnergy;
        this.dominatingGenome = dominatingGenome;
        this.lifeExpectancy = calcLifeExpectancy();
        this.averageChildren = avgChildren();

        history.add(new Epoch(
                dominatingGenome, averageEnergy, lifeExpectancy, averageChildren, animalCount, foodCount, dayCount.intValue()
        ));
    }

    public Epoch getEpoch(int i) {
        return history.get(i - 1);
    }

    public void addNewborn(Animal animal) {
        animals.add(animal);
    }

    private double calcLifeExpectancy() {
        return this.animals.stream()
                .filter(animal -> animal.getDeathEpoch() != -1)
                .reduce(0, (acc, e) -> acc + e.getLifetime(), Integer::sum) / (double) deadAnimalsCount();
    }

    private long deadAnimalsCount() {
        return this.animals.stream()
                .filter(animal -> animal.getDeathEpoch() != -1).count();
    }

    private double avgChildren() {
        return animals.stream()
                .map(Animal::getChildren)
                .reduce(Integer::sum)
                .orElse(0) / (double) animals.size();
    }

}
