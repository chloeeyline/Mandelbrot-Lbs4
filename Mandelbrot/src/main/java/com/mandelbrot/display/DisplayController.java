package com.mandelbrot.display;

import javafx.scene.input.ScrollEvent;

public class DisplayController {
    private DisplayModel model;
    private DisplayView view;
    private double zoom = 200;
    private double offsetX = 0;
    private double offsetY = 0;

    public DisplayController(DisplayModel model, DisplayView view) {
        this.model = model;
        this.view = view;
    }

    private void handleScroll(ScrollEvent event) {

    }
}
