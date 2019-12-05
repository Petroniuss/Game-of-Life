package agh.iet.devs.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Tile extends Button {

    private static final String defaultStyle = "field";

    public enum TileType {
        JUNGLE("jungle-field"),
        GRASSLAND("grassland-field");

        final String style;
        TileType(String style) {
            this.style = style;
        }
    }

    public enum Icon {
        Animal("panda.png"),
        Food("food.png");

        final Image img;
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

    public Tile(double width, double height, TileType type) {
        setPrefWidth(width);
        setPrefHeight(height);
        setMaxSize(width, height);
        setMinSize(width, height);

        getStyleClass().addAll(defaultStyle, type.style);
    }

    public void renderIcon(Icon icon) {
        final ImageView imageView = new ImageView(icon.img);

        imageView.setFitWidth(this.getPrefWidth() - 5);
        imageView.setFitHeight(this.getPrefHeight() - 5);

        setGraphic(imageView);
    }

    public void clear() {
        setGraphic(null);
    }

}
