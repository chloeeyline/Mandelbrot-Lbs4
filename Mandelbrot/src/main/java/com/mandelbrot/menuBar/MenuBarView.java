package com.mandelbrot.menuBar;

import com.mandelbrot.base.BaseView;
import javafx.scene.control.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuBarView extends BaseView<MenuBarController> {

    MenuItem openFile;
    MenuItem saveFile;
    ToggleGroup toggleRadioButton;
    Menu helpMenu;
    MenuItem helper;
    MenuItem impressum;

    @Override
    public void DefineControls() {

        //create MenuBar
        MenuBar menuBar = new MenuBar();

        //add Menu
        Menu fileMenu = new Menu("File");
        //add Items from the Menu
        openFile = new MenuItem("Open");
        saveFile = new MenuItem("Save Image");

        //add all Items to the Menu
        fileMenu.getItems().addAll(openFile, saveFile);

        Menu iterationsMenu = new Menu("Iterations");

        //create List for iterations
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(50);
        numbers.add(100);
        numbers.add(250);
        numbers.add(1000);
        numbers.add(2000);
        numbers.add(50000);
        numbers.add(10000);
        numbers.add(500000);


        //create toggleGroup to add RadioMenu
        toggleRadioButton = new ToggleGroup();
        for (Integer element : numbers) {
            //create RadioMenu from the List
            RadioMenuItem radioButton = new RadioMenuItem(element.toString());
            radioButton.setUserData(element);
            //add to the Group
            radioButton.setToggleGroup(toggleRadioButton);
            //add to the Menu
            iterationsMenu.getItems().add(radioButton);
        }

        RadioMenuItem radioButton = new RadioMenuItem("Custom");
        //add to the Group
        radioButton.setToggleGroup(toggleRadioButton);
        //add to the Menu
        iterationsMenu.getItems().add(radioButton);

        Menu editMenu = new Menu("Colors");


        helpMenu = new Menu("Help");
        helper = new MenuItem("Helper");
        impressum = new MenuItem("About us");

        helpMenu.getItems().addAll(helper, impressum);

        //add all Menus to the Bar
        menuBar.getMenus().addAll(fileMenu, editMenu, iterationsMenu ,helpMenu);

        getChildren().add(menuBar);
    }

    @Override
    public void BindActions() {

        //create Actions
        openFile.setOnAction(e -> {
            this._controller.openFile();});

        saveFile.setOnAction(e -> {
            this._controller.SaveImage();});

//        iterationsMenu.getItems().get(8).setOnAction(e ->{
//            String maxIteration = JOptionPane.showInputDialog("What is the max iteration count");});

        //toggleRadioButton.getSelectedToggle().getUserData();

    }
}
