package com.mandelbrot;

import MenuBar.MenuBarController;
import MenuBar.MenuBarView;
import com.mandelbrot.display.DisplayController;
import com.mandelbrot.display.DisplayModel;
import com.mandelbrot.display.DisplayView;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MandelbrotApp extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        MenuBarController MenuController = new MenuBarController();
        MenuBarView MenuView = new MenuBarView(MenuController);

        DisplayModel model = new DisplayModel();
        DisplayView view = new DisplayView(model, WIDTH, HEIGHT);
        new DisplayController(model, view);

        VBox root = new VBox();
        root.getChildren().addAll(MenuView.createMenuBar(), view.getCanvas());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();

        view.drawMandelbrotSet();
    }
}
