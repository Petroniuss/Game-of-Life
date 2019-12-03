package agh.iet.devs.config;

import agh.iet.devs.error.SimulationError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParamsParser {
    private static final String defaultPath = "src/main/resources/parameters.json".replace("/", File.separator);

    public static Params parse() {
        return parse(defaultPath);
    }

    public static Params parse(String path) {
        Params params = null;
        try {
            final var json = new String(Files.readAllBytes(Paths.get(path)));
            final var gson = new Gson();
            params = gson.fromJson(json, Params.class);
        } catch (IOException | JsonSyntaxException e) {
            throw new SimulationError(e.getMessage(), SimulationError.Phase.PARSE);
        }

        return params;
    }

}
