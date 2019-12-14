package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

@SuppressWarnings("unchecked")
public class Chart extends LineChart<Number, Number> {

    private final SimulationState state;
    private final XYChart.Series<Number, Number> foodSeries;
    private final XYChart.Series<Number, Number> animalSeries;

    public Chart(SimulationState state) {
        super(new NumberAxis(), new NumberAxis());
        this.state = state;

        this.animalSeries = new XYChart.Series<>(
                "Animals", FXCollections.observableArrayList(new XYChart.Data<>(state.dayCount.get(), state.animalCount.get())));

        this.foodSeries = new XYChart.Series<>(
                "Food", FXCollections.observableArrayList(new XYChart.Data<>(state.dayCount.get(), state.foodCount.get())));

        getData().addAll(animalSeries, foodSeries);
        setCreateSymbols(false);
        setMaxWidth(ViewConfiguration.SIDE_MENU_WIDTH);
        setPadding(new Insets(2, 0, 10, 0));

        applyCss();
    }

    public void onUpdate() {
        foodSeries.getData().add(new XYChart.Data<>(
                state.dayCount, state.foodCount.get()
        ));

        animalSeries.getData().add(new XYChart.Data<>(
                state.dayCount, state.animalCount.get()
        ));
    }

}
