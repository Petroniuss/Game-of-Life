import agh.iet.devs.config.Config;
import agh.iet.devs.map.World;
import agh.iet.devs.view.MainMenu;
import agh.iet.devs.view.StatisticsMenu;
import agh.iet.devs.view.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
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

    // UI
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicLong interval = new AtomicLong(MainMenu.MAX_INTERVAL / 2);

    private final AtomicInteger animalCount = new AtomicInteger(0);
    private final AtomicInteger foodCount = new AtomicInteger(0);
    private final AtomicLong dayCount = new AtomicLong(0);

    @Override
    public void start(Stage stage) {
        final var config = Config.getInstance();
        final var grid = new GridPane();
        final var controller = new ViewController(grid, WINDOW_WIDTH, WINDOW_HEIGHT);

        this.world = new World(controller);
        this.statisticsMenu = new StatisticsMenu(animalCount, foodCount, dayCount);

        final var menu = new MainMenu(running, interval);
        final var vbox = new VBox(new MenuBar(menu, statisticsMenu), grid);

        grid.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

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

        stage.setTitle(config.name);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.jpg")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
