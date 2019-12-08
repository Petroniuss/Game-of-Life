package agh.iet.devs.view.controller;

import agh.iet.devs.utils.GeneralUtils;
import javafx.scene.image.Image;

public final class ViewConfiguration {

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    public static final String TITLE = "Game Of Life";

    public static Image icon() {
        return GeneralUtils.fromImages("icon.png");
    }

}
