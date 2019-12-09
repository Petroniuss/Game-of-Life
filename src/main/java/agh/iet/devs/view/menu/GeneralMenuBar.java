package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import javafx.scene.control.MenuBar;

public class GeneralMenuBar extends MenuBar {

    public final StatisticsMenu statisticsMenu;
    public final SettingsMenu settingsMenu;
    public final ChartMenu chartMenu;

    public GeneralMenuBar(SimulationState state) {
        this.statisticsMenu = new StatisticsMenu(state);
        this.settingsMenu = new SettingsMenu(state);
        this.chartMenu = new ChartMenu(state);

        getMenus().addAll(settingsMenu, statisticsMenu, chartMenu);
    }

}
