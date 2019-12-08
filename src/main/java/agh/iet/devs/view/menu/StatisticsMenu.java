package agh.iet.devs.view.menu;

import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.config.SimulationState;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsMenu extends Menu {
    private final AtomicInteger animalCount;
    private final AtomicInteger foodCount;
    private final AtomicLong dayCount;

    private final Label dayLabel;
    private final Label animalLabel;
    private final Label foodLabel;

    public StatisticsMenu(SimulationState state) {
        super("Statistics");

        this.animalCount = state.animalCount;
        this.foodCount = state.foodCount;
        this.dayCount = state.dayCount;

        this.dayLabel = new Label("Day: " + dayCount.get());
        this.animalLabel = new Label("Animals: " + animalCount.get());
        this.foodLabel = new Label("Food: " + foodCount.get());

        setGraphic(new ImageView(GeneralUtils.fromImages("bar-chart.png")));

        getItems().addAll(
                new CustomMenuItem(dayLabel),
                new CustomMenuItem(animalLabel),
                new CustomMenuItem(foodLabel));
    }

    public void onUpdate() {
        this.dayLabel.setText("Day: " + dayCount.incrementAndGet());
        this.animalLabel.setText("Animals: " + animalCount.get());
        this.foodLabel.setText("Food: " + foodCount.get());
    }

}
