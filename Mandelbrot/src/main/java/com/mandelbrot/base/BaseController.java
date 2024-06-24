package com.mandelbrot.base;

/**
 * BaseController is the base class for controllers in the MVC pattern.
 *
 * @param <TView>     The type of the view, which extends BaseView.
 * @param <TModel>    The type of the model.
 * @param <TViewData> The type of the view data.
 */
public class BaseController<TView extends BaseView, TModel, TViewData> {
    private TView _view;
    private TModel _model;
    private TViewData _viewData;

    /**
     * Constructor to initialize the controller with view, model, and view data.
     *
     * @param view      The view instance.
     * @param dataModel The model instance.
     * @param viewModel The view data instance.
     */
    public BaseController(TView view, TModel dataModel, TViewData viewModel) {
        setView(view);
        this._model = dataModel;
        this._viewData = viewModel;
    }

    /**
     * Gets the view associated with this controller.
     *
     * @return The view instance.
     */
    public TView getView() {
        return this._view;
    }

    /**
     * Sets the view for this controller and defines its controls.
     *
     * @param view The view instance to be set.
     */
    public void setView(TView view) {
        if (this._view != null) this._view.setController(null);
        this._view = view;
        this._view.setController(this);
        this._view.DefineControls();
    }

    /**
     * Gets the model associated with this controller.
     *
     * @return The model instance.
     */
    public TModel getModel() {
        return this._model;
    }

    /**
     * Sets the model for this controller.
     *
     * @param dataModel The model instance to be set.
     */
    public void setModel(TModel dataModel) {
        this._model = dataModel;
    }

    /**
     * Gets the view data associated with this controller.
     *
     * @return The view data instance.
     */
    public TViewData getViewData() {
        return this._viewData;
    }

    /**
     * Sets the view data for this controller.
     *
     * @param viewModel The view data instance to be set.
     */
    public void setViewData(TViewData viewModel) {
        this._viewData = viewModel;
    }
}
