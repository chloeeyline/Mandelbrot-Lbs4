package com.mandelbrot.base;

public class BaseController<TView extends BaseView, TModel extends BaseModel> {
    protected TView _view;
    protected TModel _model;

    public BaseController(TModel model, TView view) {
        this._model = model;
        this._view = view;

        this._view.setController(this);
        this._view.DefineControls();
        this._view.BindActions();
    }

    public TModel getModel() {
        return _model;
    }

    public void setModel(TModel model) {
        this._model = model;
    }

    public TView getView() {
        return _view;
    }

    public void setView(TView view) {
        this._view = view;
    }
}
