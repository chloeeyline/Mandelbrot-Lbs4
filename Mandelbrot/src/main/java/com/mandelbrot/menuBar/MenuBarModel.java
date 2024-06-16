package com.mandelbrot.menuBar;

import com.mandelbrot.base.BaseModel;

public class MenuBarModel extends BaseModel<MenuBarDataModel, MenuBarViewDataModel> {

    public MenuBarModel(){
        setViewDataModel(new MenuBarViewDataModel());
        setDataModel(new MenuBarDataModel());
    }

}
