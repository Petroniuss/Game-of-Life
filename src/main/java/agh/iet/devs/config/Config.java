package agh.iet.devs.config;

import agh.iet.devs.utils.ParamsParser;

/**
 * Singleton responsible for holding information about simulation parameters.
 */
public class Config {
    private static Config instance;

    public final Params params;

    private Config() {
        this.params = ParamsParser.parse();
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();

        return instance;
    }
}
