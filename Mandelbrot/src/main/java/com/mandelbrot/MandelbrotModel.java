package com.mandelbrot;

import javafx.scene.paint.Color;

public class MandelbrotModel {
    private static final int MAX_ITER = 1000;

    public int computeIterations(double zx, double zy, double cX, double cY) {
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }

    public Color getColor(int iter) {
        if (iter == 0) {
            return Color.BLACK;
        }
        double hue = 360.0 * iter / MAX_ITER;
        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }

    private Color interpolateColor(Color startColor, Color endColor, double ratio) {
        double red = startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio;
        double green = startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio;
        double blue = startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio;
        return new Color(red, green, blue, 1.0);
    }
}