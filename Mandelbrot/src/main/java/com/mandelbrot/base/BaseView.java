package com.mandelbrot.base;

import javafx.scene.Node;

/**
 * BaseView is the abstract base class for views in the MVC pattern.
 * It defines the basic methods for controlling the view.
 *
 * @param <TController> The type of the controller, which extends BaseController.
 * @param <TElement>    The type of the main node element, which extends Node.
 */
public abstract class BaseView<TController extends BaseController, TElement extends Node> {
    protected TElement _mainNode;

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
     * Gets the main node of the view.
     *
     * @return The main node element.
     */
    public TElement getMainNode() {
        return this._mainNode;
    }

    /**
     * Gets the controller associated with this view.
     *
     * @return The controller instance.
     */
    public TController getController() {
        return this._controller;
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller The controller to be assigned to this view.
     */
    public void setController(TController controller) {
        this._controller = controller;
    }
}
