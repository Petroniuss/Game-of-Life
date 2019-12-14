package agh.iet.devs.config;

import agh.iet.devs.elements.animal.Genome;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationState {

    public final AtomicInteger animalCount = new AtomicInteger(Config.getInstance().params.animalsAtStart);
    public final AtomicInteger foodCount = new AtomicInteger(0);
    public final AtomicLong dayCount = new AtomicLong(1);
    public Genome dominatingGenome;

    public void update(int foodCount, int animalCount) {
        this.dayCount.incrementAndGet();
        this.foodCount.set(foodCount);
        this.animalCount.set(animalCount);
    }

    @Override
    public String toString() {
        return "SimulationState{" +
                "animalCount=" + animalCount +
                ", foodCount=" + foodCount +
                ", dayCount=" + dayCount +
                '}';
    }
}
