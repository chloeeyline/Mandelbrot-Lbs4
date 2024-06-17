package com.mandelbrot.colorPicker;

import com.mandelbrot.base.BaseController;

public class ColorPickerController extends BaseController<ColorPickerView, ColorPickerModel> {
    /**
     * Constructor for BaseController.
     *
     * @param model The model assigned to this controller.
     * @param view  The view assigned to this controller.
     */
    public ColorPickerController(ColorPickerModel model, ColorPickerView view) {
        super(model, view);
    }
}
