package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import javafx.scene.layout.VBox;

import java.util.List;

public class SideMenu extends VBox {

    private final List<Updatable> updatableList;
    private final SettingsMenu settingsMenu;

    public SideMenu(SimulationState state) {
        this.settingsMenu = new SettingsMenu();

        var statisticsMenu = new StatisticsMenu(state);
        var chart = new Chart(state);
        var submenu = new Submenu(state);

        this.updatableList = List.of(statisticsMenu, chart, submenu);

        getChildren().addAll(statisticsMenu, chart, settingsMenu, submenu);
        setStyle("-fx-background-color: #01142F");
    }

    public void onPausePlayEvent() {
        this.settingsMenu.onPausePlayEvent();
    }

    public void onUpdate() {
        this.updatableList.forEach(Updatable::onUpdate);
    }
}
