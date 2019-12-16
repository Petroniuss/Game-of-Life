package agh.iet.devs.utils;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.data.Epoch;
import agh.iet.devs.error.SimulationError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StatisticsSaver {

    private final static String directory = GeneralUtils.resources + "statistics" + File.separator;
    private static final Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    public static void saveAll(List<Epoch> history) {
        final var content = gson.toJson(history);
        final var name = "ALL";

        writeJson(name, content);
    }

    public static void save(Epoch epoch) {
        final var content = gson.toJson(epoch);
        final var name = "EPOCH-" + epoch.epoch;

        writeJson(name, content);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeJson(String name, String content) {
        var file = new File(directory + name + ".json");

        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists())
                file.createNewFile();

            var bytes = content.getBytes();

            fop.write(bytes);
            fop.flush();
        } catch (IOException e) {
            throw new SimulationError("Failed to save statitcs.", SimulationError.Phase.RUNTIME);
        }
    }

}
