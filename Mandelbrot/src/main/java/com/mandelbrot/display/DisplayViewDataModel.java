package com.mandelbrot.display;

public class DisplayViewDataModel {
    private double _imageWidth = 600;
    private double _imageHeight = 600;

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
