package com.mandelbrot.display;

import com.mandelbrot.base.BaseModel;
import javafx.scene.paint.Color;

public class DisplayModel extends BaseModel<DisplayDataModel, DisplayViewDataModel> {
    public DisplayModel() {
        setViewDataModel(new DisplayViewDataModel());
        setDataModel(new DisplayDataModel());
    }

    public int computeIterations(double zx, double zy, double cX, double cY) {
        int iteration = getDataModel().getMaxIteration();
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
        double hue = 360.0 * iter / getDataModel().getMaxIteration();
        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }
}