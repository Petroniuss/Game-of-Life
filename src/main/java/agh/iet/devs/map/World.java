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
    private final List<Region> regions = new LinkedList<>();
    private final Config config = Config.getInstance();
    private final UpdateListener listener;

    public World(UpdateListener listener) {
        this.listener = listener;

        regions.add(new Jungle(config.jungleBounds()));
        regions.add(new Grassland(config.outerBounds(), config.jungleBounds()));

        IntStream.range(0, config.params.animalsAtStart)
                .mapToObj(i -> GeneralUtils.random(regions))
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(freePosition -> new Animal(freePosition, config.params.startEnergy))
                .forEach(this::attachMapElement);
    }

    public void onUpdate() {
        addFood();

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
     * I think some kind of bug occurs when 3 animals and food happen to be on the same position.
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

    /**
     * If there's place adds food to each region.
     */
    private void addFood() {
        regions.stream()
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(freePosition -> new Food(freePosition, config.params.plantEnergy))
                .forEach(this::attachMapElement);
    }

    private void attachMapElement(MapElement e) {
        regions.forEach(region -> region.attachElement(e));
    }

}
