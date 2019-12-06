package agh.iet.devs.utils;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

public final class GeneralUtils {

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

}
