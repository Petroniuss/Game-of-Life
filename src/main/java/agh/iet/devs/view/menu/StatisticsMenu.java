package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsMenu extends VBox {

    private final AtomicInteger animalCount;
    private final AtomicInteger foodCount;
    private final AtomicLong dayCount;

    private final Label dayLabel;
    private final Label animalLabel;
    private final Label foodLabel;

    public StatisticsMenu(SimulationState state) {
        this.animalCount = state.animalCount;
        this.foodCount = state.foodCount;
        this.dayCount = state.dayCount;

        this.dayLabel = new Label("Day: " + dayCount.get());
        this.animalLabel = new Label("Animals: " + animalCount.get());
        this.foodLabel = new Label("Food: " + foodCount.get());


        getChildren().addAll(
                dayLabel,
                animalLabel,
                foodLabel
        );

        setPadding(new Insets(10, 25, 25, 10));
    }

    public void onUpdate() {
        this.dayLabel.setText("Day: " + dayCount.get());
        this.animalLabel.setText("Animals: " + animalCount.get());
        this.foodLabel.setText("Food: " + foodCount.get());
    }

}
