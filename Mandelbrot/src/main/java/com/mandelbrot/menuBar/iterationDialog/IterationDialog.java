package com.mandelbrot.menuBar.iterationDialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IterationDialog extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = dialog();

        //Stage for Dialog
        stage.setTitle("Set max Iteration");
        stage.setScene(scene);
        stage.show();

    }

    private Scene dialog(){

        GridPane gridPane = new GridPane();

        VBox vbox = new VBox(10);
        String text = "What is the max iteration count?";
        Label lbText = new Label(text);
        TextField txtInput = new TextField();

        vbox.getChildren().addAll(lbText, txtInput);
        GridPane.setConstraints(vbox, 0,0);

        HBox hBox = new HBox(10);
        Button btnOK = new Button("OK");
        Button btnCancel = new Button("Cancel");
        hBox.getChildren().addAll(btnOK, btnCancel);

        GridPane.setConstraints(hBox,0,1);

        gridPane.getChildren().addAll(vbox, hBox);

        return new Scene(gridPane);
    }
}
