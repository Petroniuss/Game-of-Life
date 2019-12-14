package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Tuple;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.region.Region;
import agh.iet.devs.view.controller.UpdateListener;

import java.util.*;
import java.util.stream.Collectors;

public class WorldController {

    private final World world;
    private final UpdateListener listener;

    public WorldController(UpdateListener listener) {
        this.world = new World();
        this.listener = listener;
    }

    public void updateWorld() {
        final var regions = world.getRegions();
        final var foodMap = world.getFoodMap();
        final var animalMap = world.getAnimalMap();

        // grow!
        regions.stream()
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(empty -> new Food(empty, Config.getInstance().params.plantEnergy))
                .forEach(world::attachFood);

        // update!
        foodMap.values()
                .forEach(AbstractMapElement::onUpdate);

        animalMap.values()
                .stream()
                .flatMap(Set::stream)
                .forEach(AbstractMapElement::onUpdate);

        // eat!
        animalMap.values()
                .stream()
                .filter(animals -> !animals.isEmpty())
                .map(this::findHealthiestAnimal)
                .filter(animal -> foodMap.containsKey(animal.getPosition()))
                .forEach(animal -> animal.eat(foodMap.get(animal.getPosition())));

        // propagate!
        animalMap.values()
                .stream()
                .filter(animals -> animals.size() >= 2)
                .map(this::findHealthiest)
                .filter(parents -> parents.first.eligibleForReproduction() && parents.second.eligibleForReproduction())
                .map(parents -> Animal.cross(parents.first, parents.second))
                .forEach(world::attachAnimal);

        listener.onUpdate(world.elements());
    }

    private Animal findHealthiestAnimal(Set<Animal> animalSet) {
        return animalSet.stream()
                .max(Comparator.comparingInt(AbstractMapElement::getEnergy))
                .orElseThrow();
    }

    private Tuple<Animal> findHealthiest(Set<Animal> set) {
        final var cpy = new HashSet<>(set);
        final var a1 = findHealthiestAnimal(cpy);

        cpy.remove(a1);

        final var a2 = findHealthiestAnimal(cpy);

        return Tuple.of(a1, a2);
    }

}
