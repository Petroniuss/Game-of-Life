package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.config.SimulationState;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.MapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.animal.Genome;
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
    private final DominatingGeneCalculator dominatingGeneCalculator = new DominatingGeneCalculator();
    private final SimulationState state;

    private final Config config = Config.getInstance();

    public World(SimulationState state) {
        this.state = state;
        this.regions = List.of(
                new Jungle(config.params.jungleBounds()),
                new Grassland(config.params.outerBounds(), config.params.jungleBounds()));

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
        final var shallow = new HashMap<Vector, Set<Animal>>(animalMap.size());
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
        return (int) animalMap.values()
                .stream()
                .mapToLong(Collection::size)
                .sum();
    }

    public int dominatingGen() {
        return dominatingGeneCalculator.dominating();
    }

    public double lifeExpectancy() {
        return animalMap.values()
                .stream()
                .flatMap(Collection::stream)
                .map(a -> a.getLifetime(state.getDayCount()))
                .reduce(Integer::sum).orElse(0) / (double) animalCount();
    }

    public double averageChildren() {
        return animalMap.values()
                .stream()
                .flatMap(Collection::stream)
                .map(Animal::getChildren)
                .reduce(Integer::sum)
                .orElse(0) / (double) animalCount();
    }

    public double averageEnergy() {
        return animalMap.values()
                .stream()
                .flatMap(Collection::stream)
                .map(AbstractMapElement::getEnergy)
                .reduce(0.0, Double::sum, Double::sum) / (double) animalCount();
    }

    public int[] genesDistribution() {
        return dominatingGeneCalculator.getGenesDistribution();
    }

    public void markDominatingAnimals() {
        final var dominatingGene = dominatingGeneCalculator.dominating();

        animalMap.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(a -> a.dominatingGene() == dominatingGene)
                .forEach(Animal::markAsDominating);
    }

    @Override
    public void onMove(MapElement e, Vector from) {
        e.acceptOnMove(this, from);
    }

    @Override
    public void onVanish(MapElement e) {
        e.acceptOnVanish(this);
        e.detachListener(this);
    }

    @Override
    public void onFoodVanish(Food food) {
        this.foodMap.remove(food.getPosition());
    }

    @Override
    public void onAnimalVanish(Animal animal) {
        this.animalMap.get(animal.getPosition()).remove(animal);
        this.dominatingGeneCalculator.removeGenome(animal.getGenome());
    }

    @Override
    public void onAnimalMove(Animal animal, Vector from) {
        this.animalMap.get(from).remove(animal);
        this.animalMap.get(animal.getPosition()).add(animal);
    }

    public void attachAnimal(Animal animal) {
        this.animalMap.get(animal.getPosition()).add(animal);
        this.dominatingGeneCalculator.addGenome(animal.getGenome());
        attachMapElement(animal);
    }

    public void attachFood(Food food) {
        this.foodMap.put(food.getPosition(), food);

        attachMapElement(food);
    }

    public void attachMapElement(MapElement e) {
        e.attachListener(this);
        regions.forEach(region -> region.attachElement(e));
    }

    private static class DominatingGeneCalculator {
        private int[] freq = new int[8];

        void addGenome(Genome g) {
            for (int i = 0; i < Genome.genomeSize; i++)
                freq[g.geneAt(i)] += 1;

        }

        void removeGenome(Genome g) {
            for (int i = 0; i < Genome.genomeSize; i++)
                freq[g.geneAt(i)] -= 1;
        }

        int dominating() {
            return GeneralUtils.maxElementFromArray(freq);
        }

        public int[] getGenesDistribution() {
            return Arrays.copyOf(freq, 8);
        }
    }

}
