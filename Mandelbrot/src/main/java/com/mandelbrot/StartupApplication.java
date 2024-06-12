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

public class StartupApplication extends Application {
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

        //MVC for Display
        DisplayModel displayModel = new DisplayModel();
        DisplayView displayView = new DisplayView();
        DisplayController displayController = new DisplayController(displayModel, displayView);

        //MainContainer for Menu/Display/TitleBar
        VBox root = new VBox();
        root.getChildren().addAll(MenuView.createMenuBar(), displayView);
        Scene scene = new Scene(root);

        //Stage for the MainWindow
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}