package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Tuple;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.region.Grassland;
import agh.iet.devs.map.region.Jungle;
import agh.iet.devs.map.region.Region;
import agh.iet.devs.utils.GeneralUtils;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Class responsible for holding information about animals.
 */
public class World implements MapElementObserver, MapElementVisitor {

    private final List<Region> regions;
    private final Map<Vector, Set<Animal>> animalMap = new HashMap<>();
    private final Map<Vector, Food> foodMap = new HashMap<>();

    private final Config config = Config.getInstance();

    public World() {
        this.regions = List.of(
                new Jungle(config.jungleBounds()),
                new Grassland(config.outerBounds(), config.jungleBounds()));

        regions.stream()
                .map(Region::emptyPositions)
                .flatMap(List::stream)
                .forEach(v -> animalMap.put(v, new HashSet<>()));

        IntStream.range(0, config.params.animalsAtStart)
                .mapToObj(i -> GeneralUtils.randomFromList(regions))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(freePosition -> new Animal(freePosition, config.params.startEnergy))
                .forEach(this::attachAnimal);
    }

    public void onUpdate() {
        // grow!
        regions.stream()
                .map(Region::emptyPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(empty -> new Food(empty, config.params.plantEnergy))
                .forEach(this::attachFood);

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
                .map(parents -> Animal.cross(parents.first, parents.second))
                .forEach(this::attachAnimal);
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
        this.foodMap.remove(food.getPosition());
    }

    @Override
    public void onVanish(Animal animal) {
        this.animalMap.get(animal.getPosition()).remove(animal);
    }

    @Override
    public void onMove(Animal animal, Vector from) {
        this.animalMap.get(from).remove(animal);
        this.animalMap.get(animal.getPosition()).add(animal);
    }

    private void attachAnimal(Animal animal) {
        animalMap.get(animal.getPosition()).add(animal);

        attachMapElement(animal);
    }

    private void attachFood(Food food) {
        foodMap.put(food.getPosition(), food);

        attachMapElement(food);
    }

    private void attachMapElement(MapElement e) {
        e.attachListener(this);
        regions.forEach(region -> region.attachElement(e));
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
