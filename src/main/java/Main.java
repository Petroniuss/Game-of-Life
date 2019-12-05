import agh.iet.devs.config.Config;
import agh.iet.devs.data.Vector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        final var config = Config.getInstance();
        final var bounds = Screen.getPrimary().getBounds();

        final var gridWidth = bounds.getWidth() * 2; // I have absolutely no idea why i have to multiply it by 2.. :C
        final var gridHeight = bounds.getHeight();

        final var grid = new GridPane();


        grid.setPrefSize(gridWidth, gridHeight);
        renderFrame(grid, gridWidth, gridHeight);

        final var scene = new Scene(grid, gridWidth, gridHeight);
        scene.getStylesheets().add(getClass().getResource("styles/styles.css").toExternalForm());

        stage.setTitle(config.name);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    private void renderFrame(GridPane grid, double gridWidth, double gridHeight) {
        final var params = Config.getInstance().params;
        final var jungle = Config.getInstance().jungleBounds();

        final var width = gridWidth / params.width;
        final var height = gridHeight / params.height;

        final var jungleStyle = "jungle-field";
        final var grassLandStyle = "grassland-field";

        for (Vector v : Config.getInstance().outerBounds()) {
            final var rectangle = new Button();
            rectangle.setPrefWidth(width);
            rectangle.setPrefHeight(height);

            rectangle.getStyleClass().add("field");

            if (v.withinRect(jungle))
                rectangle.getStyleClass().add(jungleStyle);
            else
                rectangle.getStyleClass().add(grassLandStyle);

            grid.add(rectangle, v.y, v.x, 1, 1);
        }
    }

}
