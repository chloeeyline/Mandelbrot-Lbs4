package com.mandelbrot.display;

import com.mandelbrot.base.BaseController;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayController extends BaseController<DisplayView, DisplayModel> {
    public DisplayController(DisplayModel model, DisplayView view) {
        super(model, view);
        drawMandelbrotSet();
    }

    public void drawMandelbrotSet() {
        PixelWriter pw = getView().getImage().getPixelWriter();

        for (int x = 0; x < getModel().getViewDataModel().getImageWidth(); x++) {
            for (int y = 0; y < getModel().getViewDataModel().getImageHeight(); y++) {
                double zx = 0;
                double zy = 0;
                double cX = getModel().getDataModel().getXMin() + (x / getModel().getViewDataModel().getImageWidth()) * (getModel().getDataModel().getXMax() - getModel().getDataModel().getXMin());
                double cY = getModel().getDataModel().getYMin() + (y / getModel().getViewDataModel().getImageHeight()) * (getModel().getDataModel().getYMax() - getModel().getDataModel().getYMin());
                int iter = getModel().computeIterations(zx, zy, cX, cY);
                pw.setColor(x, y, getModel().getColor(iter));
            }
        }

        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            getView().getCanvas().getGraphicsContext2D().drawImage(getView().getImage(), 0, 0);
        });
    }

    public void mouseReleased(MouseEvent evt) {
        if (getModel().getViewDataModel().isDragging()) {
            getView().getDisplayPane().getChildren().remove(getModel().getViewDataModel().getDragZoomRect());
            getModel().mouseReleased(evt.getX(), evt.getY());
            drawMandelbrotSet();
        }
    }

    public void mouseDragged(MouseEvent evt) {
        if (!getModel().getViewDataModel().isDragging()) return;
        double x = evt.getX();
        double y = evt.getY();

        double offsetX = x - getModel().getViewDataModel().getStartX();
        double offsetY = y - getModel().getViewDataModel().getStartY();
        if (!getModel().getViewDataModel().isMovedMouse() && Math.abs(offsetX) < 3 && Math.abs(offsetY) < 3) return;
        getModel().getViewDataModel().setMovedMouse(true);

        if (getModel().getViewDataModel().getMouseAction() == DisplayViewDataModel.MOUSE_ACTION_DRAG) {
            getModel().getViewDataModel().setDragOffsetX(offsetX);
            getModel().getViewDataModel().setDragOffsetY(offsetY);

            //clear the Canvas
            getView().getCanvas().getGraphicsContext2D().setFill(Color.LIGHTGRAY);
            getView().getCanvas().getGraphicsContext2D().fillRect(0, 0, getModel().getViewDataModel().getImageWidth(), getModel().getViewDataModel().getImageHeight());

            // Draw the shifted image
            getView().getCanvas().getGraphicsContext2D().drawImage(getView().getImage(), getModel().getViewDataModel().getDragOffsetX(), getModel().getViewDataModel().getDragOffsetY());
        } else if (getModel().getViewDataModel().getMouseAction() == DisplayViewDataModel.MOUSE_ACTION_ZOOM_IN ||
                getModel().getViewDataModel().getMouseAction() == DisplayViewDataModel.MOUSE_ACTION_ZOOM_OUT) {
            double width = Math.abs(offsetX);
            double height = Math.abs(offsetY);
            if (width < 3 || height < 3) {
                getView().getDisplayPane().getChildren().remove(getModel().getViewDataModel().getDragZoomRect());
                getModel().getViewDataModel().setDragZoomRect(null);
            } else {
                double aspect = getModel().getViewDataModel().getImageWidth() / getModel().getViewDataModel().getImageHeight();
                double rectAspect = width / height;
                if (aspect > rectAspect) width = width * aspect / rectAspect + 0.499;
                else if (aspect < rectAspect) height = height * rectAspect / aspect + 0.499;
                double xMin = getModel().getViewDataModel().getStartX() < x ? getModel().getViewDataModel().getStartX() : getModel().getViewDataModel().getStartX() - width;
                double yMin = getModel().getViewDataModel().getStartY() < y ? getModel().getViewDataModel().getStartY() : getModel().getViewDataModel().getStartY() - height;
                getView().getDisplayPane().getChildren().remove(getModel().getViewDataModel().getDragZoomRect());
                getModel().getViewDataModel().setDragZoomRect(new Rectangle(xMin, yMin, width, height));
                getModel().getViewDataModel().getDragZoomRect().setStroke(Color.BLACK);
                getModel().getViewDataModel().getDragZoomRect().setFill(Color.TRANSPARENT);
                getView().getDisplayPane().getChildren().add(getModel().getViewDataModel().getDragZoomRect());
            }
        }
    }
}
