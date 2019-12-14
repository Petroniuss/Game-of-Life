package agh.iet.devs.view.controller;

import agh.iet.devs.config.Config;
import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.view.menu.SettingsMenu;
import javafx.scene.image.Image;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class ViewConfiguration {
    private static ViewConfiguration instance;

    public static final String TITLE = "Game Of Life";

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    public static final long MAX_INTERVAL = 150;
    public static final long MIN_INTERVAL = 50;

    public static final int SIDE_MENU_WIDTH = 200;
    public static final int SIDE_MENU_HEIGHT = WINDOW_HEIGHT;

    public static final int SIMULATION_WIDTH;
    public static final int SIMULATION_HEIGHT;

    public final AtomicLong interval = new AtomicLong(SettingsMenu.MAX_INTERVAL);
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

    public static Image icon() {
        return GeneralUtils.fromImages("icon.png");
    }
}
