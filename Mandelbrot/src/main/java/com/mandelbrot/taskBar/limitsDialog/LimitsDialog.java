package com.mandelbrot.taskBar.limitsDialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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

        GridPane gridPane = new GridPane();


        return new Scene(gridPane);
    }
}
