package com.mandelbrot.utils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Dialog for setting the limits of the Mandelbrot set view.
 */
public class LimitsDialog {
    private RectangleCords rectangleCords;

    /**
     * Constructor to initialize the dialog with initial coordinates.
     *
     * @param initialCords The initial coordinates for the dialog.
     */
    public LimitsDialog(RectangleCords initialCords) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Set Limits");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField xMinField = new TextField(Double.toString(initialCords.getxMin()));
        TextField xMaxField = new TextField(Double.toString(initialCords.getxMax()));
        TextField yMinField = new TextField(Double.toString(initialCords.getyMin()));
        TextField yMaxField = new TextField(Double.toString(initialCords.getyMax()));

        grid.add(new Label("xMin:"), 0, 0);
        grid.add(xMinField, 1, 0);
        grid.add(new Label("xMax:"), 0, 1);
        grid.add(xMaxField, 1, 1);
        grid.add(new Label("yMin:"), 0, 2);
        grid.add(yMinField, 1, 2);
        grid.add(new Label("yMax:"), 0, 3);
        grid.add(yMaxField, 1, 3);

        Button cancelButton = new Button("Abbrechen");
        grid.add(cancelButton, 0, 4);
        cancelButton.setOnAction(evt -> dialog.close());

        Button submitButton = new Button("Speichern");
        submitButton.setOnAction(e -> {
            double xMin = Double.parseDouble(xMinField.getText());
            double xMax = Double.parseDouble(xMaxField.getText());
            double yMin = Double.parseDouble(yMinField.getText());
            double yMax = Double.parseDouble(yMaxField.getText());
            rectangleCords = new RectangleCords(xMin, xMax, yMin, yMax);
            dialog.close();
        });

        grid.add(submitButton, 1, 4);

        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Gets the rectangle coordinates set in the dialog.
     *
     * @return The rectangle coordinates.
     */
    public RectangleCords getRectangleCords() {
        return rectangleCords;
    }
}
