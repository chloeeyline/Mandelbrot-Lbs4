package com.mandelbrot.menuBar;

import com.mandelbrot.base.BasePaneView;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class MenuBarView extends BasePaneView<MenuBarController> {

    private MenuItem _openFile;
    private MenuItem _saveFile;
    private ToggleGroup _toggleGroupIterations;
    private MenuItem _helper;
    private MenuItem _impressum;
    private RadioMenuItem _rbCustom;

    @Override
    public void DefineControls() {

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


         Menu helpMenu = new Menu("Help");
        _helper = new MenuItem("Helper");
        _impressum = new MenuItem("About us");

        helpMenu.getItems().addAll(_helper, _impressum);

        //add all Menus to the Bar
        menuBar.getMenus().addAll(fileMenu, editMenu, iterationsMenu ,helpMenu);

        getChildren().add(menuBar);
    }

    @Override
    public void BindActions() {

        //create Actions

        _openFile.setOnAction(e -> {
            this._controller.openFile();});

        _saveFile.setOnAction(e -> {
            this._controller.SaveImage();});

        _rbCustom.setOnAction(e ->{});

        //_toggleGroupIterations.getSelectedToggle().getUserData();

        _helper.setOnAction(e -> {});

        _impressum.setOnAction(e ->{});

    }

    //create getter and setter for class variablen

    public RadioMenuItem get_rbCustom() {
        return _rbCustom;
    }

    public void set_rbCustom(RadioMenuItem _rbCustom) {
        this._rbCustom = _rbCustom;
    }

    public MenuItem get_impressum() {
        return _impressum;
    }

    public void set_impressum(MenuItem _impressum) {
        this._impressum = _impressum;
    }

    public MenuItem get_helper() {
        return _helper;
    }

    public void set_helper(MenuItem _helper) {
        this._helper = _helper;
    }

    public ToggleGroup get_toggleGroupIterations() {
        return _toggleGroupIterations;
    }

    public void set_toggleGroupIterations(ToggleGroup _toggleGroupIterations) {
        this._toggleGroupIterations = _toggleGroupIterations;
    }

    public MenuItem get_saveFile() {
        return _saveFile;
    }

    public void set_saveFile(MenuItem _saveFile) {
        this._saveFile = _saveFile;
    }

    public MenuItem get_openFile() {
        return _openFile;
    }

    public void set_openFile(MenuItem _openFile) {
        this._openFile = _openFile;
    }
}
