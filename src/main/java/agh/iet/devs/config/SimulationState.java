package agh.iet.devs.config;

import agh.iet.devs.data.Epoch;
import agh.iet.devs.map.WorldController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationState {
    // Current state
    public final AtomicInteger animalCount = new AtomicInteger(Config.getInstance().params.animalsAtStart);
    public final AtomicInteger foodCount = new AtomicInteger(0);
    public final AtomicLong dayCount = new AtomicLong(1);

    public int dominatingGen = -1;
    public double averageEnergy = Double.NaN;
    public double lifeExpectancy = Double.NaN;
    public double averageChildren = Double.NaN;

    public final List<Epoch> history = new ArrayList<>(List.of(new Epoch(
       dominatingGen, averageEnergy, lifeExpectancy, averageChildren, animalCount.get(), foodCount.get(), dayCount.intValue()
    )));

    // We need to maintain bidirectional relationship.
    private WorldController controller;

    public void setController(WorldController controller) {
        this.controller = controller;
    }

    public void update(int foodCount, int animalCount, double averageEnergy, int dominatingGen,
                       double lifeExpectancy, double avgChildren){
        this.dayCount.incrementAndGet();
        this.foodCount.set(foodCount);
        this.animalCount.set(animalCount);
        this.averageEnergy = averageEnergy;
        this.dominatingGen = dominatingGen;
        this.lifeExpectancy = lifeExpectancy;
        this.averageChildren = avgChildren;

        history.add(new Epoch(
                dominatingGen, averageEnergy, lifeExpectancy, averageChildren, animalCount, foodCount, dayCount.intValue()
        ));
    }

    public Epoch getEpoch(int i) {
        return history.get(i - 1);
    }

    public void showDominating() {
        controller.showDominatingAnimals();
    }

}
