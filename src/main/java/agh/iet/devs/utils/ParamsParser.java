package agh.iet.devs.utils;

import agh.iet.devs.config.Params;
import agh.iet.devs.error.SimulationError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ParamsParser {
    private static final String defaultParameters = "parameters.json";

    public static Params parse() {
        return parse(defaultParameters);
    }

    public static Params parse(String name) {
        final Params params;
        try {
            final var json = new String(GeneralUtils.fromConfiguration(name));
            final var gson = new Gson();
            params = gson.fromJson(json, Params.class);
        } catch (JsonSyntaxException e) {
            throw new SimulationError(e.getMessage(), SimulationError.Phase.PARSE);
        }

        return params;
    }

}
