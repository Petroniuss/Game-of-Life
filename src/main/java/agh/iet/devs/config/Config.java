package agh.iet.devs.config;

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
