package agh.iet.devs.map;

import agh.iet.devs.config.Config;
import agh.iet.devs.config.SimulationState;
import agh.iet.devs.data.Tuple;
import agh.iet.devs.elements.AbstractMapElement;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;
import agh.iet.devs.map.region.Region;
import agh.iet.devs.view.controller.UpdateListener;

import java.util.*;

public class WorldController {

    private final World world;
    private final UpdateListener listener;
    private final SimulationState state;

    public WorldController(UpdateListener listener, SimulationState state) {
        this.world = new World();
        this.listener = listener;
        this.state = state;
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
                .map(WorldController::findHealthiestAnimal)
                .filter(animal -> foodMap.containsKey(animal.getPosition()))
                .forEach(animal -> animal.eat(foodMap.get(animal.getPosition())));

        // propagate!
        animalMap.values()
                .stream()
                .filter(animals -> animals.size() >= 2)
                .map(WorldController::findHealthiestPair)
                .filter(parents -> parents.second.eligibleForReproduction())
                .map(parents -> Animal.cross(parents.first, parents.second))
                .forEach(world::attachAnimal);

        listener.onUpdate(world.elements());
        this.state.update(world.foodCount(), world.animalCount());
    }

    private static Animal findHealthiestAnimal(Set<Animal> animalSet) {
        return animalSet.stream()
                .max(Comparator.comparingInt(AbstractMapElement::getEnergy))
                .orElseThrow();
    }

    private static Tuple<Animal> findHealthiestPair(Set<Animal> set) {
        var it = set.iterator();
        var a = it.next();
        var b = it.next();

        if (a.getEnergy() < b.getEnergy()) {
            var c = a;
            a = b;
            b = c;
        }

        while (it.hasNext()) {
            var next = it.next();
            if (next.getEnergy() > a.getEnergy()){
                b = a;
                a = next;
            } else if (next.getEnergy() > b.getEnergy()) {
                b = next;
            }
        }

        return Tuple.of(a, b);
    }

}
