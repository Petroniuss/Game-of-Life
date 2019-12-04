package agh.iet.devs.utils;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public final class CollectionsUtils {

    public static <E> E random(List<E> c) {
        final var i = new Random().nextInt(c.size());

        return c.get(i);
    }

}
