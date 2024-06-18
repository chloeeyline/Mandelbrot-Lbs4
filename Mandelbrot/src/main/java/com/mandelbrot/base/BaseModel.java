package com.mandelbrot.base;

/**
 * BaseModel is the base class for models in the MVC pattern.
 * It manages the data and its representation.
 *
 * @param <TData>     The type of the data managed by this model.
 * @param <TViewData> The type of the view data managed by this model.
 */
public class BaseModel<TData, TViewData> {
    /**
     * The data managed by this model.
     */
    private TData _dataModel;
    /**
     * The view data managed by this model.
     */
    private TViewData _viewDataModel;

    /**
     * Returns the currently assigned data model.
     *
     * @return The currently assigned data model.
     */
    public TData getDataModel() {
        return _dataModel;
    }

    /**
     * Sets the data model for this model.
     *
     * @param dataModel The data model to be assigned to this model.
     */
    public void setDataModel(TData dataModel) {
        this._dataModel = dataModel;
    }

    /**
     * Returns the currently assigned view data model.
     *
     * @return The currently assigned view data model.
     */
    public TViewData getViewDataModel() {
        return _viewDataModel;
    }

    /**
     * Sets the view data model for this model.
     *
     * @param viewDataModel The view data model to be assigned to this model.
     */
    public void setViewDataModel(TViewData viewDataModel) {
        this._viewDataModel = viewDataModel;
    }
}
