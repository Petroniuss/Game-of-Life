package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import javafx.scene.layout.VBox;

//Todo create interface for submenu!
public class SideMenu extends VBox {

    private SettingsMenu settingsMenu;
    private StatisticsMenu statisticsMenu;
    private Chart chart;
    private Submenu submenu;

    public SideMenu(SimulationState state) {
        this.settingsMenu = new SettingsMenu();
        this.statisticsMenu = new StatisticsMenu(state);
        this.chart = new Chart(state);
        this.submenu = new Submenu(state);

        getChildren().addAll(statisticsMenu, chart, settingsMenu, submenu);
        setStyle("-fx-background-color: #01142F");
    }

    public void onPausePlayEvent() {
        this.settingsMenu.onPausePlayEvent();
    }

    public void onUpdate() {
        this.statisticsMenu.onUpdate();
        this.chart.onUpdate();
        this.submenu.onUpdate();
    }
}
