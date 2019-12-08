package agh.iet.devs.view.menu;

import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.config.SimulationState;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class SettingsMenu extends Menu {

    public static final long MAX_INTERVAL = 150;
    public static final long MIN_INTERVAL = 50;

    private final AtomicBoolean running;
    private final AtomicLong interval;

    private final Button pausePlayButton;

    public SettingsMenu(SimulationState state) {
        super("Settings");

        this.running = state.running;
        this.interval = state.interval;

        final var slider = new Slider(0.0, 1.0, 0.0);
        slider.valueProperty().addListener(this::onSwiped);

        final var label = new Label("Evolution speed");
        final var vbox = new VBox(slider, label);
        final var firstItem = new CustomMenuItem(vbox, false);

        this.pausePlayButton = new Button("Pause");
        this.pausePlayButton.setGraphic(
                new ImageView(ButtonGraphics.PAUSE.image));
        this.setGraphic(
                new ImageView(ButtonGraphics.PLAY.image));
        this.pausePlayButton.setOnAction(this::onButtonClick);

        final var hBox = new HBox(this.pausePlayButton);
        final var secondItem = new CustomMenuItem(hBox, true);

        vbox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        getItems().addAll(firstItem, secondItem);
    }

    private void onSwiped(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        interval.set(MIN_INTERVAL + (long) (MAX_INTERVAL * (1 - newValue.doubleValue())));
    }

    private void onButtonClick(ActionEvent e) {
        onPausePlayEvent();
    }

    private enum ButtonGraphics {
        PLAY("play-btn.png"),
        PAUSE("pause-button.png");

        final Image image;
        ButtonGraphics(String name) {
            this.image = GeneralUtils.fromImages(name);
        }
    }

    public void onPausePlayEvent() {
        final var wasRunning = this.running.get();
        this.running.set(!wasRunning);

        if (wasRunning) {
            this.pausePlayButton.setText("Play");
            this.pausePlayButton.setGraphic
                    (new ImageView(ButtonGraphics.PLAY.image));
            this.setGraphic(
                    new ImageView(ButtonGraphics.PAUSE.image));
        } else {
            this.setGraphic(
                    new ImageView(ButtonGraphics.PLAY.image));
            this.pausePlayButton.setGraphic(
                    new ImageView(ButtonGraphics.PAUSE.image));
            this.pausePlayButton.setText("Pause");
        }
    }

}
