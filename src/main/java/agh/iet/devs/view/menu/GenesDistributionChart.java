package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableList;

public class GenesDistributionChart extends PieChart implements Updatable {

    private final SimulationState state;

    public GenesDistributionChart(SimulationState state) {
        this.state = state;

        setMaxWidth(ViewConfiguration.SIDE_MENU_WIDTH);
        setMaxHeight(100);

        final var list = new ArrayList<Data>();
        for (int i = 0; i < 8; i++)
            list.add((data(i, 0)));

        setData(observableList(list));
        setLabelsVisible(false);
    }

    private Data data(int gene, int freq) {
        return new PieChart.Data(String.valueOf(gene), freq);
    }

    public void onUpdate() {
        final var distr = state.getGenesDistribution();

        for (int i = 0; i < 8; i++)
            getData().get(i).setPieValue(distr[i]);

    }

}
