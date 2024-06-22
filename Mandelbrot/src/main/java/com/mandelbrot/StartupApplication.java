package com.mandelbrot;

import com.mandelbrot.display.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class StartupApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //MVC for Display
        DisplayModel displayModel = new DisplayModel();
        DisplayViewData displayViewModel = new DisplayViewData();
        DisplayView displayView = new DisplayView();
        new DisplayController(displayView, displayModel, displayViewModel);

        //Stage for the MainWindow
        Scene scene = new Scene(displayView.getMainNode());
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
