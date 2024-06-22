package com.mandelbrot.base;

public class BaseController<TView extends BaseView, TModel, TViewData> {
    private TView _view;
    private TModel _model;
    private TViewData _viewData;

    public BaseController(TView view, TModel dataModel, TViewData viewModel) {
        setView(view);
        this._model = dataModel;
        this._viewData = viewModel;
    }

    public TView getView() {
        return this._view;
    }

    public void setView(TView view) {
        if (this._view != null) this._view.setController(null);
        this._view = view;
        this._view.setController(this);
        this._view.DefineControls();
    }

    public TModel getModel() {
        return this._model;
    }

    public void setModel(TModel dataModel) {
        this._model = dataModel;
    }

    public TViewData getViewData() {
        return this._viewData;
    }

    public void setViewData(TViewData viewModel) {
        this._viewData = viewModel;
    }
}
