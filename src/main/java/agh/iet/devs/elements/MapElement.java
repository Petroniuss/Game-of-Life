package agh.iet.devs.elements;

import agh.iet.devs.data.Vector;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public interface MapElement extends MapElementObservable {

    void onUpdate();

    Vector getPosition();

    int getEnergy();

    Icon getIcon();

    enum Icon {
        Animal("panda.png"),
        Food("weed.png");

        public final Image img;
        Icon(String name) {
            FileInputStream input = null;
            try {
                input = new FileInputStream("src/main/resources/images/" + name);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.img = new Image(input);
        }
    }

}
