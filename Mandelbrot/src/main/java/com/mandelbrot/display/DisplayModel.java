package com.mandelbrot.display;

import com.mandelbrot.base.DrawData;
import javafx.scene.paint.Color;

public class DisplayModel extends DrawData {

    private double _imageWidth = 800;
    private double _imageHeight = 800;

    public int computeIterations(double zx, double zy, double cX, double cY) {
        int iteration = this._maxIteration;
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
            return Color.BLACK;
        }
        double hue = 360.0 * iter / this._maxIteration;
        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }

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