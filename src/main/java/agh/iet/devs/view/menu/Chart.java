package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import static javafx.collections.FXCollections.observableArrayList;

@SuppressWarnings("unchecked")
public class Chart extends LineChart<Number, Number> implements Updatable {

    private final SimulationState state;
    private final XYChart.Series<Number, Number> foodSeries;
    private final XYChart.Series<Number, Number> animalSeries;
    private final XYChart.Series<Number, Number> energySeries;

    public Chart(SimulationState state) {
        super(new NumberAxis(), new NumberAxis());
        this.state = state;

        this.animalSeries = new XYChart.Series<>(
                "Animals", observableArrayList(
                data(state.getDayCount(), state.getAnimalCount()))
        );

        this.foodSeries = new XYChart.Series<>(
                "Food", observableArrayList(
                        data(state.getDayCount(), state.getAnimalCount()))
        );

        this.energySeries = new XYChart.Series<>(
                "Energy", observableArrayList(
                    data(state.getDayCount(), (int) state.getAverageEnergy())
        ));

        getData().addAll(animalSeries, energySeries, foodSeries);
        setCreateSymbols(false);
        setMaxWidth(ViewConfiguration.SIDE_MENU_WIDTH);
        setPadding(new Insets(2, 0, 0, 0));
        applyCss();
    }

    @Override
    public void onUpdate() {
        foodSeries.getData().add(
                data(state.getDayCount(), state.getFoodCount())
        );

        animalSeries.getData().add(
                data(state.getDayCount(), state.getAnimalCount())
        );

        energySeries.getData().add(
                data(state.getDayCount(), (int) state.getAverageEnergy()));
    }

    private static XYChart.Data<Number, Number> data(Number x, Number y) {
        return new XYChart.Data<>(x, y);
    }

}
