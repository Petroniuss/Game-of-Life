package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Rect;
import agh.iet.devs.map.region.Jungle;
import agh.iet.devs.map.region.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {

    private final List<Region> regions;

    public World() {
        final var params = Config.getInstance().params;

        final var outer = new Rect();

        this.regions = List.of(
                new Jungle()
        );
    }


}
