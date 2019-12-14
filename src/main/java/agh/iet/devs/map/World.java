package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.region.Grassland;
import agh.iet.devs.map.region.Jungle;
import agh.iet.devs.map.region.Region;
import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.view.controller.UpdateListener;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Class responsible for holding information about animals.
 */
public class World implements MapElementObserver, MapElementVisitor {
    private final Config config = Config.getInstance();
    private final UpdateListener listener;
    private final List<Region> regions;

    private final Map<Vector, Set<Animal>> animals = new HashMap<>();
    private final Map<Vector, Food> food = new HashMap<>();

    public World(UpdateListener listener) {
        this.listener = listener;
        this.regions = List.of(
                new Jungle(config.jungleBounds()),
                new Grassland(config.outerBounds(), config.jungleBounds()));

        IntStream.range(0, config.params.animalsAtStart)
                .mapToObj(i -> GeneralUtils.randomFromList(regions))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(freePosition -> new Animal(freePosition, config.params.startEnergy))
                .forEach(this::attachMapElement);
    }

    @Override
    public void onMove(MapElement e, Vector from) {
        e.acceptOnMove(this, from);
    }

    @Override
    public void onVanish(MapElement e) {
        e.acceptOnVanish(this);
    }

    @Override
    public void onVanish(Food food) {

    }

    @Override
    public void onVanish(Animal animal) {

    }

    @Override
    public void onMove(Animal animal, Vector from) {

    }

    public void onUpdate() {

    }

    private Animal findHealthiestAnimal(Set<Animal> elements) {
        return elements.stream()
                .max(Comparator.comparingInt(AbstractMapElement::getEnergy))
                .orElseThrow();
    }

    private void attachMapElement(MapElement e) {
        regions.forEach(region -> region.attachElement(e));
    }

}
