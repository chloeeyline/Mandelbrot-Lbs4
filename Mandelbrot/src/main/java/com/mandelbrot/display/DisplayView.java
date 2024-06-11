package com.mandelbrot.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayView {
    private final Canvas canvas;
    private final WritableImage image;
    private final DisplayModel model;

    private double xmin, xmax, ymin, ymax;
    private double zoom = 200; // initial zoom level
    private double offsetX = 0;
    private double offsetY = 0;

    private Pane pane;
    private Rectangle dragZoomRect;
    private boolean dragging;
    private boolean movedMouse = false;
    private double startX, startY;
    private int mouseAction;

    private static final int MOUSE_ACTION_DRAG = 1;
    private static final int MOUSE_ACTION_ZOOM_IN = 2;
    private static final int MOUSE_ACTION_ZOOM_OUT = 3;
    private int defaultMouseAction = MOUSE_ACTION_DRAG;

    public DisplayView(DisplayModel model, int width, int height) {
        this.model = model;
        this.canvas = new Canvas(width, height);
        this.image = new WritableImage(width, height);
        this.pane = new Pane();
        pane.getChildren().add(canvas);
        canvas.setOnMousePressed(new MouseHandler());
        canvas.setOnMouseReleased(new MouseHandler());
        canvas.setOnMouseDragged(new MouseHandler());

        // Initial limits for the Mandelbrot set
        xmin = -2.0;
        xmax = 1.0;
        ymin = -1.5;
        ymax = 1.5;
    }

    public Pane getCanvas() {
        return pane;
    }

    public void drawMandelbrotSet() {
        PixelWriter pixelWriter = image.getPixelWriter();
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double zx = 0;
                double zy = 0;
                double cX = xmin + (x / (double) width) * (xmax - xmin);
                double cY = ymin + (y / (double) height) * (ymax - ymin);
                int iter = model.computeIterations(zx, zy, cX, cY);
                pixelWriter.setColor(x, y, model.getColor(iter));
            }
        }

        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        });
    }

    private void setLimits(double xmin, double xmax, double ymin, double ymax) {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }

    private class MouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent evt) {
            if (evt.getEventType() == MouseEvent.MOUSE_PRESSED) {
                handleMousePressed(evt);
            } else if (evt.getEventType() == MouseEvent.MOUSE_RELEASED) {
                handleMouseReleased(evt);
            } else if (evt.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                handleMouseDragged(evt);
            }
        }

        private void handleMousePressed(MouseEvent evt) {
            if (dragging) return;
            startX = evt.getX();
            startY = evt.getY();
            if (startX > pane.getWidth() || startY > pane.getHeight()) return;

            if (evt.isPrimaryButtonDown()) mouseAction = MOUSE_ACTION_DRAG;
            else if (evt.isSecondaryButtonDown()) mouseAction = MOUSE_ACTION_ZOOM_IN;
            else if (evt.isMiddleButtonDown()) mouseAction = MOUSE_ACTION_ZOOM_OUT;
            else mouseAction = defaultMouseAction;

            dragging = true;
            movedMouse = false;
        }

        private void handleMouseReleased(MouseEvent evt) {
            if (!dragging) return;

            if (mouseAction == MOUSE_ACTION_DRAG) {
                // Implement your logic to handle dragging
            } else if (mouseAction == MOUSE_ACTION_ZOOM_IN && dragZoomRect != null) {
                doZoomInOnRect(dragZoomRect);
            } else if (mouseAction == MOUSE_ACTION_ZOOM_OUT && dragZoomRect != null) {
                doZoomOutFromRect(dragZoomRect);
            }

            dragging = false;
            dragZoomRect = null;
        }

        private void handleMouseDragged(MouseEvent evt) {
            if (!dragging) return;
            double x = evt.getX();
            double y = evt.getY();

            double offsetX = x - startX;
            double offsetY = y - startY;
            if (!movedMouse && Math.abs(offsetX) < 3 && Math.abs(offsetY) < 3) return;
            movedMouse = true;

            if (mouseAction == MOUSE_ACTION_DRAG) {
                // Implement your logic to handle dragging
            } else if (mouseAction == MOUSE_ACTION_ZOOM_IN || mouseAction == MOUSE_ACTION_ZOOM_OUT) {
                double width = Math.abs(offsetX);
                double height = Math.abs(offsetY);
                if (width < 3 || height < 3) {
                    pane.getChildren().remove(dragZoomRect);
                    dragZoomRect = null;
                } else {
                    double aspect = pane.getWidth() / pane.getHeight();
                    double rectAspect = width / height;
                    if (aspect > rectAspect) width = width * aspect / rectAspect + 0.499;
                    else if (aspect < rectAspect) height = height * rectAspect / aspect + 0.499;
                    double xmin = startX < x ? startX : startX - width;
                    double ymin = startY < y ? startY : startY - height;
                    pane.getChildren().remove(dragZoomRect);
                    dragZoomRect = new Rectangle(xmin, ymin, width, height);
                    dragZoomRect.setStroke(Color.BLACK);
                    dragZoomRect.setFill(Color.TRANSPARENT);
                    pane.getChildren().add(dragZoomRect);
                }
            }
        }

        private void doZoomInOnRect(Rectangle rect) {
            double rectX = rect.getX();
            double rectY = rect.getY();
            double rectW = rect.getWidth();
            double rectH = rect.getHeight();
            double imageWidth = canvas.getWidth();
            double imageHeight = canvas.getHeight();

            double newXmin = xmin + (rectX / imageWidth) * (xmax - xmin);
            double newXmax = xmin + ((rectX + rectW) / imageWidth) * (xmax - xmin);
            double newYmin = ymin + (rectY / imageHeight) * (ymax - ymin);
            double newYmax = ymin + ((rectY + rectH) / imageHeight) * (ymax - ymin);
            pane.getChildren().remove(dragZoomRect);
            setLimits(newXmin, newXmax, newYmin, newYmax);

            drawMandelbrotSet();
        }

        private void doZoomOutFromRect(Rectangle rect) {
            // Implementation for zooming out
        }
    }
}
