package com.mandelbrot.base;

import javafx.scene.layout.Pane;

public abstract class BaseView<TController extends BaseController
        > extends Pane {
    protected TController _controller;

    public abstract void DefineControls();
    public abstract void BindActions();

    public void setController(TController controller) {
        this._controller = controller;
    }
}
