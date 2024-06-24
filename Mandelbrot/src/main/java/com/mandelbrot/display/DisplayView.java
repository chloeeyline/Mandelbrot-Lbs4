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
        Menu limitsMenu = CreateLimitsMenu();

        menuBar.getMenus().addAll(fileMenu, iterationMenu, colorMenu, limitsMenu);

        _mainNode.getChildren().add(menuBar);
    }

    private Menu CreateFileMenu() {
        Menu menu = new Menu("File");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save Image");
        MenuItem help = new MenuItem("Help");

        openFile.setOnAction(evt -> getController().loadImageWithMetadata());
        saveFile.setOnAction(evt -> getController().saveImageWithMetadata());

        menu.getItems().addAll(openFile, saveFile, help);
        return menu;
    }

    private Menu CreateIterationMenu() {
        Menu menu = new Menu("Iterations");
        ToggleGroup toggleGroupIterations = new ToggleGroup();
        int value = 50;

        while (value <= 15000) {
            RadioMenuItem radioButton = new RadioMenuItem(Integer.toString(value));
            if (value == 50) radioButton.setSelected(true);
            radioButton.setUserData(value);
            radioButton.setToggleGroup(toggleGroupIterations);
            radioButton.setOnAction(evt -> {
                if (radioButton.isSelected())
                    getController().getModel().setMaxIteration((int) radioButton.getUserData());
                getController().drawMandelbrotSet();
            });
            menu.getItems().add(radioButton);
            value *= 2;
        }
        return menu;
    }

    private Menu CreateLimitsMenu() {
        Menu menu = new Menu("Limits");
        MenuItem setLimits = new MenuItem("Limits einstellen");
        MenuItem reset = new MenuItem("Reset");

        setLimits.setOnAction(evt -> {

        });

        reset.setOnAction(evt -> {
            getController().getModel().resetLimits();
            getController().drawMandelbrotSet();
        });

        menu.getItems().addAll(setLimits, reset);
        return menu;
    }

    private Menu CreateColorMenu() {
        Menu menu = new Menu("Colors");
        ColorPicker backgroundColorPicker = new ColorPicker();
        CustomMenuItem backgroundColor = new CustomMenuItem(backgroundColorPicker);
        backgroundColor.setHideOnClick(false);
        backgroundColorPicker.setOnAction(evt -> {
            getController().getModel().setBackgroundColor(backgroundColorPicker.getValue());
            getController().drawMandelbrotSet();
        });

        Menu colorPaletteMenu = new Menu("Farbpalette");
        ToggleGroup toggleGroupColorPalette = new ToggleGroup();
        CreateColorPaletteOption(colorPaletteMenu, toggleGroupColorPalette, true, 0, "Klassisches HSB");
        CreateColorPaletteOption(colorPaletteMenu, toggleGroupColorPalette, false, 1, "Blau-Töne");
        CreateColorPaletteOption(colorPaletteMenu, toggleGroupColorPalette, false, 2, "Grün-Töne");
        CreateColorPaletteOption(colorPaletteMenu, toggleGroupColorPalette, false, 3, "Feuer-Töne");
        CreateColorPaletteOption(colorPaletteMenu, toggleGroupColorPalette, false, 4, "Lila-Töne");

        menu.getItems().addAll(backgroundColor, colorPaletteMenu);
        return menu;
    }

    private void CreateColorPaletteOption(Menu menu, ToggleGroup toggle, boolean selected, int value, String text) {
        RadioMenuItem rmi = new RadioMenuItem(text);
        menu.getItems().add(rmi);
        rmi.setToggleGroup(toggle);
        rmi.setSelected(selected);
        rmi.setUserData(value);
        rmi.setOnAction(evt -> {
            if (rmi.isSelected())
                getController().getModel().setColorPalette((int) rmi.getUserData());
            getController().drawMandelbrotSet();
        });
    }
}
