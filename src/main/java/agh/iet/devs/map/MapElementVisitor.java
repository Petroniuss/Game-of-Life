package agh.iet.devs.map;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;

public interface MapElementVisitor {

    void onFoodVanish(Food food);

    void onAnimalVanish(Animal animal);

    void onAnimalMove(Animal animal, Vector from);

}
