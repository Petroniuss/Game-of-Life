package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.region.Grassland;
import agh.iet.devs.map.region.Jungle;
import agh.iet.devs.map.region.Region;
import agh.iet.devs.utils.GeneralUtils;

import java.util.*;
import java.util.stream.Collectors;
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

    public Map<Vector, Set<Animal>> getAnimalMap() {
        final var shallow = new  HashMap<Vector, Set<Animal>>(animalMap.size());

        animalMap.forEach((key, value) -> shallow.put(key, new HashSet<>(value)));

        return shallow;
    }

    public Map<Vector, Food> getFoodMap() {
        return new HashMap<>(foodMap);
    }

    public List<Region> getRegions() {
        return new ArrayList<>(regions);
    }

    public int foodCount() {
        return foodMap.size();
    }

    public int animalCount() {
        return animalMap.values()
                .stream()
                .flatMap(Collection::stream)
                .reduce(0, (acc, e) -> acc + 1, Integer::sum);
    }

    public List<MapElement> elements() {
        final List<MapElement> elements = animalMap.values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toList());
        elements.addAll(foodMap.values());

        return elements;
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
    public void onFoodVanish(Food food) {
        this.foodMap.remove(food.getPosition());
    }

    @Override
    public void onAnimalVanish(Animal animal) {
        this.animalMap.get(animal.getPosition()).remove(animal);
    }

    @Override
    public void onAnimalMove(Animal animal, Vector from) {
        this.animalMap.get(from).remove(animal);
        this.animalMap.get(animal.getPosition()).add(animal);
    }

    public void attachAnimal(Animal animal) {
        animalMap.get(animal.getPosition()).add(animal);

        attachMapElement(animal);
    }

    public void attachFood(Food food) {
        foodMap.put(food.getPosition(), food);

        attachMapElement(food);
    }

    public void attachMapElement(MapElement e) {
        e.attachListener(this);
        regions.forEach(region -> region.attachElement(e));
    }
}
