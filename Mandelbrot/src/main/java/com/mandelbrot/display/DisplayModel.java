package com.mandelbrot.display;

import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * The model class for the Mandelbrot set display.
 *
 * @author Chloe
 * @version 1.0
 * @since 24.06.2024
 */
public class DisplayModel {
    // Instance variables for the limits and settings of the Mandelbrot set

    /**
     * The minimum X value.
     */
    private double _xMin;

    /**
     * The maximum X value.
     */
    private double _xMax;

    /**
     * The minimum Y value.
     */
    private double _yMin;

    /**
     * The maximum Y value.
     */
    private double _yMax;

    /**
     * The maximum Iteration value.
     */
    private int _maxIteration;

    /**
     * The background color of the mandelbrot.
     */
    private Color _backgroundColor = Color.BLACK;

    /**
     * The color palette for the mandelbrot.
     */
    private int _colorPalette;

    /**
     * Constructor initializing with default values.
     */
    public DisplayModel() {
        this._xMin = -2.5;
        this._xMax = 1.5;
        this._yMin = -2;
        this._yMax = 2;
        this._maxIteration = 50;
        this._colorPalette = 1;
    }

    /**
     * Gets the minimum X value.
     *
     * @return The minimum X value.
     */
    public double getXMin() {
        return this._xMin;
    }

    /**
     * Gets the maximum X value.
     *
     * @return The maximum X value.
     */
    public double getXMax() {
        return this._xMax;
    }

    /**
     * Gets the minimum Y value.
     *
     * @return The minimum Y value.
     */
    public double getYMin() {
        return this._yMin;
    }

    /**
     * Gets the maximum Y value.
     *
     * @return The maximum Y value.
     */
    public double getYMax() {
        return this._yMax;
    }

    /**
     * Gets the maximum number of iterations for the Mandelbrot set computation.
     *
     * @return The maximum number of iterations.
     */
    public int getMaxIteration() {
        return this._maxIteration;
    }

    /**
     * Sets the maximum number of iterations for the Mandelbrot set computation.
     *
     * @param maxIteration The maximum number of iterations.
     */
    public void setMaxIteration(int maxIteration) {
        this._maxIteration = maxIteration;
    }

    /**
     * Gets the background color.
     *
     * @return The background color.
     */
    public Color getBackgroundColor() {
        return _backgroundColor;
    }

    /**
     * Sets the background color.
     *
     * @param backgroundColor The background color to set.
     */
    public void setBackgroundColor(Color backgroundColor) {
        _backgroundColor = backgroundColor;
    }

    /**
     * Gets the color palette setting.
     *
     * @return The color palette setting.
     */
    public int getColorPalette() {
        return _colorPalette;
    }

    /**
     * Sets the color palette.
     *
     * @param colorPalette The color palette to set.
     */
    public void setColorPalette(int colorPalette) {
        this._colorPalette = colorPalette;
    }

    /**
     * Sets the limits for the Mandelbrot set.
     *
     * @param xMin The minimum X value.
     * @param xMax The maximum X value.
     * @param yMin The minimum Y value.
     * @param yMax The maximum Y value.
     */
    public void setLimits(double xMin, double xMax, double yMin, double yMax) {
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;
    }

    /**
     * Resets the limits to the default values.
     */
    public void resetLimits() {
        this._xMin = -2.5;
        this._xMax = 1.5;
        this._yMin = -2;
        this._yMax = 2;
    }

    /**
     * Saves the current state of the model as a string.
     *
     * @return The string representation of the model state.
     */
    public String saveModelState() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.####################", symbols);
        String text = "xMin=%s; xMax=%s; yMin=%s; yMax=%s; maxIteration=%d; backgroundColor=%s; colorPalette=%d";
        return String.format(text, df.format(_xMin), df.format(_xMax), df.format(_yMin), df.format(_yMax), getMaxIteration(), getBackgroundColor().toString(), getColorPalette());
    }

    /**
     * Reads the model state from a string and updates the model.
     *
     * @param state The string representation of the model state.
     */
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
                case "colorPalette":
                    setColorPalette(Integer.parseInt(keyValue[1]));
                    break;
            }
        }
        setLimits(xMin, xMax, yMin, yMax);
    }

    /**
     * Computes the color for a given iteration.
     *
     * @param iter The iteration count.
     * @return The color corresponding to the iteration.
     */
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

    /**
     * Computes the number of iterations for a given point in the Mandelbrot set.
     *
     * @param zx The real part of the initial complex number.
     * @param zy The imaginary part of the initial complex number.
     * @param cX The real part of the constant complex number.
     * @param cY The imaginary part of the constant complex number.
     * @return The number of iterations.
     */
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
