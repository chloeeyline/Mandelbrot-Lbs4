package MenuBar;

import javafx.scene.control.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuBarView {

    private MenuBarController _controller;

    public MenuBarView(MenuBarController controller){

        this._controller = controller;
    }

    public MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save Image");

        fileMenu.getItems().addAll(openFile, saveFile);

        Menu interationsMenu = new Menu("Iterations");

        List<Object> numbers = new ArrayList<>();
        numbers.add(50);
        numbers.add(100);
        numbers.add(250);
        numbers.add(1000);
        numbers.add(2000);
        numbers.add(50000);
        numbers.add(10000);
        numbers.add(500000);
        numbers.add("Custom");

        ToggleGroup toggleRadioButton = new ToggleGroup();
        for (Object element : numbers) {
            RadioMenuItem radioButton = new RadioMenuItem(element.toString());
            radioButton.setToggleGroup(toggleRadioButton);
            interationsMenu.getItems().add(radioButton);
        }

        Menu editMenu = new Menu("Colors");
        MenuItem limits = new MenuItem("");

        editMenu.getItems().addAll(limits);

        Menu helpMenu = new Menu("Help");
        MenuItem helper = new MenuItem("Helper");
        MenuItem impressum = new MenuItem("About us");

        helpMenu.getItems().addAll(helper, impressum);

        menuBar.getMenus().addAll(fileMenu, editMenu, interationsMenu ,helpMenu);

        openFile.setOnAction(e -> {
            this._controller.openFile();});

        saveFile.setOnAction(e -> {
            this._controller.SaveImage();});

        return menuBar;
    }

    public MenuBarController get_controller() {
        return _controller;
    }

    public void set_controller(MenuBarController _controller) {
        this._controller = _controller;
    }

}
