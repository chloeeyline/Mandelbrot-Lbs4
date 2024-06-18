package com.mandelbrot.base;

/**
 * BaseView is the interface for views in the MVC pattern.
 * It defines the basic methods for controlling the view.
 *
 * @param <TController> The type of the controller, which extends BaseController.
 */
public interface BaseView<TController extends BaseController> {
    /**
     * Defines the controls in the view.
     * This method must be implemented by implementing classes.
     */
    void DefineControls();

    /**
     * Binds the actions in the view.
     * This method must be implemented by implementing classes.
     */
    void BindActions();

    /**
     * Sets the controller for this view.
     *
     * @param controller The controller to be assigned to this view.
     */
    void setController(TController controller);
}
