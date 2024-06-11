package com.mandelbrot;

import MenuBarPackage.MenuBarView;
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
        MandelbrotModel model = new MandelbrotModel();
        MandelbrotView view = new MandelbrotView(model, WIDTH, HEIGHT);
        MenuBarView MenuView = new MenuBarView();
        new MandelbrotController(model, view);

        VBox root = new VBox();
        root.getChildren().addAll(MenuView.createMenuBar(), view.getCanvas());
        Scene scene = new Scene(root);
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.sizeToScene();

        view.drawMandelbrotSet(200, 0, 0);
    }
}
