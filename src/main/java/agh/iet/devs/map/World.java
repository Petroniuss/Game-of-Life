package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.region.Grassland;
import agh.iet.devs.map.region.Jungle;
import agh.iet.devs.map.region.Region;
import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.view.UpdateListener;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class World {
    private final Config config = Config.getInstance();
    private final UpdateListener listener;
    private final List<Region> regions;

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

    public void onUpdate() {
        regions.stream()
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(freePosition -> new Food(freePosition, config.params.plantEnergy))
                .forEach(this::attachMapElement);

        regions.stream()
                .map(Region::objectsInRegion)
                .flatMap(Collection::stream)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .forEach(MapElement::onUpdate);

        regions.stream()
                .map(Region::objectsInRegion)
                .flatMap(Collection::stream)
                .map(Map.Entry::getValue)
                .filter(set -> set.size() >= 2)
                .forEach(this::handleCollisions);

        final var updated = regions.stream()
                .map(Region::objectsInRegion)
                .flatMap(Collection::stream)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        listener.onUpdate(updated);
    }

    /**
     * Note that this method should be called when set contains >= 2 elems.
     */
    private void handleCollisions(Set<MapElement> elements) {
        final var healthiest = findHealthiestAnimal(elements);
        final var optionalFood = findFood(elements);
        final var set = new HashSet<>(elements);

        set.remove(healthiest);
        optionalFood.ifPresent(food -> {
            healthiest.eat(food);
            set.remove(food);
        });

        if (!set.isEmpty()) {
            final var parent = findHealthiestAnimal(set);

            if (parent.eligibleForReproduction()) {
                final var kid = Animal.cross(healthiest, parent);
                attachMapElement(kid);
            }
        }
    }

    private Animal findHealthiestAnimal(Set<MapElement> elements) {
        return elements.stream()
                .filter(e -> e instanceof Animal)
                .map(e -> (Animal) e)
                .max(Comparator.comparingInt(AbstractMapElement::getEnergy))
                .orElseThrow();
    }

    private Optional<Food> findFood(Set<MapElement> elements) {
        return elements.stream()
                .filter(e -> e instanceof Food)
                .map(f -> (Food) f)
                .findAny();
    }

    private void attachMapElement(MapElement e) {
        regions.forEach(region -> region.attachElement(e));
    }
}
