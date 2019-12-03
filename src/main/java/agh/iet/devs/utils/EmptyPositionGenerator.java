package agh.iet.devs.utils;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Params;
import agh.iet.devs.data.Vector;
import agh.iet.devs.map.Map;

import java.util.Random;

public class EmptyPositionGenerator {
    private static final Params params = Config.getInstance().params;
    private static final Random random = new Random();

    /**
     * @return empty position within given map.
     */
    public static Vector find(Map map) {
        var position = randomWithin(params.width, params.height);
        while (map.isOccupied(position))
            position = randomWithin(params.width, params.height);

        return position;
    }

    private static Vector randomWithin(int width, int height) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);

        return Vector.create(x, y);
    }


}
