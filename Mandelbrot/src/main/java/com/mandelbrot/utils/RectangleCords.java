package com.mandelbrot.utils;

/**
 * Class representing the coordinates of a rectangle.
 *
 * @author Chloe
 * @version 1.0
 * @since 24.06.2024
 */
public class RectangleCords {

    /**
     * minimum of the x Cords
     */
    private double xMin;

    /**
     * maximum of the x Cords
     */
    private double xMax;

    /**
     * minimum of the y Cords
     */
    private double yMin;

    /**
     * maximum of the y Cords
     */
    private double yMax;

    /**
     * Gets the minimum X coordinate.
     *
     * @return The minimum X coordinate.
     */
    public double getxMin() {
        return xMin;
    }

    /**
     * Sets the minimum X coordinate.
     *
     * @param xMin The minimum X coordinate to set.
     */
    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    /**
     * Gets the maximum X coordinate.
     *
     * @return The maximum X coordinate.
     */
    public double getxMax() {
        return xMax;
    }

    /**
     * Sets the maximum X coordinate.
     *
     * @param xMax The maximum X coordinate to set.
     */
    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    /**
     * Gets the minimum Y coordinate.
     *
     * @return The minimum Y coordinate.
     */
    public double getyMin() {
        return yMin;
    }

    /**
     * Sets the minimum Y coordinate.
     *
     * @param yMin The minimum Y coordinate to set.
     */
    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    /**
     * Gets the maximum Y coordinate.
     *
     * @return The maximum Y coordinate.
     */
    public double getyMax() {
        return yMax;
    }

    /**
     * Sets the maximum Y coordinate.
     *
     * @param yMax The maximum Y coordinate to set.
     */
    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    /**
     * Constructor to initialize the rectangle coordinates.
     *
     * @param xMin The minimum X coordinate.
     * @param xMax The maximum X coordinate.
     * @param yMin The minimum Y coordinate.
     * @param yMax The maximum Y coordinate.
     */
    public RectangleCords(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
}
