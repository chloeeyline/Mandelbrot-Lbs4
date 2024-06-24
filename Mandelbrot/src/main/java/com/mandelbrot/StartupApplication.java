package com.mandelbrot;

import com.mandelbrot.display.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * The main entry point for the Mandelbrot application.
 */
public class StartupApplication extends Application {

    /**
     * The main method that launches the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // MVC setup for Display
        DisplayModel displayModel = new DisplayModel();
        DisplayViewData displayViewModel = new DisplayViewData();
        DisplayView displayView = new DisplayView();
        new DisplayController(displayView, displayModel, displayViewModel);

        // Setup the main window stage
        Scene scene = new Scene(displayView.getMainNode());
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
