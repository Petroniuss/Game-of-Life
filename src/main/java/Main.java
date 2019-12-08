import agh.iet.devs.config.SimulationState;
import agh.iet.devs.map.World;
import agh.iet.devs.view.controller.ViewConfiguration;
import agh.iet.devs.view.controller.ViewController;
import agh.iet.devs.view.menu.SettingsMenu;
import agh.iet.devs.view.menu.StatisticsMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static agh.iet.devs.view.controller.ViewConfiguration.WINDOW_HEIGHT;
import static agh.iet.devs.view.controller.ViewConfiguration.WINDOW_WIDTH;

public class Main extends Application {
    private World world;

    private StatisticsMenu statisticsMenu;
    private SettingsMenu settingsMenu;
    private SimulationState state;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.state = new SimulationState();

        final var grid = new GridPane();
        final var controller = new ViewController(grid, WINDOW_WIDTH, WINDOW_HEIGHT, state);

        this.world = new World(controller);
        this.statisticsMenu = new StatisticsMenu(state);
        this.settingsMenu = new SettingsMenu(state);

        final var vbox = new VBox(
                new MenuBar(settingsMenu, statisticsMenu), grid);

        final var scene = new Scene(vbox);
        scene.getStylesheets().add(getStyleSheets());

        Thread thread = new Thread(() -> {
            Runnable updater = this::update;
            while (true) {
                try {
                    Thread.sleep(state.interval.get());
                } catch (InterruptedException ignore) {}

                // UI update is run on the UI thread
                if (state.running.get())
                    Platform.runLater(updater);
            }
        });
        thread.setDaemon(true);
        thread.start();

        scene.setOnKeyPressed(this::onKeyPressed);

        stage.setTitle(ViewConfiguration.TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(ViewConfiguration.icon());
        stage.show();
    }

    private void update() {
        this.world.onUpdate();
        this.statisticsMenu.onUpdate();
    }

    private void onKeyPressed(KeyEvent event) {
        final var code = event.getCode();

        if (code == KeyCode.P)
            this.settingsMenu.onPausePlayEvent();
        else if (code == KeyCode.ESCAPE)
            System.exit(0);
    }

    private String getStyleSheets() {
        return getClass().getResource("styles/styles.css").toExternalForm();
    }

}
