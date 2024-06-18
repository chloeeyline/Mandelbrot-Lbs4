package com.mandelbrot.display;

import com.mandelbrot.base.BaseView;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DisplayView extends BaseView<DisplayController, VBox> {
    private Pane _displayPane;
    private Canvas _canvas;
    private WritableImage _image;

    private MenuItem _openFile;
    private MenuItem _saveFile;
    private ToggleGroup _toggleGroupIterations;
    private MenuItem _helper;
    private RadioMenuItem _rbCustom;
    private ColorPicker backgroundColorPicker;

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

    @Override
    public void BindActions() {
        _canvas.setOnMousePressed(evt -> _controller.getModel().mousePressed(evt));
        _canvas.setOnMouseReleased(evt -> _controller.mouseReleased(evt));
        _canvas.setOnMouseDragged(evt -> _controller.mouseDragged(evt));
        backgroundColorPicker.setOnAction(evt -> {
            getController().getModel().getDataModel().setBackgroundColor(backgroundColorPicker.getValue());
        });
    }

    private void CreateDisplayControls() {
        _image = new WritableImage(600, 600);
        _canvas = new Canvas(600, 600);
        _displayPane = new Pane();
        _displayPane.getChildren().add(_canvas);
        _mainNode.getChildren().add(_displayPane);
    }

    private void CreateMenuControls() {
        //create MenuBar
        MenuBar menuBar = new MenuBar();

        //add Menu
        Menu fileMenu = new Menu("File");
        //add Items from the Menu
        _openFile = new MenuItem("Open");
        _saveFile = new MenuItem("Save Image");

        //add all Items to the Menu
        fileMenu.getItems().addAll(_openFile, _saveFile);

        Menu iterationsMenu = new Menu("Iterations");

        //create List for iterations
        List<Integer> iterationList = new ArrayList<Integer>();
        iterationList.add(50);
        iterationList.add(100);
        iterationList.add(250);
        iterationList.add(1000);
        iterationList.add(2000);
        iterationList.add(50000);
        iterationList.add(10000);
        iterationList.add(500000);

        //create toggleGroup to add RadioMenu
        _toggleGroupIterations = new ToggleGroup();
        for (Integer element : iterationList) {
            //create RadioMenu from the List
            RadioMenuItem radioButton = new RadioMenuItem(element.toString());
            radioButton.setUserData(element);
            //add to the Group
            radioButton.setToggleGroup(_toggleGroupIterations);
            //add to the Menu
            iterationsMenu.getItems().add(radioButton);
        }

        _rbCustom = new RadioMenuItem("Custom");
        //add to the Group
        _rbCustom.setToggleGroup(_toggleGroupIterations);
        //add to the Menu
        iterationsMenu.getItems().add(_rbCustom);

        Menu editMenu = new Menu("Colors");
        backgroundColorPicker = new ColorPicker();
        CustomMenuItem backgroundColor = new CustomMenuItem(backgroundColorPicker);
        backgroundColor.setHideOnClick(false);
        editMenu.getItems().add(backgroundColor);


        Menu helpMenu = new Menu("Help");
        _helper = new MenuItem("Helper");

        helpMenu.getItems().addAll(_helper);

        //add all Menus to the Bar
        menuBar.getMenus().addAll(fileMenu, editMenu, iterationsMenu, helpMenu);

        _mainNode.getChildren().add(menuBar);
    }
}
