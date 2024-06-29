package com.mandelbrot.display;

import javafx.scene.shape.Rectangle;

/**
 * The view data class for the display, containing data about the current state of the view.
 */
public class DisplayViewData {

    /**
     * Set the width of the image.
     */
    private double _imageWidth = 600;

    /**
     * Set the height of the image.
     */
    private double _imageHeight = 600;

    /**
     *the rectangle for drag zooming
     */
    private Rectangle dragZoomRect;

    /**
     *state of dragging
     */
    private boolean dragging;

    /**
     * state if the mouse moved
     */
    private boolean movedMouse = false;

    /**
     *the start X coordinate for dragging or zooming
     */
    private double startX;

    /**
     *the start Y coordinate for dragging or zooming
     */
    private double startY;

    /**
     *the drag offset in the X direction
     */
    private double dragOffsetX = 0;

    /**
     *the drag offset in the Y direction
     */
    private double dragOffsetY = 0;

    /**
     *gets mouse action
     */
    private int mouseAction;

    // Constants for mouse actions

    /**
     * Set the drag action from the mouse
     */
    public static final int MOUSE_ACTION_DRAG = 1;

    /**
     * Set the zoom in from the mouse
     */
    public static final int MOUSE_ACTION_ZOOM_IN = 2;

    /**
     * Set the zoom out from the mouse
     */
    public static final int MOUSE_ACTION_ZOOM_OUT = 3;

    /**
     * Gets the height of the image.
     *
     * @return The image height.
     */
    public double getImageHeight() {
        return this._imageHeight;
    }

    /**
     * Sets the height of the image.
     *
     * @param _imageHeight The image height to set.
     */
    public void setImageHeight(double _imageHeight) {
        this._imageHeight = _imageHeight;
    }

    /**
     * Gets the width of the image.
     *
     * @return The image width.
     */
    public double getImageWidth() {
        return this._imageWidth;
    }

    /**
     * Sets the width of the image.
     *
     * @param _imageWidth The image width to set.
     */
    public void setImageWidth(double _imageWidth) {
        this._imageWidth = _imageWidth;
    }

    /**
     * Gets the rectangle used for drag zooming.
     *
     * @return The drag zoom rectangle.
     */
    public Rectangle getDragZoomRect() {
        return dragZoomRect;
    }

    /**
     * Sets the rectangle used for drag zooming.
     *
     * @param dragZoomRect The drag zoom rectangle to set.
     */
    public void setDragZoomRect(Rectangle dragZoomRect) {
        this.dragZoomRect = dragZoomRect;
    }

    /**
     * Checks if dragging is in progress.
     *
     * @return True if dragging is in progress, false otherwise.
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Sets the dragging state.
     *
     * @param dragging The dragging state to set.
     */
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    /**
     * Checks if the mouse has moved.
     *
     * @return True if the mouse has moved, false otherwise.
     */
    public boolean isMovedMouse() {
        return movedMouse;
    }

    /**
     * Sets the moved mouse state.
     *
     * @param movedMouse The moved mouse state to set.
     */
    public void setMovedMouse(boolean movedMouse) {
        this.movedMouse = movedMouse;
    }

    /**
     * Gets the start X coordinate for dragging or zooming.
     *
     * @return The start X coordinate.
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Sets the start X coordinate for dragging or zooming.
     *
     * @param startX The start X coordinate to set.
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }

    /**
     * Gets the start Y coordinate for dragging or zooming.
     *
     * @return The start Y coordinate.
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Sets the start Y coordinate for dragging or zooming.
     *
     * @param startY The start Y coordinate to set.
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }

    /**
     * Gets the drag offset in the X direction.
     *
     * @return The drag offset in the X direction.
     */
    public double getDragOffsetX() {
        return dragOffsetX;
    }

    /**
     * Sets the drag offset in the X direction.
     *
     * @param dragOffsetX The drag offset in the X direction to set.
     */
    public void setDragOffsetX(double dragOffsetX) {
        this.dragOffsetX = dragOffsetX;
    }

    /**
     * Gets the drag offset in the Y direction.
     *
     * @return The drag offset in the Y direction.
     */
    public double getDragOffsetY() {
        return dragOffsetY;
    }

    /**
     * Sets the drag offset in the Y direction.
     *
     * @param dragOffsetY The drag offset in the Y direction to set.
     */
    public void setDragOffsetY(double dragOffsetY) {
        this.dragOffsetY = dragOffsetY;
    }

    /**
     * Gets the current mouse action.
     *
     * @return The current mouse action.
     */
    public int getMouseAction() {
        return mouseAction;
    }

    /**
     * Sets the current mouse action.
     *
     * @param mouseAction The mouse action to set.
     */
    public void setMouseAction(int mouseAction) {
        this.mouseAction = mouseAction;
    }
}
