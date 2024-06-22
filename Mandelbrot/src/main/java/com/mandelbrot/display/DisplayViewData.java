package com.mandelbrot.display;

import javafx.scene.shape.Rectangle;

public class DisplayViewData {
    private double _imageWidth = 600;
    private double _imageHeight = 600;

    private Rectangle dragZoomRect;
    private boolean dragging;
    private boolean movedMouse = false;
    private double startX;
    private double startY;
    private double dragOffsetX = 0;
    private double dragOffsetY = 0;
    private int mouseAction;

    public static final int MOUSE_ACTION_DRAG = 1;
    public static final int MOUSE_ACTION_ZOOM_IN = 2;
    public static final int MOUSE_ACTION_ZOOM_OUT = 3;

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

    public Rectangle getDragZoomRect() {
        return dragZoomRect;
    }

    public void setDragZoomRect(Rectangle dragZoomRect) {
        this.dragZoomRect = dragZoomRect;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isMovedMouse() {
        return movedMouse;
    }

    public void setMovedMouse(boolean movedMouse) {
        this.movedMouse = movedMouse;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getDragOffsetX() {
        return dragOffsetX;
    }

    public void setDragOffsetX(double dragOffsetX) {
        this.dragOffsetX = dragOffsetX;
    }

    public double getDragOffsetY() {
        return dragOffsetY;
    }

    public void setDragOffsetY(double dragOffsetY) {
        this.dragOffsetY = dragOffsetY;
    }

    public int getMouseAction() {
        return mouseAction;
    }

    public void setMouseAction(int mouseAction) {
        this.mouseAction = mouseAction;
    }
}
