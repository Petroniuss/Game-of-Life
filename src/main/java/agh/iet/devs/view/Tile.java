package agh.iet.devs.view;

import agh.iet.devs.elements.MapElement;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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


    public Tile(double width, double height, TileType type) {
        setPrefSize(width, height);
        setMaxSize(width, height);
        setMinSize(width, height);

        getStyleClass().addAll(defaultStyle, type.style);

        final var tooltip = new Tooltip("Empty spot");

        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.getStyleClass().add("tile-tooltip");

        setTooltip(tooltip);
    }

    public void renderIcon(MapElement.Icon icon) {
        final ImageView imageView = new ImageView(icon.img);

        imageView.setFitWidth(this.getPrefWidth() * 0.8);
        imageView.setFitHeight(this.getPrefHeight() * 0.8);

        setGraphic(imageView);
    }

    public void updateTooltip(String txt) {
        getTooltip().setText(txt);
    }

    public void clear() {
        setGraphic(null);
        getTooltip().setText("Empty spot");
    }

}
