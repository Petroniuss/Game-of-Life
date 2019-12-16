import agh.iet.devs.config.SimulationState;
import agh.iet.devs.map.WorldController;
import agh.iet.devs.view.menu.SideMenu;
import agh.iet.devs.view.controller.SimulationViewController;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static agh.iet.devs.view.controller.ViewConfiguration.*;

public class Main extends Application {

    private WorldController worldController;
    private SideMenu menu;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        var state = new SimulationState();
        var controller = new SimulationViewController(SIMULATION_WIDTH, SIMULATION_HEIGHT);

        this.menu = new SideMenu(state);
        this.worldController = new WorldController(controller, state);

        final var hbox = new HBox(menu, controller);
        final var scene = new Scene(hbox);

        scene.getStylesheets().add(getStyleSheets());

        Thread thread = new Thread(() -> {
            Runnable updater = this::update;
            while (true) {
                try {
                    Thread.sleep(ViewConfiguration.getInstance().interval.get());
                } catch (InterruptedException ignore) {}

                // UI update is run on the UI thread
                if (ViewConfiguration.getInstance().running.get())
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
        this.worldController.updateWorld();
        this.menu.onUpdate();
    }

    private void onKeyPressed(KeyEvent event) {
        final var code = event.getCode();

        if (code == KeyCode.P)
            this.menu.onPausePlayEvent();
        else if (code == KeyCode.ESCAPE)
            System.exit(0);
    }

    private String getStyleSheets() {
        return getClass().getResource("styles/styles.css").toExternalForm();
    }

}
