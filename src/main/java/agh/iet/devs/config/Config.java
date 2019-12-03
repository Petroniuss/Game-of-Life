package agh.iet.devs.config;

import agh.iet.devs.data.Params;
import agh.iet.devs.utils.ParamsParser;

/**
 * Singleton responsible for holding information about simulation parameters.
 */
public class Config {
    private static Config instance;

    public final Params params;
    public final MoveCoordinator moveCoordinator;

    private Config() {
        this.params = ParamsParser.parse();
        this.moveCoordinator = new MoveCoordinator(params.width, params.height);
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();

        return instance;
    }
}
