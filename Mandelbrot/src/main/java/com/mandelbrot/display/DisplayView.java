package com.mandelbrot.display;

import com.mandelbrot.base.BaseView;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

public class DisplayView extends BaseView<DisplayController> {
    private Canvas _canvas;
    private WritableImage _image;

    public Canvas getCanvas() {
        return _canvas;
    }

    public WritableImage getImage() {
        return _image;
    }

    @Override
    public void DefineControls() {
        _image = new WritableImage(600, 600);
        _canvas = new Canvas(600, 600);
        getChildren().add(_canvas);
    }

    @Override
    public void BindActions() {
        _canvas.setOnMousePressed(evt -> _controller.getModel().mousePressed(evt));
        _canvas.setOnMouseReleased(evt -> _controller.mouseReleased(evt));
        _canvas.setOnMouseDragged(evt -> _controller.mouseDragged(evt));
    }
}
