package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatisticsMenu extends VBox {

    private final SimulationState state;

    private final Label dayLabel;
    private final Label animalLabel;
    private final Label foodLabel;
    private final Label averageEnergyLabel;
    private final Label lifeExpectancyLabel;
    private final Label dominatingGenomeLabel;
    private final Label avgChildrenLabel;

    public StatisticsMenu(SimulationState state) {
        this.state = state;

        this.dayLabel = new Label("Day: " + state.getDayCount());
        this.animalLabel = new Label("Animals: " + state.getAnimalCount());
        this.foodLabel = new Label("Food: " + state.getFoodCount());
        this.averageEnergyLabel = new Label(String.format("Average Energy: %.2f", state.getAverageEnergy()));
        this.lifeExpectancyLabel = new Label(String.format("Life Expectancy: %.2f", state.getLifeExpectancy()));
        this.dominatingGenomeLabel = new Label("Dominating Gene: ");
        this.avgChildrenLabel = new Label(String.format("Average Number of Children: %.2f", state.getAverageEnergy()));

        this.dominatingGenomeLabel.textOverrunProperty().setValue(OverrunStyle.CENTER_ELLIPSIS);

        final var label = new Text("Statistics");
        label.setFont(Font.font(24));
        label.setFill(Color.WHITE);
        final var labelBox = new HBox(label);

        labelBox.setPrefWidth(ViewConfiguration.SIDE_MENU_WIDTH);
        labelBox.setAlignment(Pos.BASELINE_CENTER);

        getChildren().addAll(
                labelBox,
                dayLabel,
                animalLabel,
                foodLabel,
                averageEnergyLabel,
                lifeExpectancyLabel,
                dominatingGenomeLabel,
                avgChildrenLabel
        );

        setPadding(new Insets(10, 25, 25, 10));
    }

    public void onUpdate() {
        this.dayLabel.setText("Day: " + state.getDayCount());
        this.animalLabel.setText("Animals: " + state.getAnimalCount());
        this.foodLabel.setText("Food: " + state.getFoodCount());
        this.averageEnergyLabel.setText(String.format("Average Energy: %.2f", state.getAverageEnergy()));
        this.lifeExpectancyLabel.setText(String.format("Life Expectancy: %.2f", state.getLifeExpectancy()));
        this.dominatingGenomeLabel.setText("Dominating Gene: " + state.getDominatingGen());
        this.avgChildrenLabel.setText(String.format("Average Number of Children: %.2f", state.getAverageChildren()));
    }

}
