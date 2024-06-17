package com.mandelbrot;

import com.mandelbrot.menuBar.MenuBarController;
import com.mandelbrot.menuBar.MenuBarModel;
import com.mandelbrot.menuBar.MenuBarView;
import com.mandelbrot.display.DisplayController;
import com.mandelbrot.display.DisplayModel;
import com.mandelbrot.display.DisplayView;
import com.mandelbrot.taskBar.TaskBarController;
import com.mandelbrot.taskBar.TaskBarModel;
import com.mandelbrot.taskBar.TaskBarView;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class StartupApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //MVC for MenuBar
        MenuBarModel menuBarModel = new MenuBarModel();
        MenuBarView MenuView = new MenuBarView();
        MenuBarController MenuController = new MenuBarController(menuBarModel, MenuView);

        DisplayModel model = new DisplayModel();
        DisplayView view = new DisplayView();
        new DisplayController(model, view);

        //MVC for Display
        DisplayModel displayModel = new DisplayModel();
        DisplayView displayView = new DisplayView();
        DisplayController displayController = new DisplayController(displayModel, displayView);

        //MVC for Taskbar
        TaskBarModel taskBarModel = new TaskBarModel();
        TaskBarView taskBarView = new TaskBarView();
        TaskBarController taskBarController = new TaskBarController(taskBarModel, taskBarView);

        //MainContainer for Menu/Display/TitleBar
        VBox root = new VBox();
        root.getChildren().addAll(MenuView, displayView, taskBarView);
        Scene scene = new Scene(root);

        //Stage for the MainWindow
        primaryStage.setTitle("Mandelbrot Set");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
