package com.mandelbrot.display;

import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DisplayModel {
    // Instance variables
    private double _xMin;
    private double _xMax;
    private double _yMin;
    private double _yMax;
    private int _maxIteration;
    private Color _backgroundColor = Color.BLACK;
    private int _colorPalette;

    // Constructor initializing with default values
    public DisplayModel() {
        this._xMin = -2.5;
        this._xMax = 1.5;
        this._yMin = -2;
        this._yMax = 2;
        this._maxIteration = 50;
        this._colorPalette = 1;
    }

    public double getXMin() {
        return this._xMin;
    }

    public double getXMax() {
        return this._xMax;
    }

    public double getYMin() {
        return this._yMin;
    }

    public double getYMax() {
        return this._yMax;
    }

    public int getMaxIteration() {
        return this._maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
        this._maxIteration = maxIteration;
    }

    public Color getBackgroundColor() {
        return _backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        _backgroundColor = backgroundColor;
    }

    public int getColorPalette() {
        return _colorPalette;
    }

    public void setColorPalette(int colorPalette) {
        this._colorPalette = colorPalette;
    }

    public void setLimits(double xMin, double xMax, double yMin, double yMax) {
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;
    }

    public void resetLimits() {
        this._xMin = -2.5;
        this._xMax = 1.5;
        this._yMin = -2;
        this._yMax = 2;
    }

    public String saveModelState() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.####################", symbols);
        String text = "xMin=%s; xMax=%s; yMin=%s; yMax=%s; maxIteration=%d; backgroundColor=%s";
        return String.format(text, df.format(_xMin), df.format(_xMax), df.format(_yMin), df.format(_yMax), getMaxIteration(), getBackgroundColor().toString());
    }

    public void readModelState(String state) {
        String[] parts = state.split("; ");
        double xMin = 0;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0;
        for (String part : parts) {
            String[] keyValue = part.split("=");
            switch (keyValue[0]) {
                case "xMin":
                    xMin = Double.parseDouble(keyValue[1]);
                    break;
                case "xMax":
                    xMax = Double.parseDouble(keyValue[1]);
                    break;
                case "yMin":
                    yMin = Double.parseDouble(keyValue[1]);
                    break;
                case "yMax":
                    yMax = Double.parseDouble(keyValue[1]);
                    break;
                case "maxIteration":
                    setMaxIteration(Integer.parseInt(keyValue[1]));
                    break;
                case "backgroundColor":
                    setBackgroundColor(Color.web(keyValue[1]));
                    break;
            }
        }
        setLimits(xMin, xMax, yMin, yMax);
    }

    public Color getColor(int iter) {
        if (iter == 0) return _backgroundColor;

        double hue;
        if (_colorPalette == 1) hue = 240.0 + (120.0 * iter / _maxIteration); // Bereich von Blau bis Cyan
        else if (_colorPalette == 2) hue = 120.0 * iter / _maxIteration; // Bereich von Grün bis Gelb
        else if (_colorPalette == 3) hue = (iter % 256); // Bereiche von Rot, Orange, Gelb und zurück
        else if (_colorPalette == 4) hue = 270.0 + (90.0 * iter / _maxIteration); // Bereich von Violett bis Magenta
        else hue = 360.0 * iter / _maxIteration;

        return Color.hsb(hue, 1.0, iter / (iter + 8.0));
    }

    public int computeIterations(double zx, double zy, double cX, double cY) {
        int iteration = _maxIteration;
        while (zx * zx + zy * zy < 4 && iteration > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iteration--;
        }
        return iteration;
    }
}
