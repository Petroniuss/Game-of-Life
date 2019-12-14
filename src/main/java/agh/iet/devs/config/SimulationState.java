package agh.iet.devs.config;

import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.animal.Genome;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationState {

    public final AtomicInteger animalCount = new AtomicInteger(Config.getInstance().params.animalsAtStart);
    public final AtomicInteger foodCount = new AtomicInteger(0);
    public final AtomicLong dayCount = new AtomicLong(1);

    public Genome dominatingGenome;
    public double averageEnergy = 0;
    public double lifeExpectancy = 0;
    public Set<Animal> animals = new HashSet<>();

    public void update(int foodCount, int animalCount, double averageEnergy, Genome dominatingGenome){
        this.dayCount.incrementAndGet();
        this.foodCount.set(foodCount);
        this.animalCount.set(animalCount);
        this.averageEnergy = averageEnergy;
        this.dominatingGenome = dominatingGenome;
        this.lifeExpectancy = calcLifeExpectancy();
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



}
