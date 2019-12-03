package agh.iet.devs.config;

import agh.iet.devs.error.SimulationError;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParamsParser {
    private static final String defaultPath = "src/resources/parameters.json".replace("/", System.lineSeparator());

    public static Params parse() {
        return parse(defaultPath);
    }

    public static Params parse(String path) {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new SimulationError(e.getMessage(), SimulationError.Phase.PARSE);
        }
        final Gson gson = new Gson();

        return gson.fromJson(json, Params.class);
    }

}
