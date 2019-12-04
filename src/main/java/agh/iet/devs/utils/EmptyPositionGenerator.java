package agh.iet.devs.utils;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Params;
import agh.iet.devs.data.Vector;
import agh.iet.devs.map.Map;

import java.util.Random;

/**
 * Note that this implementation might be slow when map is overloaded with food.
 */
public class EmptyPositionGenerator {
    private static final Params params = Config.getInstance().params;
    private static final Random random = new Random();

    /**
     * @return empty position within given map.
     */
    public static Vector find(Map map, int startX, int endX, int startY, int endY) {
        var position = randomWithinRect(startX, endX, startY, endY);
        while (map.isOccupied(position))
            position = randomWithinRect(startX, endX, startY, endY);

        return position;
    }

    private static Vector randomWithinRect(int startX, int endX, int startY, int endY) {
        final var x = startX + random.nextInt(endX - startX);
        final var y = startY + random.nextInt(endY - startY);

        return Vector.create(x, y);
    }


}
