package agh.iet.devs.config;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;
import agh.iet.devs.utils.ParamsParser;

/**
 * Singleton responsible for holding information about simulation parameters.
 */
public class Config {
    private static Config instance;

    public final Params params;
    public final MoveCoordinator moveCoordinator;
    public final String name = "Game Of Life";

    private Config() {
        this.params = ParamsParser.parse();
        this.moveCoordinator = new MoveCoordinator(params.width, params.height);
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();

        return instance;
    }

    public Rect outerBounds() {
        final Vector lowerLeft = Vector.create(0, 0);
        final Vector upperRight = Vector.create(params.width - 1, params.height - 1);

        return new Rect(lowerLeft, upperRight);
    }

    public Rect jungleBounds() {
        final var jungleWidth = (int) (params.width * params.jungleRatio);
        final var jungleHeight = (int) (params.height * params.jungleRatio);

        final var startX = (params.width - jungleWidth) / 2;
        final var endX = startX + jungleWidth;
        final var startY = (params.height - jungleHeight) / 2;
        final var endY = startY + jungleHeight;

        return new Rect(Vector.create(startX, startY), Vector.create(endX, endY));
    }
}
