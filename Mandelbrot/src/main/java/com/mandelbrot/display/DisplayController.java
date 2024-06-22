package com.mandelbrot.display;

import com.mandelbrot.base.BaseController;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayController extends BaseController<DisplayView, DisplayModel, DisplayViewData> {
    public DisplayController(DisplayView view, DisplayModel model, DisplayViewData viewData) {
        super(view, model, viewData);
        drawMandelbrotSet();
    }

    public void drawMandelbrotSet() {
        PixelWriter pw = getView().getImage().getPixelWriter();

        for (int x = 0; x < getViewData().getImageWidth(); x++) {
            for (int y = 0; y < getViewData().getImageHeight(); y++) {
                double zx = 0;
                double zy = 0;
                double cX = getModel().getXMin() + (x / getViewData().getImageWidth()) * (getModel().getXMax() - getModel().getXMin());
                double cY = getModel().getYMin() + (y / getViewData().getImageHeight()) * (getModel().getYMax() - getModel().getYMin());
                int iter = computeIterations(zx, zy, cX, cY);
                pw.setColor(x, y, getColor(iter));
            }
        }

        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> getView().getCanvas().getGraphicsContext2D().drawImage(getView().getImage(), 0, 0));
    }

    public void mouseReleased(MouseEvent evt) {
        if (getViewData().isDragging()) {
            getView().getDisplayPane().getChildren().remove(getViewData().getDragZoomRect());
            mouseReleased(evt.getX(), evt.getY());
            drawMandelbrotSet();
        }
    }

    public void mouseDragged(MouseEvent evt) {
        if (!getViewData().isDragging()) return;
        double x = evt.getX();
        double y = evt.getY();

        double offsetX = x - getViewData().getStartX();
        double offsetY = y - getViewData().getStartY();
        if (!getViewData().isMovedMouse() && Math.abs(offsetX) < 3 && Math.abs(offsetY) < 3) return;
        getViewData().setMovedMouse(true);

        if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_DRAG) {
            getViewData().setDragOffsetX(offsetX);
            getViewData().setDragOffsetY(offsetY);

            //clear the Canvas
            getView().getCanvas().getGraphicsContext2D().setFill(Color.LIGHTGRAY);
            getView().getCanvas().getGraphicsContext2D().fillRect(0, 0, getViewData().getImageWidth(), getViewData().getImageHeight());

            // Draw the shifted image
            getView().getCanvas().getGraphicsContext2D().drawImage(getView().getImage(), getViewData().getDragOffsetX(), getViewData().getDragOffsetY());
        } else if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_IN ||
                getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_OUT) {
            double width = Math.abs(offsetX);
            double height = Math.abs(offsetY);
            if (width < 3 || height < 3) {
                getView().getDisplayPane().getChildren().remove(getViewData().getDragZoomRect());
                getViewData().setDragZoomRect(null);
            } else {
                double aspect = getViewData().getImageWidth() / getViewData().getImageHeight();
                double rectAspect = width / height;
                if (aspect > rectAspect) width = width * aspect / rectAspect + 0.499;
                else if (aspect < rectAspect) height = height * rectAspect / aspect + 0.499;
                double xMin = getViewData().getStartX() < x ? getViewData().getStartX() : getViewData().getStartX() - width;
                double yMin = getViewData().getStartY() < y ? getViewData().getStartY() : getViewData().getStartY() - height;
                getView().getDisplayPane().getChildren().remove(getViewData().getDragZoomRect());
                getViewData().setDragZoomRect(new Rectangle(xMin, yMin, width, height));
                getViewData().getDragZoomRect().setStroke(Color.BLACK);
                getViewData().getDragZoomRect().setFill(Color.TRANSPARENT);
                getView().getDisplayPane().getChildren().add(getViewData().getDragZoomRect());
            }
        }
    }

    public int computeIterations(double zx, double zy, double cX, double cY) {
        int iteration = getModel().getMaxIteration();
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
            return getModel().getBackgroundColor();
        }
        double hue = 360.0 * iter / getModel().getMaxIteration();
        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }

    public void doDrag(double endX, double endY) {
        double xShift = endX - getViewData().getStartX();
        double yShift = endY - getViewData().getStartY();

        double xRange = getModel().getXMax() - getModel().getXMin();
        double yRange = getModel().getYMax() - getModel().getYMin();

        double newXMin = getModel().getXMin() - (xShift / getViewData().getImageWidth()) * xRange;
        double newXMax = getModel().getXMax() - (xShift / getViewData().getImageWidth()) * xRange;
        double newYMin = getModel().getYMin() - (yShift / getViewData().getImageHeight()) * yRange;
        double newYMax = getModel().getYMax() - (yShift / getViewData().getImageHeight()) * yRange;

        getModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doZoomInOnRect() {
        double newXMin = getModel().getXMin() + (getViewData().getDragZoomRect().getX() / getViewData().getImageWidth()) * (getModel().getXMax() - getModel().getXMin());
        double newXMax = getModel().getXMin() + ((getViewData().getDragZoomRect().getX() + getViewData().getDragZoomRect().getWidth()) / getViewData().getImageWidth()) * (getModel().getXMax() - getModel().getXMin());
        double newYMin = getModel().getYMin() + (getViewData().getDragZoomRect().getY() / getViewData().getImageHeight()) * (getModel().getYMax() - getModel().getYMin());
        double newYMax = getModel().getYMin() + ((getViewData().getDragZoomRect().getY() + getViewData().getDragZoomRect().getHeight()) / getViewData().getImageHeight()) * (getModel().getYMax() - getModel().getYMin());
        getModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void doZoomOutFromRect() {
        if (getViewData().getDragZoomRect() == null) return;
        double rectX = getViewData().getDragZoomRect().getX();
        double rectY = getViewData().getDragZoomRect().getY();
        double rectW = getViewData().getDragZoomRect().getWidth();
        double rectH = getViewData().getDragZoomRect().getHeight();
        double imageWidth = getViewData().getImageWidth();
        double imageHeight = getViewData().getImageHeight();

        double newPixelWidth = (getModel().getXMax() - getModel().getXMin()) / rectW;
        double newPixelHeight = (getModel().getYMax() - getModel().getYMin()) / rectH;

        double newXMin = getModel().getXMin() - newPixelWidth * rectX;
        double newYMax = getModel().getYMax() + newPixelHeight * rectY;
        double newWidth = newPixelWidth * imageWidth;
        double newHeight = newPixelHeight * imageHeight;
        double newXMax = newXMin + newWidth;
        double newYMin = newYMax - newHeight;

        getModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    public void mousePressed(MouseEvent evt) {
        if (!getViewData().isDragging()) {
            getViewData().setStartX(evt.getX());
            getViewData().setStartY(evt.getY());

            if (getViewData().getStartX() > getViewData().getImageWidth() ||
                    getViewData().getStartY() > getViewData().getImageHeight()) return;

            if (evt.isPrimaryButtonDown())
                getViewData().setMouseAction(DisplayViewData.MOUSE_ACTION_DRAG);
            else if (evt.isSecondaryButtonDown())
                getViewData().setMouseAction(DisplayViewData.MOUSE_ACTION_ZOOM_IN);
            else if (evt.isMiddleButtonDown())
                getViewData().setMouseAction(DisplayViewData.MOUSE_ACTION_ZOOM_OUT);
            else return;

            getViewData().setDragging(true);
            getViewData().setMovedMouse(false);
        }

    }

    public void mouseReleased(double x, double y) {
        if (!getViewData().isDragging()) return;

        if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_DRAG &&
                (getViewData().getDragOffsetX() != 0 ||
                        getViewData().getDragOffsetY() != 0)) {
            doDrag(x, y);
        } else if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_IN &&
                getViewData().getDragZoomRect() != null) {
            doZoomInOnRect();
        } else if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_OUT) {
            doZoomOutFromRect();
        }

        if (getViewData().isDragging()) {
            getViewData().setDragging(false);
            getViewData().setDragZoomRect(null);
            getViewData().setDragOffsetX(0);
            getViewData().setDragOffsetX(1);
        }
    }
}
