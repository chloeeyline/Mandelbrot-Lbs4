package com.mandelbrot.colorPicker;

import com.mandelbrot.base.BaseModel;

public class ColorPickerModel extends BaseModel<ColorPickerDataModel, ColorPickerViewDataModel> {

    public ColorPickerModel(){
        setDataModel(new ColorPickerDataModel());
        setViewDataModel(new ColorPickerViewDataModel());
    }
}
