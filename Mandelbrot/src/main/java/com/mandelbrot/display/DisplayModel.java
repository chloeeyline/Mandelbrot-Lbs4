package com.mandelbrot.display;

import com.mandelbrot.base.DrawData;
import javafx.scene.paint.Color;

public class DisplayModel extends DrawData {

    public int computeIterations(double zx, double zy, double cX, double cY) {
        iteration = 1000;
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
        double hue = 360.0 * iter / 1000;
        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }
}