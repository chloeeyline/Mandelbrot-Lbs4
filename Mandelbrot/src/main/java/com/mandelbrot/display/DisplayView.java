package com.mandelbrot.display;

import com.mandelbrot.base.BaseView;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class DisplayView extends BaseView<DisplayController, VBox> {
    private Pane _displayPane;
    private Canvas _canvas;
    private WritableImage _image;

    public Canvas getCanvas() {
        return _canvas;
    }

    public WritableImage getImage() {
        return _image;
    }

    public Pane getDisplayPane() {
        return _displayPane;
    }

    @Override
    public void DefineControls() {
        _mainNode = new VBox();
        CreateMenuControls();
        CreateDisplayControls();
    }

    private void CreateDisplayControls() {
        _image = new WritableImage(600, 600);
        _canvas = new Canvas(600, 600);
        _displayPane = new Pane();
        _displayPane.getChildren().add(_canvas);
        _mainNode.getChildren().add(_displayPane);

        _canvas.setOnMousePressed(evt -> _controller.mousePressed(evt));
        _canvas.setOnMouseReleased(evt -> _controller.mouseReleased(evt));
        _canvas.setOnMouseDragged(evt -> _controller.mouseDragged(evt));
    }

    private void CreateMenuControls() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = CreateFileMenu();
        Menu iterationMenu = CreateIterationMenu();
        Menu colorMenu = CreateColorMenu();
        Menu helpMenu = new Menu("Help");

        menuBar.getMenus().addAll(fileMenu, iterationMenu, colorMenu, helpMenu);

        _mainNode.getChildren().add(menuBar);
    }

    private Menu CreateFileMenu() {
        Menu menu = new Menu("File");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save Image");
        menu.getItems().addAll(openFile, saveFile);
        return menu;
    }

    private Menu CreateIterationMenu() {
        Menu menu = new Menu("Iterations");
        ToggleGroup toggleGroupIterations = new ToggleGroup();

        for (Integer element : new int[]{50, 100, 250, 1000, 2000, 10000, 50000, 500000}) {
            RadioMenuItem radioButton = new RadioMenuItem(element.toString());
            radioButton.setUserData(element);
            radioButton.setToggleGroup(toggleGroupIterations);
            radioButton.setOnAction(evt -> {
                if (radioButton.isSelected()) getController().getModel().setMaxIteration(element);
                getController().drawMandelbrotSet();
            });
            menu.getItems().add(radioButton);
        }

        RadioMenuItem customIteration = new RadioMenuItem("Custom");
        customIteration.setToggleGroup(toggleGroupIterations);
        menu.getItems().add(customIteration);
        return menu;
    }

    private Menu CreateColorMenu() {
        Menu menu = new Menu("Colors");
        ColorPicker backgroundColorPicker = new ColorPicker();
        CustomMenuItem backgroundColor = new CustomMenuItem(backgroundColorPicker);
        backgroundColor.setHideOnClick(false);
        menu.getItems().add(backgroundColor);

        backgroundColorPicker.setOnAction(evt -> {
            getController().getModel().setBackgroundColor(backgroundColorPicker.getValue());
            getController().drawMandelbrotSet();
        });

        return menu;
    }
}
