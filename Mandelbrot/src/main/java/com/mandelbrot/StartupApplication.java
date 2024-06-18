package com.mandelbrot;

import com.mandelbrot.display.DisplayController;
import com.mandelbrot.display.DisplayModel;
import com.mandelbrot.display.DisplayView;
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
        DisplayView displayView = new DisplayView();
        new DisplayController(displayModel, displayView);

        //Stage for the MainWindow
        Scene scene = new Scene(displayView.getMainNode());
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
