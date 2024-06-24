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
    private Color _BackgroundColor = Color.BLACK;

    // Constructor initializing with default values
    public DisplayModel() {
        this._xMin = -2.5;
        this._xMax = 1.5;
        this._yMin = -2;
        this._yMax = 2;
        this._maxIteration = 50;
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
        return _BackgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        _BackgroundColor = backgroundColor;
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

    public String getModelState() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.####################", symbols);
        String text = "xMin=%s; xMax=%s; yMin=%s; yMax=%s; maxIteration=%d; backgroundColor=%s";
        return String.format(text,
                df.format(_xMin),
                df.format(_xMax),
                df.format(_yMin),
                df.format(_yMax),
                getMaxIteration(),
                getBackgroundColor().toString());
    }

    public void setModelState(String state) {
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
}
