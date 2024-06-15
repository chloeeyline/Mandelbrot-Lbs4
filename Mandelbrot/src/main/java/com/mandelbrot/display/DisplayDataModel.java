package com.mandelbrot.display;

public class DisplayDataModel {
    // Instance variables
    private double _xMin;
    private double _xMax;
    private double _yMin;
    private double _yMax;
    private int _maxIteration;

    // Constructor initializing with default values
    public DisplayDataModel() {
        this._xMin = -2.5;
        this._xMax = 1.5;
        this._yMin = -2;
        this._yMax = 2;
        this._maxIteration = 1000;
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

    public void setLimits(double xMin, double xMax, double yMin, double yMax) {
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;
    }
}