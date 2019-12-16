package agh.iet.devs.view.menu;

import agh.iet.devs.config.SimulationState;
import agh.iet.devs.utils.GeneralUtils;
import agh.iet.devs.utils.StatisticsSaver;
import agh.iet.devs.view.controller.ViewConfiguration;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Submenu extends VBox {

    private final SimulationState state;

    private CheckBox checkBox;
    private Spinner<Integer> spinner;
    private SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory;

    public Submenu(SimulationState state) {
        this.state = state;

        Button saveButton = new Button("Save");
        saveButton.setGraphic(new ImageView(
                GeneralUtils.ButtonGraphics.SAVE.image
        ));
        saveButton.setOnAction(this::setSaveButtonClick);

        this.checkBox = new CheckBox("All");
        this.checkBox.setAllowIndeterminate(false);
        this.checkBox.setPadding(new Insets(0, 5, 0, 5));

        this.valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
        this.spinner = new Spinner<>(this.valueFactory);
        this.spinner.setPrefWidth(ViewConfiguration.SIDE_MENU_WIDTH / 4.0);

        final var label = new Text("Save epochs");
        label.setFont(Font.font(24));
        label.setFill(Color.WHITE);
        final var labelBox = new HBox(label);
        labelBox.setPadding(new Insets(0, 5, 10, 0));

        final var hbox = new HBox(saveButton, checkBox, spinner);

        setPadding(new Insets(5, 2, 5, 2));

        hbox.setPrefWidth(ViewConfiguration.SIDE_MENU_WIDTH);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        labelBox.setAlignment(Pos.BASELINE_CENTER);

        getChildren().addAll(hbox);
    }

    private void setSaveButtonClick(ActionEvent a) {
        if (!checkBox.isSelected()) {
            StatisticsSaver.save(state.getEpoch(this.spinner.getValue()));
        } else {
            StatisticsSaver.saveAll(state.history);
        }
    }

    public void onUpdate() {
        this.valueFactory.setMax(state.dayCount.intValue());
    }

}
