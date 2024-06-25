package com.mandelbrot.display;

import com.mandelbrot.base.BaseView;
import com.mandelbrot.utils.LimitsDialog;
import com.mandelbrot.utils.RectangleCords;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

/**
 * The view class for displaying the Mandelbrot set.
 */
public class DisplayView extends BaseView<DisplayController, VBox> {
    private Pane _displayPane;
    private Canvas _canvas;
    private WritableImage _image;
    private ToggleGroup _iterationGroup;
    private ToggleGroup _colorPaletteGroup;

    /**
     * Gets the canvas where the Mandelbrot set is drawn.
     *
     * @return The canvas.
     */
    public Canvas getCanvas() {
        return _canvas;
    }

    /**
     * Gets the image of the Mandelbrot set.
     *
     * @return The image.
     */
    public WritableImage getImage() {
        return _image;
    }

    /**
     * Gets the display pane containing the canvas.
     *
     * @return The display pane.
     */
    public Pane getDisplayPane() {
        return _displayPane;
    }

    /**
     * Gets the Group to that contains the iteration options
     *
     * @return The Group of the iteration.
     */
    public ToggleGroup getIterationGroup() {
        return _iterationGroup;
    }

    /**
     * Gets the Group to that contains the colorPalette options
     *
     * @return The Group of the colorPalette.
     */
    public ToggleGroup getColorPaletteGroup() {
        return _colorPaletteGroup;
    }

    @Override
    public void DefineControls() {
        _mainNode = new VBox();
        CreateMenuControls();
        CreateDisplayControls();
    }

    /**
     * Creates the controls for displaying the Mandelbrot set.
     */
    private void CreateDisplayControls() {
        _image = new WritableImage(600, 600);
        _canvas = new Canvas(600, 600);
        _displayPane = new Pane();
        _displayPane.getChildren().add(_canvas);
        _mainNode.getChildren().add(_displayPane);

        // Set up mouse event handlers
        _canvas.setOnMousePressed(evt -> _controller.mousePressed(evt));
        _canvas.setOnMouseReleased(evt -> _controller.mouseReleased(evt));
        _canvas.setOnMouseDragged(evt -> _controller.mouseDragged(evt));
    }

    /**
     * Creates the menu controls.
     */
    private void CreateMenuControls() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = CreateFileMenu();
        Menu iterationMenu = CreateIterationMenu();
        Menu colorMenu = CreateColorMenu();
        Menu limitsMenu = CreateLimitsMenu();

        menuBar.getMenus().addAll(fileMenu, iterationMenu, colorMenu, limitsMenu);

        _mainNode.getChildren().add(menuBar);
    }

    /**
     * Creates the File menu.
     *
     * @return The File menu.
     */
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

    /**
     * Creates the Iterations menu.
     *
     * @return The Iterations menu.
     */
    private Menu CreateIterationMenu() {
        Menu menu = new Menu("Iterations");
        _iterationGroup = new ToggleGroup();
        int value = 50;

        // Create radio buttons for different iteration counts
        while (value <= 5000) {
            RadioMenuItem radioButton = new RadioMenuItem(Integer.toString(value));
            if (value == 50) radioButton.setSelected(true);
            radioButton.setUserData(value);
            radioButton.setToggleGroup(_iterationGroup);
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

    /**
     * Creates the Limits menu.
     *
     * @return The Limits menu.
     */
    private Menu CreateLimitsMenu() {
        Menu menu = new Menu("Limits");
        MenuItem setLimits = new MenuItem("Limits einstellen");
        MenuItem reset = new MenuItem("Reset");

        setLimits.setOnAction(evt -> {
            RectangleCords initialCords = new RectangleCords(
                    getController().getModel().getXMin(),
                    getController().getModel().getXMax(),
                    getController().getModel().getYMin(),
                    getController().getModel().getYMax()
            );
            LimitsDialog dialog = new LimitsDialog(initialCords);
            RectangleCords newCords = dialog.getRectangleCords();
            if (newCords != null) {
                getController().getModel().setLimits(newCords.getxMin(), newCords.getxMax(), newCords.getyMin(), newCords.getyMax());
                getController().drawMandelbrotSet();
            }
        });

        reset.setOnAction(evt -> {
            getController().getModel().resetLimits();
            getController().drawMandelbrotSet();
        });

        menu.getItems().addAll(setLimits, reset);
        return menu;
    }

    /**
     * Creates the Colors menu.
     *
     * @return The Colors menu.
     */
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
        _colorPaletteGroup = new ToggleGroup();
        CreateColorPaletteOption(colorPaletteMenu, _colorPaletteGroup, true, 0, "Klassisches HSB");
        CreateColorPaletteOption(colorPaletteMenu, _colorPaletteGroup, false, 1, "Blau-Töne");
        CreateColorPaletteOption(colorPaletteMenu, _colorPaletteGroup, false, 2, "Grün-Töne");
        CreateColorPaletteOption(colorPaletteMenu, _colorPaletteGroup, false, 3, "Feuer-Töne");
        CreateColorPaletteOption(colorPaletteMenu, _colorPaletteGroup, false, 4, "Lila-Töne");

        menu.getItems().addAll(backgroundColor, colorPaletteMenu);
        return menu;
    }

    /**
     * Creates an option for the color palette menu.
     *
     * @param menu     The menu to add the option to.
     * @param toggle   The toggle group for the radio buttons.
     * @param selected Whether the option should be selected by default.
     * @param value    The value associated with the option.
     * @param text     The text for the option.
     */
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
