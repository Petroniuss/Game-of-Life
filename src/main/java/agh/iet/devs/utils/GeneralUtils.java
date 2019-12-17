package agh.iet.devs.utils;

import agh.iet.devs.error.SimulationError;
import agh.iet.devs.error.SimulationError.Phase;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public final class GeneralUtils {
    public static final String resources = "src/main/resources/".replace("/", File.separator);

    private static final String images = resources + File.separator + "images" + File.separator;
    private static final String configuration = resources + File.separator + "configuration" + File.separator;
    private static final Random random = new Random();

    public static Image fromImages(String name) {
        try {
            final var input = new FileInputStream(images + name);
            return new Image(input);
        } catch (FileNotFoundException ignore) {}

        throw new SimulationError("No way...", Phase.INIT);
    }

    public static byte[] fromConfiguration(String name) {
        try {
            return Files.readAllBytes(Paths.get(configuration + name));
        } catch (IOException ignore) {}

        throw new SimulationError("No way...", Phase.INIT);
    }

    public static String currentTime() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }

    public static <E> Optional<E> randomFromList(List<E> collection) {
        final var size = collection.size();
        if (size < 1)
            return Optional.empty();

        final var i = new Random().nextInt(size);
        return Optional.of(collection.get(i));
    }

    public static <E> Optional<E> randomFromIterable(Collection<E> e) {
        final var size = e.size();
        if (size < 1)
            return Optional.empty();

        var i = random.nextInt(size);
        for (E el : e) {
            if (i-- == 0)
                return Optional.of(el);
        }

        throw new SimulationError("No way...", Phase.RUNTIME);
    }

    public static int maxElementFromArray(int[] array) {
        if (array.length < 1)
            throw new IllegalArgumentException("Array should have at least 1 element");

        int i = 0;
        for (int j = 1; j < array.length; j++) {
            if (array[i] < array[j])
                i = j;
        }

        return i;
    }
}
