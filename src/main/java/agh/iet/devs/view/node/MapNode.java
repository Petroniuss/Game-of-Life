package agh.iet.devs.view.node;

import agh.iet.devs.elements.MapElement;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MapNode extends ImageView {

    private final Tooltip tooltip;

    public MapNode(MapElement e) {
        super(e.getIcon().img);
        this.tooltip = new Tooltip(e.toString());

        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.getStyleClass().add("tile-tooltip");

        Tooltip.install(this, tooltip);
    }

    public void updateTooltip(String msg) {
        tooltip.setText(msg);
    }

    public void updateIcon(MapElement.Icon icon) {
        setImage(icon.img);
    }
}
