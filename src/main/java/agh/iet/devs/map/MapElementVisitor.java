package agh.iet.devs.map;

import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.animal.Animal;
import agh.iet.devs.elements.food.Food;

public interface MapElementVisitor {

    void onVanish(Food food);

    void onVanish(Animal animal);

    void onMove(Animal animal, Vector from);

}
