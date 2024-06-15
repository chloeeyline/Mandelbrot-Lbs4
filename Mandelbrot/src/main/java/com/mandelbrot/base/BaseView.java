package com.mandelbrot.base;

import javafx.scene.layout.Pane;

/**
 * BaseView is the abstract base class for views in the MVC pattern.
 * It defines the basic methods for controlling the view.
 *
 * @param <TController> The type of the controller, which extends BaseController.
 */
public abstract class BaseView<TController extends BaseController> extends Pane {
    /**
     * The controller associated with this view.
     */
    protected TController _controller;

    /**
     * Abstract method to define the controls in the view.
     * This method must be implemented by subclasses.
     */
    public abstract void DefineControls();

    /**
     * Abstract method to bind the actions in the view.
     * This method must be implemented by subclasses.
     */
    public abstract void BindActions();

    /**
     * Sets the controller for this view.
     *
     * @param controller The controller to be assigned to this view.
     */
    public void setController(TController controller) {
        this._controller = controller;
    }
}
