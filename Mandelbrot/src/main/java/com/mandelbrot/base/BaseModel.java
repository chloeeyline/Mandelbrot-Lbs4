package com.mandelbrot.base;

public class BaseModel<TData, TViewData> {
    private TData _dataModel;
    private TViewData _viewDataModel;

    public TData getDataModel() {
        return _dataModel;
    }

    public void setDataModel(TData dataModel) {
        this._dataModel = dataModel;
    }

    public TViewData getViewDataModel() {
        return _viewDataModel;
    }

    public void setViewDataModel(TViewData viewDataModel) {
        this._viewDataModel = viewDataModel;
    }
}

