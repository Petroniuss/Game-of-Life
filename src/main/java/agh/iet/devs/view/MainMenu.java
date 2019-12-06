package agh.iet.devs.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class MainMenu extends Menu {

    public static final long MAX_INTERVAL = 500;
    public static final long MIN_INTERVAL = 50;

    private final AtomicBoolean running;
    private final AtomicLong interval;

    private final Button pausePlayButton;

    public MainMenu(AtomicBoolean running, AtomicLong interval) {
        super("Settings");

        this.running = running;
        this.interval = interval;


        final var slider = new Slider(0.0, 1.0, 0.0);
        slider.valueProperty().addListener(this::onSwiped);
        final var label = new Label("Evolution speed");
        final var vbox = new VBox(slider, label);
        final var firstItem = new CustomMenuItem(vbox, false);

        this.pausePlayButton = new Button("Pause");
        this.pausePlayButton.setOnAction(this::onButtonClick);

        final var anotherVBox = new HBox(this.pausePlayButton);
        anotherVBox.setAlignment(Pos.CENTER);

        final var secondItem = new CustomMenuItem(anotherVBox, true);

        getItems().addAll(firstItem, new CustomMenuItem(label), secondItem);
    }

    private void onSwiped(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        interval.set(MIN_INTERVAL + (long) (MAX_INTERVAL * (1 - newValue.doubleValue())));
    }

    private void onButtonClick(ActionEvent e) {
        final var wasRunning = this.running.get();
        this.running.set(!wasRunning);

        if (wasRunning) {
            this.pausePlayButton.setText("Play");
        } else {
            this.pausePlayButton.setText("Pause");
        }
    }

}
