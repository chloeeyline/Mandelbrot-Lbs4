package com.mandelbrot.base;

import javafx.scene.control.Menu;

public abstract class BaseMenuView<TController extends BaseController> extends Menu implements BaseView<TController> {
    protected TController _controller;

    @Override
    public void setController(TController controller) {
        this._controller = controller;
    }
}
