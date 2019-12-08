package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import javafx.scene.control.MenuBar;

public class GeneralMenuBar extends MenuBar {

    public final StatisticsMenu statisticsMenu;
    public final SettingsMenu settingsMenu;

    public GeneralMenuBar(SimulationState state) {
        this.statisticsMenu = new StatisticsMenu(state);
        this.settingsMenu = new SettingsMenu(state);

        getMenus().addAll(settingsMenu, statisticsMenu);
    }

}
