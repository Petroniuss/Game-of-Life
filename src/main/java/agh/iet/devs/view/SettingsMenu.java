package agh.iet.devs.view;

import agh.iet.devs.utils.GeneralUtils;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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

    public SettingsMenu(AtomicBoolean running, AtomicLong interval) {
        super("Settings");

        this.running = running;
        this.interval = interval;

        final var slider = new Slider(0.0, 1.0, 0.0);
        slider.valueProperty().addListener(this::onSwiped);

        final var label = new Label("Evolution speed");
        label.setLabelFor(slider);
        final var vbox = new VBox(slider, label);
        final var firstItem = new CustomMenuItem(vbox, false);

        this.pausePlayButton = new Button("Pause");
        this.pausePlayButton.setGraphic(new ImageView(ButtonGraphics.PAUSE.image));
        this.setGraphic(new ImageView(ButtonGraphics.PLAY.image));
        this.pausePlayButton.setOnAction(this::onButtonClick);

        final var anotherVBox = new HBox(this.pausePlayButton);
        final var secondItem = new CustomMenuItem(anotherVBox, true);

        getItems().addAll(firstItem, new CustomMenuItem(label), secondItem);
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
            this.pausePlayButton.setGraphic(new ImageView(ButtonGraphics.PLAY.image));
            this.setGraphic(new ImageView(ButtonGraphics.PAUSE.image));
        } else {
            this.setGraphic(new ImageView(ButtonGraphics.PLAY.image));
            this.pausePlayButton.setGraphic(new ImageView(ButtonGraphics.PAUSE.image));
            this.pausePlayButton.setText("Pause");
        }
    }

}
