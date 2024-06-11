package com.mandelbrot.base;

import java.math.BigDecimal;

public abstract class DrawData {
    // Constants for default values
    protected static final BigDecimal DEFAULT_XMIN = BigDecimal.ZERO;
    protected static final BigDecimal DEFAULT_XMAX = BigDecimal.ONE;
    protected static final BigDecimal DEFAULT_YMIN = BigDecimal.ZERO;
    protected static final BigDecimal DEFAULT_YMAX = BigDecimal.ONE;
    protected static final int DEFAULT_ITERATION = 1000;

    // Instance variables
    protected BigDecimal xMin;
    protected BigDecimal xMax;
    protected BigDecimal yMin;
    protected BigDecimal yMax;
    protected Integer iteration;

    // Constructor initializing with default values
    public DrawData() {
        this.xMin = DEFAULT_XMIN;
        this.xMax = DEFAULT_XMAX;
        this.yMin = DEFAULT_YMIN;
        this.yMax = DEFAULT_YMAX;
        this.iteration = DEFAULT_ITERATION;
    }

    public BigDecimal getXMin() {
        return xMin;
    }

    public void setXMin(BigDecimal xMin) {
        this.xMin = xMin;
    }

    public BigDecimal getXMax() {
        return xMax;
    }

    public void setXMax(BigDecimal xMax) {
        this.xMax = xMax;
    }

    public BigDecimal getYMin() {
        return yMin;
    }

    public void setYMin(BigDecimal yMin) {
        this.yMin = yMin;
    }

    public BigDecimal getYMax() {
        return yMax;
    }

    public void setYMax(BigDecimal yMax) {
        this.yMax = yMax;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }
}