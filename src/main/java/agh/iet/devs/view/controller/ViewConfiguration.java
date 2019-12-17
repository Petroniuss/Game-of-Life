package agh.iet.devs.view.controller;

import agh.iet.devs.config.Config;
import agh.iet.devs.utils.GeneralUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class ViewConfiguration {
    private static ViewConfiguration instance;

    public static final String TITLE = "Game of Life";

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    public static final long MAX_INTERVAL = 150;
    public static final long MIN_INTERVAL = 50;

    public static final int SIDE_MENU_WIDTH = 250;
    public static final int SIDE_MENU_HEIGHT = WINDOW_HEIGHT;

    public static final int SIMULATION_WIDTH;
    public static final int SIMULATION_HEIGHT;

    public final AtomicLong interval = new AtomicLong(MIN_INTERVAL);
    public final AtomicBoolean running = new AtomicBoolean(true);

    static {
        final var config = Config.getInstance();

        var w = WINDOW_WIDTH - SIDE_MENU_WIDTH;
        var h = WINDOW_HEIGHT;

        if (config.params.parallel)
            w /= 2;

        SIMULATION_WIDTH = w;
        SIMULATION_HEIGHT = h;
    }

    public static ViewConfiguration getInstance() {
        if (instance == null)
            instance = new ViewConfiguration();

        return instance;
    }

    public enum ButtonGraphics {
        PLAY("play-btn.png"),
        PAUSE("pause-button.png"),
        SAVE("save-icon.png"),
        ARROW("arrow.png");

        public final Image image;
        ButtonGraphics(String name) {
            this.image = GeneralUtils.fromImages(name);
        }

        public ImageView toImageView() {
            return new ImageView(image);
        }
    }

    public static Image icon() {
        return GeneralUtils.fromImages("icon.png");
    }
}
