package com.mandelbrot.display;

import javafx.scene.image.PixelWriter;
import javafx.scene.shape.Rectangle;

public class DisplayController {
    private final DisplayModel _model;
    private final DisplayView _view;

    public DisplayController(DisplayModel model, DisplayView view) {
        this._model = model;
        this._view = view;

        this._view.setController(this);
        this._view.drawMandelbrotSet();
    }

    public void drawMandelbrotSet(PixelWriter pw) {
        for (int x = 0; x < _model.getImageWidth(); x++) {
            for (int y = 0; y < _model.getImageHeight(); y++) {
                double zx = 0;
                double zy = 0;
                double cX = _model.getXMin() + (x / _model.getImageWidth()) * (_model.getXMax() - _model.getXMin());
                double cY = _model.getYMin() + (y / _model.getImageHeight()) * (_model.getYMax() - _model.getYMin());
                int iter = _model.computeIterations(zx, zy, cX, cY);
                pw.setColor(x, y, _model.getColor(iter));
            }
        }
    }

    public void doZoomInOnRect(Rectangle rect) {
        double newXMin = _model.getXMin() + (rect.getX() / _model.getImageWidth()) * (_model.getXMax() - _model.getXMin());
        double newXMax = _model.getXMin() + ((rect.getX() + rect.getWidth()) / _model.getImageWidth()) * (_model.getXMax() - _model.getXMin());
        double newYMin = _model.getYMin() + (rect.getY() / _model.getImageHeight()) * (_model.getYMax() - _model.getYMin());
        double newYMax = _model.getYMin() + ((rect.getY() + rect.getHeight()) / _model.getImageHeight()) * (_model.getYMax() - _model.getYMin());
        _model.setLimits(newXMin, newXMax, newYMin, newYMax);
    }
}
