package com.mandelbrot.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayView extends Pane {
    private final Canvas canvas;
    private final WritableImage image;
    private DisplayController controller;

    private Rectangle _dragZoomRect;
    private boolean dragging;
    private boolean movedMouse = false;
    private double startX, startY;
    private int mouseAction;

    private static final int MOUSE_ACTION_DRAG = 1;
    private static final int MOUSE_ACTION_ZOOM_IN = 2;
    private static final int MOUSE_ACTION_ZOOM_OUT = 3;

    public DisplayView() {
        this.image = new WritableImage(800, 800);
        this.canvas = new Canvas(800, 800);
        getChildren().add(canvas);
        canvas.setOnMousePressed(new MouseHandler());
        canvas.setOnMouseReleased(new MouseHandler());
        canvas.setOnMouseDragged(new MouseHandler());
    }

    public void setController(DisplayController controller) {
        this.controller = controller;
    }

    public void drawMandelbrotSet() {
        PixelWriter pixelWriter = image.getPixelWriter();
        controller.drawMandelbrotSet(pixelWriter);
        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        });
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
            if (startX > getWidth() || startY > getHeight()) return;

            if (evt.isPrimaryButtonDown()) mouseAction = MOUSE_ACTION_DRAG;
            else if (evt.isSecondaryButtonDown()) mouseAction = MOUSE_ACTION_ZOOM_IN;
            else if (evt.isMiddleButtonDown()) mouseAction = MOUSE_ACTION_ZOOM_OUT;
            else return;

            dragging = true;
            movedMouse = false;
        }

        private void handleMouseReleased(MouseEvent evt) {
            if (!dragging) return;

            if (mouseAction == MOUSE_ACTION_DRAG) {
                // Implement your logic to handle dragging
            } else if (mouseAction == MOUSE_ACTION_ZOOM_IN && _dragZoomRect != null) {
                controller.doZoomInOnRect(_dragZoomRect);
                getChildren().remove(_dragZoomRect);
                drawMandelbrotSet();
            } else if (mouseAction == MOUSE_ACTION_ZOOM_OUT && _dragZoomRect != null) {
//                doZoomOutFromRect(dragZoomRect);
            }

            getChildren().remove(_dragZoomRect);
            dragging = false;
            _dragZoomRect = null;
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
                    getChildren().remove(_dragZoomRect);
                    _dragZoomRect = null;
                } else {
                    double aspect = getWidth() / getHeight();
                    double rectAspect = width / height;
                    if (aspect > rectAspect) width = width * aspect / rectAspect + 0.499;
                    else if (aspect < rectAspect) height = height * rectAspect / aspect + 0.499;
                    double xMin = startX < x ? startX : startX - width;
                    double yMin = startY < y ? startY : startY - height;
                    getChildren().remove(_dragZoomRect);
                    _dragZoomRect = new Rectangle(xMin, yMin, width, height);
                    _dragZoomRect.setStroke(Color.BLACK);
                    _dragZoomRect.setFill(Color.TRANSPARENT);
                    getChildren().add(_dragZoomRect);
                }
            }
        }
    }
}
