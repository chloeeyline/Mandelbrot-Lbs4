package com.mandelbrot.taskBar.limitsDialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LimitsDialog extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = dialog();

        //Stage for Dialog
        stage.setTitle("set Limits");
        stage.setScene(scene);
        stage.show();

    }

    private Scene dialog(){

        String text = "Enter the x and y limits for the region " +
                        "to be displayed in the image.";

        GridPane gridPane = new GridPane();
        HBox hBox = new HBox(10);
        VBox vBox = new VBox(10);

        TextField txtminX = new TextField();
        TextField txtmaxX = new TextField();
        TextField txtminY = new TextField();
        TextField txtmaxY = new TextField();

        Button btnOK = new Button("OK");
        Button btnCancel = new Button("Cancel");

        hBox.getChildren().addAll(btnOK, btnCancel);
        GridPane.setConstraints(hBox,0,0);
        vBox.getChildren().addAll(txtmaxX, txtminX, txtmaxY, txtminY);
        GridPane.setConstraints(vBox, 1,0);

        return new Scene(gridPane);
    }
}
