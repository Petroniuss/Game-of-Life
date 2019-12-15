package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import javafx.scene.layout.VBox;

public class SideMenu extends VBox {

    private SettingsMenu settingsMenu;
    private StatisticsMenu statisticsMenu;
    private Chart chart;

    public SideMenu(SimulationState state) {
        this.settingsMenu = new SettingsMenu();
        this.statisticsMenu = new StatisticsMenu(state);
        this.chart = new Chart(state);

        getChildren().addAll(settingsMenu, statisticsMenu, chart);
        setStyle("-fx-background-color: #01142F");
    }

    public void onPausePlayEvent() {
        this.settingsMenu.onPausePlayEvent();
    }

    public void onUpdate() {
        this.statisticsMenu.onUpdate();
        this.chart.onUpdate();
    }
}
