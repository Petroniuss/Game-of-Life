package agh.iet.devs.config;

import agh.iet.devs.data.Epoch;
import agh.iet.devs.map.WorldController;

import java.util.ArrayList;
import java.util.List;

public class SimulationState {

    private int animalCount = Config.getInstance().params.animalsAtStart;
    private int foodCount = 0;
    private int dayCount = 1;
    private int dominatingGen = -1;
    private int[] genesDistribution = new int[8];

    private double averageEnergy = Double.NaN;
    private double lifeExpectancy = Double.NaN;
    private double averageChildren = Double.NaN;

    private final List<Epoch> history = new ArrayList<>(List.of(
            new Epoch(dominatingGen, averageEnergy, lifeExpectancy, averageChildren, animalCount, foodCount, dayCount)
    ));

    private WorldController controller;

    public void setController(WorldController controller) {
        this.controller = controller;
    }

    public void update(int foodCount, int animalCount, double averageEnergy, int dominatingGen,
                       double lifeExpectancy, double avgChildren, int[] genesDistribution){
        this.dayCount += 1;
        this.foodCount = foodCount;
        this.animalCount = animalCount;
        this.averageEnergy = averageEnergy;
        this.dominatingGen = dominatingGen;
        this.lifeExpectancy = lifeExpectancy;
        this.averageChildren = avgChildren;
        this.genesDistribution = genesDistribution;

        history.add(
            new Epoch(dominatingGen, averageEnergy, lifeExpectancy, averageChildren, animalCount, foodCount, dayCount)
        );
    }

    public void showDominating() {
        controller.showDominatingAnimals();
    }

    public Epoch getEpoch(int i) {
        return history.get(i - 1);
    }

    public int[] getGenesDistribution() {
        return genesDistribution;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getDayCount() {
        return dayCount;
    }

    public int getDominatingGen() {
        return dominatingGen;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public double getAverageChildren() {
        return averageChildren;
    }

    public List<Epoch> getHistory() {
        return history;
    }
}
