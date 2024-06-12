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

    public void doZoomOutFromRect(Rectangle rect) {
        double rectX = rect.getX();
        double rectY = rect.getY();
        double rectW = rect.getWidth();
        double rectH = rect.getHeight();
        double imageWidth = _model.getImageWidth();
        double imageHeight = _model.getImageHeight();

        double newPixelWidth = (_model.getXMax() - _model.getXMin()) / rectW;
        double newPixelHeight = (_model.getYMax() - _model.getYMin()) / rectH;

        double newXMin = _model.getXMin() - newPixelWidth * rectX;
        double newYMax = _model.getYMax() + newPixelHeight * rectY;
        double newWidth = newPixelWidth * imageWidth;
        double newHeight = newPixelHeight * imageHeight;
        double newXMax = newXMin + newWidth;
        double newYMin = newYMax - newHeight;

        _model.setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doDrag(double startX, double startY, double endX, double endY) {
        double xShift = endX - startX;
        double yShift = endY - startY;

        double xRange = _model.getXMax() - _model.getXMin();
        double yRange = _model.getYMax() - _model.getYMin();

        double newXMin = _model.getXMin() - (xShift / _model.getImageWidth()) * xRange;
        double newXMax = _model.getXMax() - (xShift / _model.getImageWidth()) * xRange;
        double newYMin = _model.getYMin() - (yShift / _model.getImageHeight()) * yRange;
        double newYMax = _model.getYMax() - (yShift / _model.getImageHeight()) * yRange;

        _model.setLimits(newXMin, newXMax, newYMin, newYMax);
        _view.drawMandelbrotSet();
    }
}
