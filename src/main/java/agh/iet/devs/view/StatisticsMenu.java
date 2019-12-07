package agh.iet.devs.view;

import agh.iet.devs.utils.GeneralUtils;
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

    public StatisticsMenu(AtomicInteger animalCount, AtomicInteger foodCount, AtomicLong dayCount) {
        super("Statistics");

        this.animalCount = animalCount;
        this.foodCount = foodCount;
        this.dayCount = dayCount;

        this.dayLabel = new Label("Day: " + dayCount.get());
        this.animalLabel = new Label("Animals: " + animalCount.get());
        this.foodLabel = new Label("Food: " + foodCount.get());

        setGraphic(new ImageView(GeneralUtils.fromImages("bar-chart.png")));

        getItems().addAll(
                new CustomMenuItem(dayLabel),
                new CustomMenuItem(animalLabel),
                new CustomMenuItem(foodLabel));
    }

    public void update() {
        this.dayLabel.setText("Day: " + dayCount.get());
        this.animalLabel.setText("Animals: " + animalCount.get());
        this.foodLabel.setText("Food: " + foodCount.get());
    }

}
