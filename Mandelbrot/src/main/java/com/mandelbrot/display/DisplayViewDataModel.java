package com.mandelbrot.display;

public class DisplayViewDataModel {
    private double _imageWidth = 800;
    private double _imageHeight = 800;

    public double getImageHeight() {
        return this._imageHeight;
    }

    public void setImageHeight(double _imageHeight) {
        this._imageHeight = _imageHeight;
    }

    public double getImageWidth() {
        return this._imageWidth;
    }

    public void setImageWidth(double _imageWidth) {
        this._imageWidth = _imageWidth;
    }
}
