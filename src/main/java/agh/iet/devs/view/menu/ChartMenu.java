package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.utils.GeneralUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;

@SuppressWarnings("unchecked")
public class ChartMenu extends Menu {

    private final SimulationState state;
    private final XYChart.Series<Number, Number> foodSeries;
    private final XYChart.Series<Number, Number> animalSeries;

    public ChartMenu(SimulationState state) {
        super("Chart");
        this.state = state;

        setGraphic(new ImageView(GeneralUtils.fromImages("line-chart.png")));

        final var xAxis = new NumberAxis();
        final var yAxis = new NumberAxis();
        final var lineChart = new LineChart<>(xAxis,yAxis);

        xAxis.setLabel("Days");
        lineChart.setTitle("Life does find a way");

        this.animalSeries = new XYChart.Series<>(
                "Animals", FXCollections.observableArrayList(new XYChart.Data<>(1, state.animalCount.get())));


        this.foodSeries = new XYChart.Series<>(
                "Food", FXCollections.observableArrayList(new XYChart.Data<>(1, state.foodCount.get())));

        lineChart.getData().addAll(animalSeries, foodSeries);
        lineChart.setCreateSymbols(false);

        final var charMenuItem = new CustomMenuItem(lineChart);
        getItems().add(charMenuItem);
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
