package agh.iet.devs.elements;

import agh.iet.devs.data.Vector;
import agh.iet.devs.utils.GeneralUtils;
import javafx.scene.image.Image;

public interface MapElement extends MapElementObservable {

    void onUpdate();

    Vector getPosition();

    int getEnergy();

    Icon getIcon();

    enum Icon {
        Animal("animal.png"),
        Food("weed.png");

        public final Image img;
        Icon(String name) {
            this.img = GeneralUtils.fromImages(name);
        }
    }

}
