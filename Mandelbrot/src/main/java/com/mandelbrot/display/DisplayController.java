package com.mandelbrot.display;

import com.mandelbrot.base.BaseController;
import javafx.scene.image.PixelWriter;
import javafx.scene.shape.Rectangle;

public class DisplayController extends BaseController<DisplayView, DisplayModel> {
    public DisplayController(DisplayModel model, DisplayView view) {
        super(model,view);
        _view.drawMandelbrotSet();
    }

    public void drawMandelbrotSet(PixelWriter pw) {
        for (int x = 0; x < getModel().getViewDataModel().getImageWidth(); x++) {
            for (int y = 0; y < getModel().getViewDataModel().getImageHeight(); y++) {
                double zx = 0;
                double zy = 0;
                double cX = getModel().getDataModel().getXMin() + (x / getModel().getViewDataModel().getImageWidth()) * (getModel().getDataModel().getXMax() - getModel().getDataModel().getXMin());
                double cY = getModel().getDataModel().getYMin() + (y / getModel().getViewDataModel().getImageHeight()) * (getModel().getDataModel().getYMax() - getModel().getDataModel().getYMin());
                int iter = _model.computeIterations(zx, zy, cX, cY);
                pw.setColor(x, y, _model.getColor(iter));
            }
        }
    }

    public void doZoomInOnRect(Rectangle rect) {
        double newXMin = getModel().getDataModel().getXMin() + (rect.getX() / getModel().getViewDataModel().getImageWidth()) * (getModel().getDataModel().getXMax() - getModel().getDataModel().getXMin());
        double newXMax = getModel().getDataModel().getXMin() + ((rect.getX() + rect.getWidth()) / getModel().getViewDataModel().getImageWidth()) * (getModel().getDataModel().getXMax() - getModel().getDataModel().getXMin());
        double newYMin = getModel().getDataModel().getYMin() + (rect.getY() / getModel().getViewDataModel().getImageHeight()) * (getModel().getDataModel().getYMax() - getModel().getDataModel().getYMin());
        double newYMax = getModel().getDataModel().getYMin() + ((rect.getY() + rect.getHeight()) / getModel().getViewDataModel().getImageHeight()) * (getModel().getDataModel().getYMax() - getModel().getDataModel().getYMin());
        getModel().getDataModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doZoomOutFromRect(Rectangle rect) {
        double rectX = rect.getX();
        double rectY = rect.getY();
        double rectW = rect.getWidth();
        double rectH = rect.getHeight();
        double imageWidth = getModel().getViewDataModel().getImageWidth();
        double imageHeight = getModel().getViewDataModel().getImageHeight();

        double newPixelWidth = (getModel().getDataModel().getXMax() - getModel().getDataModel().getXMin()) / rectW;
        double newPixelHeight = (getModel().getDataModel().getYMax() - getModel().getDataModel().getYMin()) / rectH;

        double newXMin = getModel().getDataModel().getXMin() - newPixelWidth * rectX;
        double newYMax = getModel().getDataModel().getYMax() + newPixelHeight * rectY;
        double newWidth = newPixelWidth * imageWidth;
        double newHeight = newPixelHeight * imageHeight;
        double newXMax = newXMin + newWidth;
        double newYMin = newYMax - newHeight;

        getModel().getDataModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doDrag(double startX, double startY, double endX, double endY) {
        double xShift = endX - startX;
        double yShift = endY - startY;

        double xRange = getModel().getDataModel().getXMax() - getModel().getDataModel().getXMin();
        double yRange = getModel().getDataModel().getYMax() - getModel().getDataModel().getYMin();

        double newXMin = getModel().getDataModel().getXMin() - (xShift / getModel().getViewDataModel().getImageWidth()) * xRange;
        double newXMax = getModel().getDataModel().getXMax() - (xShift / getModel().getViewDataModel().getImageWidth()) * xRange;
        double newYMin = getModel().getDataModel().getYMin() - (yShift / getModel().getViewDataModel().getImageHeight()) * yRange;
        double newYMax = getModel().getDataModel().getYMax() - (yShift / getModel().getViewDataModel().getImageHeight()) * yRange;

        getModel().getDataModel().setLimits(newXMin, newXMax, newYMin, newYMax);
        _view.drawMandelbrotSet();
    }
}
