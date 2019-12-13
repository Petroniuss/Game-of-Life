package agh.iet.devs.config;

import agh.iet.devs.view.menu.SettingsMenu;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationState {

    public final AtomicBoolean running = new AtomicBoolean(true);
    public final AtomicLong interval = new AtomicLong(SettingsMenu.MAX_INTERVAL);

    public final AtomicInteger animalCount = new AtomicInteger(Config.getInstance().params.animalsAtStart);
    public final AtomicInteger foodCount = new AtomicInteger(2);
    public final AtomicLong dayCount = new AtomicLong(1);

    @Override
    public String toString() {
        return "SimulationState{" +
                "running=" + running +
                ", interval=" + interval +
                ", animalCount=" + animalCount +
                ", foodCount=" + foodCount +
                ", dayCount=" + dayCount +
                '}';
    }
}
