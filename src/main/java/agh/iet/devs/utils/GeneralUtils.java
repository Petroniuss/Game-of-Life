package agh.iet.devs.utils;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public final class GeneralUtils {

    private static final Random random = new Random();

    public static Image fromResources(String name) {
        FileInputStream input = null;

        try {
            input = new FileInputStream("src/main/resources/images/" + name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Image(input);
    }

    public static <E> E random(List<E> c) {
        final var i = new Random().nextInt(c.size());

        return c.get(i);
    }

    public static <E> Optional<E> random(Collection<E> e) {
        final var size = e.size();
        if (size < 1)
            return Optional.empty();

        var i = random.nextInt(size);
        for (E el : e) {
            if (i-- == 0)
                return Optional.of(el);
        }

        throw new AssertionError("No way");
    }

}
