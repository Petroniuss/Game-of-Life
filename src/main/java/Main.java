import agh.iet.devs.config.Config;
import agh.iet.devs.map.World;
import agh.iet.devs.view.SettingsMenu;
import agh.iet.devs.view.StatisticsMenu;
import agh.iet.devs.view.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;

    private World world;
    private StatisticsMenu statisticsMenu;
    private SettingsMenu settingsMenu;

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicLong interval = new AtomicLong(SettingsMenu.MAX_INTERVAL);

    private final AtomicInteger animalCount = new AtomicInteger(0);
    private final AtomicInteger foodCount = new AtomicInteger(0);
    private final AtomicLong dayCount = new AtomicLong(0);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        final var config = Config.getInstance();
        final var grid = new GridPane();
        final var controller = new ViewController(grid, WINDOW_WIDTH, WINDOW_HEIGHT);

        this.world = new World(controller);
        this.statisticsMenu = new StatisticsMenu(animalCount, foodCount, dayCount);
        this.settingsMenu = new SettingsMenu(running, interval);

        final var vbox = new VBox(new MenuBar(settingsMenu, statisticsMenu), grid);

        final var scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("styles/styles.css").toExternalForm());

        Thread thread = new Thread(() -> {
            Runnable updater = () -> {
                this.dayCount.incrementAndGet();
                world.onUpdate();
                this.statisticsMenu.update();
            };
            while (true) {
                try {
                    Thread.sleep(interval.get());
                } catch (InterruptedException ignore) {}

                // UI update is run on the Application thread
                if (running.get())
                    Platform.runLater(updater);
            }
        });
        thread.setDaemon(true);
        thread.start();

        scene.setOnKeyPressed(this::onKeyPressed);

        stage.setTitle(config.name);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));
        stage.show();
    }

    private void onKeyPressed(KeyEvent event) {
        final var code = event.getCode();

        if (code == KeyCode.P)
            this.settingsMenu.onPausePlayEvent();
        else if (code == KeyCode.ESCAPE)
            System.exit(0);
    }

}
