package com.mandelbrot.base;

/**
 * BaseController is the base class for controllers in the MVC pattern.
 * It manages the interaction between the model and the view.
 *
 * @param <TView>  The type of the view, which extends BaseView.
 * @param <TModel> The type of the model, which extends BaseModel.
 */
public class BaseController<TView extends BaseView, TModel extends BaseModel> {
    /**
     * The view managed by this controller.
     */
    private TView _view;
    /**
     * The model managed by this controller.
     */
    private TModel _model;

    /**
     * Constructor for BaseController.
     *
     * @param model The model assigned to this controller.
     * @param view  The view assigned to this controller.
     */
    public BaseController(TModel model, TView view) {
        this._model = model;
        setView(view);
    }

    /**
     * Returns the currently assigned model.
     *
     * @return The currently assigned model.
     */
    public TModel getModel() {
        return this._model;
    }

    /**
     * Sets the model for this controller.
     *
     * @param model The model to be assigned to this controller.
     */
    public void setModel(TModel model) {
        this._model = model;
    }

    /**
     * Returns the currently assigned view.
     *
     * @return The currently assigned view.
     */
    public TView getView() {
        return this._view;
    }

    /**
     * Sets the view for this controller and configures the control.
     *
     * @param view The view to be assigned to this controller.
     */
    public void setView(TView view) {
        if (this._view != null) this._view.setController(null);
        this._view = view;
        this._view.setController(this);
        this._view.DefineControls();
        this._view.BindActions();
    }
}
