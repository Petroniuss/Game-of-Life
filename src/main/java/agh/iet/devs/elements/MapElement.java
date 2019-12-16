package agh.iet.devs.elements;

import agh.iet.devs.data.Vector;
import agh.iet.devs.map.MapElementVisitor;
import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.view.node.SimulationNode;
import javafx.scene.image.Image;

public interface MapElement extends MapElementObservable {

    void acceptOnMove(MapElementVisitor visitor, Vector from);

    void acceptOnVanish(MapElementVisitor visitor);

    void onUpdate();

    void onDeath();

    Vector getPosition();

    int getEnergy();

    SimulationNode getView();

    Icon getIcon();

    enum Icon {
        GRASS("grass.png"),
        DYING_ANIMAL("dying_animal.png"),
        FERTILE_ANIMAL("fertile_animal.png"),
        HEALTHY_ANIMAL("healthy_animal.png"),
        DOMINATING_ANIMAL("dominating_animal.png");

        public final Image img;

        Icon(String name) {
            this.img = GeneralUtils.fromImages(name);
        }
    }

}
