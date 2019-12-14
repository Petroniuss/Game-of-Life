package agh.iet.devs.view.menu;

import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static agh.iet.devs.view.controller.ViewConfiguration.MAX_INTERVAL;
import static agh.iet.devs.view.controller.ViewConfiguration.MIN_INTERVAL;

public class SettingsMenu extends VBox {

    private final AtomicBoolean running;
    private final AtomicLong interval;

    private final Button pausePlayButton;

    public SettingsMenu() {
        this.running = ViewConfiguration.getInstance().running;
        this.interval = ViewConfiguration.getInstance().interval;

        final var slider = new Slider(0.0, 1.0, 0.0);
        slider.valueProperty().addListener(this::onSwiped);

        final var label = new Label("Evolution speed");
        final var vbox = new VBox(slider, label);

        vbox.setFillWidth(true);
        vbox.setPrefWidth(ViewConfiguration.SIDE_MENU_WIDTH);

        this.pausePlayButton = new Button("Pause");
        this.pausePlayButton.setGraphic(
                new ImageView(ButtonGraphics.PAUSE.image));
        this.pausePlayButton.setOnAction(this::onButtonClick);

        final var hBox = new VBox(this.pausePlayButton);

        vbox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPrefWidth(ViewConfiguration.SIDE_MENU_WIDTH);

        getChildren().addAll(vbox, hBox);
        setPadding(new Insets(10, 0, 0, 10));
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
        } else {
            this.pausePlayButton.setGraphic(
                    new ImageView(ButtonGraphics.PAUSE.image));
            this.pausePlayButton.setText("Pause");
        }
    }

}
