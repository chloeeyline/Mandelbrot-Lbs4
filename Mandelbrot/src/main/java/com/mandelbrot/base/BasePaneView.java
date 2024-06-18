package com.mandelbrot.base;

import javafx.scene.layout.Pane;

public abstract class BasePaneView<TController extends BaseController> extends Pane implements BaseView<TController> {
    protected TController _controller;

    @Override
    public void setController(TController controller) {
        this._controller = controller;
    }
}

