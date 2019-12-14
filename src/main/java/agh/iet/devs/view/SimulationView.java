package agh.iet.devs.view;

import agh.iet.devs.config.Config;
import agh.iet.devs.data.Tuple;
import agh.iet.devs.data.Vector;
import agh.iet.devs.elements.MapElement;
import javafx.scene.layout.Pane;

public class SimulationView extends Pane implements UIListener {

    private final double fitWidth;
    private final double fitHeight;

    public SimulationView(int width, int height) {
        setPrefSize(width, height);

        final var params = Config.getInstance().params;

        this.fitHeight = height / (double) params.height;
        this.fitWidth = width / (double) params.width;
    }

    @Override
    public void attach(MapElement e) {
        final var view = e.getView();
        final var position = calcPosition(e.getPosition());

        view.setFitHeight(fitHeight);
        view.setFitWidth(fitWidth);
        view.relocate(position.first, position.second);

        getChildren().add(view);
        e.attachListener(this);
    }

    @Override
    public void onMove(MapElement e, Vector from) {
        final var view = e.getView();
        final var position = calcPosition(e.getPosition());

        view.relocate(position.first, position.second);
    }

    @Override
    public void onVanish(MapElement e) {
        final var view = e.getView();

        getChildren().remove(view);
        e.detachListener(this);
    }

    private Tuple<Double> calcPosition(Vector position) {
        final var x = position.x * fitWidth;
        final var y = position.y * fitHeight;

        return Tuple.of(x, y);
    }
}
