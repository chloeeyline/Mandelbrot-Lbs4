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
    private final Canvas _canvas;
    private final WritableImage _image;
    private DisplayController _controller;

    private Rectangle _dragZoomRect;
    private boolean _dragging, _movedMouse = false;
    private double _startX, _startY, _dragOffsetX = 0, _dragOffsetY = 0;
    private int _mouseAction;

    private static final int MOUSE_ACTION_DRAG = 1;
    private static final int MOUSE_ACTION_ZOOM_IN = 2;
    private static final int MOUSE_ACTION_ZOOM_OUT = 3;

    public DisplayView() {
        this._image = new WritableImage(800, 800);
        this._canvas = new Canvas(800, 800);
        getChildren().add(_canvas);
        _canvas.setOnMousePressed(new MouseHandler());
        _canvas.setOnMouseReleased(new MouseHandler());
        _canvas.setOnMouseDragged(new MouseHandler());
    }

    public void setController(DisplayController controller) {
        this._controller = controller;
    }

    public void drawMandelbrotSet() {
        PixelWriter pixelWriter = _image.getPixelWriter();
        _controller.drawMandelbrotSet(pixelWriter);
        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            _canvas.getGraphicsContext2D().drawImage(_image, 0, 0);
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
            if (_dragging) return;
            _startX = evt.getX();
            _startY = evt.getY();
            if (_startX > getWidth() || _startY > getHeight()) return;

            if (evt.isPrimaryButtonDown()) _mouseAction = MOUSE_ACTION_DRAG;
            else if (evt.isSecondaryButtonDown()) _mouseAction = MOUSE_ACTION_ZOOM_IN;
            else if (evt.isMiddleButtonDown()) _mouseAction = MOUSE_ACTION_ZOOM_OUT;
            else return;

            _dragging = true;
            _movedMouse = false;
        }

        private void handleMouseReleased(MouseEvent evt) {
            if (!_dragging) return;

            if (_mouseAction == MOUSE_ACTION_DRAG && (_dragOffsetX != 0 || _dragOffsetY != 0)) {
                _controller.doDrag(_startX, _startY, evt.getX(), evt.getY());
            } else if (_mouseAction == MOUSE_ACTION_ZOOM_IN && _dragZoomRect != null) {
                _controller.doZoomInOnRect(_dragZoomRect);
            } else if (_mouseAction == MOUSE_ACTION_ZOOM_OUT && _dragZoomRect != null) {
                _controller.doZoomOutFromRect(_dragZoomRect);
            }

            getChildren().remove(_dragZoomRect);
            _dragging = false;
            _dragZoomRect = null;
            _dragOffsetX = 0;
            _dragOffsetY = 0;
            drawMandelbrotSet();
        }

        private void handleMouseDragged(MouseEvent evt) {
            if (!_dragging) return;
            double x = evt.getX();
            double y = evt.getY();

            double offsetX = x - _startX;
            double offsetY = y - _startY;
            if (!_movedMouse && Math.abs(offsetX) < 3 && Math.abs(offsetY) < 3) return;
            _movedMouse = true;

            if (_mouseAction == MOUSE_ACTION_DRAG) {
                _dragOffsetX = offsetX;
                _dragOffsetY = offsetY;

                redraw();
            } else if (_mouseAction == MOUSE_ACTION_ZOOM_IN || _mouseAction == MOUSE_ACTION_ZOOM_OUT) {
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
                    double xMin = _startX < x ? _startX : _startX - width;
                    double yMin = _startY < y ? _startY : _startY - height;
                    getChildren().remove(_dragZoomRect);
                    _dragZoomRect = new Rectangle(xMin, yMin, width, height);
                    _dragZoomRect.setStroke(Color.BLACK);
                    _dragZoomRect.setFill(Color.TRANSPARENT);
                    getChildren().add(_dragZoomRect);
                }
            }
        }

        private void redraw() {
            // Clear the canvas
            _canvas.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
            _canvas.getGraphicsContext2D().fillRect(0, 0, _canvas.getWidth(), _canvas.getHeight());

            // Draw the shifted image
            _canvas.getGraphicsContext2D().drawImage(_image, _dragOffsetX, _dragOffsetY);
        }
    }
}
