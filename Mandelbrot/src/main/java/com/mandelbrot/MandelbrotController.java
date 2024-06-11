package com.mandelbrot;

import javafx.concurrent.Task;
import javafx.scene.input.ScrollEvent;

public class MandelbrotController {
    private MandelbrotModel model;
    private MandelbrotView view;
    private double zoom = 200;
    private double offsetX = 0;
    private double offsetY = 0;

    public MandelbrotController(MandelbrotModel model, MandelbrotView view) {
        this.model = model;
        this.view = view;
        view.getCanvas().addEventHandler(ScrollEvent.SCROLL, this::handleScroll);
    }

    private void handleScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        double mouseX = event.getX();
        double mouseY = event.getY();

        // Calculate the new zoom level
        double zoomFactor = Math.exp(deltaY * 0.01);
        double newZoom = zoom * zoomFactor;

        // Adjust the offsets to keep the zoom centered on the mouse position
        offsetX += (mouseX - view.getCanvas().getWidth() / 2) / zoom - (mouseX - view.getCanvas().getWidth() / 2) / newZoom;
        offsetY += (mouseY - view.getCanvas().getHeight() / 2) / zoom - (mouseY - view.getCanvas().getHeight() / 2) / newZoom;

        zoom = newZoom;

        // Create and start a new task to draw the Mandelbrot set
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                view.drawMandelbrotSet(zoom, offsetX, offsetY);
                return null;
            }
        };

        new Thread(task).start();
    }
}
