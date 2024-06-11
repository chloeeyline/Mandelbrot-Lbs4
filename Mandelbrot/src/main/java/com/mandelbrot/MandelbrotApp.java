package com.mandelbrot;

import com.mandelbrot.display.DisplayController;
import com.mandelbrot.display.DisplayModel;
import com.mandelbrot.display.DisplayView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MandelbrotApp extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DisplayModel model = new DisplayModel();
        DisplayView view = new DisplayView(model, WIDTH, HEIGHT);
        new DisplayController(model, view);

        Pane root = new Pane();
        root.getChildren().add(view.getCanvas());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();

        view.drawMandelbrotSet();
    }
}
