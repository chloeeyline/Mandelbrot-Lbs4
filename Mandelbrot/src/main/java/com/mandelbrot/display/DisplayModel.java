package com.mandelbrot.display;

import com.mandelbrot.base.BaseModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DisplayModel extends BaseModel<DisplayDataModel, DisplayViewDataModel> {
    public DisplayModel() {
        setViewDataModel(new DisplayViewDataModel());
        setDataModel(new DisplayDataModel());
    }

    public int computeIterations(double zx, double zy, double cX, double cY) {
        int iteration = getDataModel().getMaxIteration();
        while (zx * zx + zy * zy < 4 && iteration > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iteration--;
        }
        return iteration;
    }

    public Color getColor(int iter) {
        if (iter == 0) {
            return getDataModel().getBackgroundColor();
        }
        double hue = 360.0 * iter / getDataModel().getMaxIteration();
        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }

    public void doDrag(double endX, double endY) {
        double xShift = endX - getViewDataModel().getStartX();
        double yShift = endY - getViewDataModel().getStartY();

        double xRange = getDataModel().getXMax() - getDataModel().getXMin();
        double yRange = getDataModel().getYMax() - getDataModel().getYMin();

        double newXMin = getDataModel().getXMin() - (xShift / getViewDataModel().getImageWidth()) * xRange;
        double newXMax = getDataModel().getXMax() - (xShift / getViewDataModel().getImageWidth()) * xRange;
        double newYMin = getDataModel().getYMin() - (yShift / getViewDataModel().getImageHeight()) * yRange;
        double newYMax = getDataModel().getYMax() - (yShift / getViewDataModel().getImageHeight()) * yRange;

        getDataModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doZoomInOnRect() {
        double newXMin = getDataModel().getXMin() + (getViewDataModel().getDragZoomRect().getX() / getViewDataModel().getImageWidth()) * (getDataModel().getXMax() - getDataModel().getXMin());
        double newXMax = getDataModel().getXMin() + ((getViewDataModel().getDragZoomRect().getX() + getViewDataModel().getDragZoomRect().getWidth()) / getViewDataModel().getImageWidth()) * (getDataModel().getXMax() - getDataModel().getXMin());
        double newYMin = getDataModel().getYMin() + (getViewDataModel().getDragZoomRect().getY() / getViewDataModel().getImageHeight()) * (getDataModel().getYMax() - getDataModel().getYMin());
        double newYMax = getDataModel().getYMin() + ((getViewDataModel().getDragZoomRect().getY() + getViewDataModel().getDragZoomRect().getHeight()) / getViewDataModel().getImageHeight()) * (getDataModel().getYMax() - getDataModel().getYMin());
        getDataModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doZoomOutFromRect() {
        if (getViewDataModel().getDragZoomRect() == null) return;
        double rectX = getViewDataModel().getDragZoomRect().getX();
        double rectY = getViewDataModel().getDragZoomRect().getY();
        double rectW = getViewDataModel().getDragZoomRect().getWidth();
        double rectH = getViewDataModel().getDragZoomRect().getHeight();
        double imageWidth = getViewDataModel().getImageWidth();
        double imageHeight = getViewDataModel().getImageHeight();

        double newPixelWidth = (getDataModel().getXMax() - getDataModel().getXMin()) / rectW;
        double newPixelHeight = (getDataModel().getYMax() - getDataModel().getYMin()) / rectH;

        double newXMin = getDataModel().getXMin() - newPixelWidth * rectX;
        double newYMax = getDataModel().getYMax() + newPixelHeight * rectY;
        double newWidth = newPixelWidth * imageWidth;
        double newHeight = newPixelHeight * imageHeight;
        double newXMax = newXMin + newWidth;
        double newYMin = newYMax - newHeight;

        getDataModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void mousePressed(MouseEvent evt) {
        if (!getViewDataModel().isDragging()) {
            getViewDataModel().setStartX(evt.getX());
            getViewDataModel().setStartY(evt.getY());

            if (getViewDataModel().getStartX() > getViewDataModel().getImageWidth() ||
                    getViewDataModel().getStartY() > getViewDataModel().getImageHeight()) return;

            if (evt.isPrimaryButtonDown())
                getViewDataModel().setMouseAction(DisplayViewDataModel.MOUSE_ACTION_DRAG);
            else if (evt.isSecondaryButtonDown())
                getViewDataModel().setMouseAction(DisplayViewDataModel.MOUSE_ACTION_ZOOM_IN);
            else if (evt.isMiddleButtonDown())
                getViewDataModel().setMouseAction(DisplayViewDataModel.MOUSE_ACTION_ZOOM_OUT);
            else return;

            getViewDataModel().setDragging(true);
            getViewDataModel().setMovedMouse(false);
        }

    }

    public void mouseReleased(double x, double y) {
        if (!getViewDataModel().isDragging()) return;

        if (getViewDataModel().getMouseAction() == DisplayViewDataModel.MOUSE_ACTION_DRAG &&
                (getViewDataModel().getDragOffsetX() != 0 ||
                        getViewDataModel().getDragOffsetY() != 0)) {
            doDrag(x, y);
        } else if (getViewDataModel().getMouseAction() == DisplayViewDataModel.MOUSE_ACTION_ZOOM_IN &&
                getViewDataModel().getDragZoomRect() != null) {
            doZoomInOnRect();
        } else if (getViewDataModel().getMouseAction() == DisplayViewDataModel.MOUSE_ACTION_ZOOM_OUT) {
            doZoomOutFromRect();
        }

        if (getViewDataModel().isDragging()) {
            getViewDataModel().setDragging(false);
            getViewDataModel().setDragZoomRect(null);
            getViewDataModel().setDragOffsetX(0);
            getViewDataModel().setDragOffsetX(1);
        }
    }
}