package com.mandelbrot.menuBar;

import com.mandelbrot.base.BaseController;

public class MenuBarController extends BaseController<MenuBarView, MenuBarModel> {

   private MenuBarModel _model;
   private MenuBarView _view;

    /**
     * Constructor for BaseController.
     *
     * @param model The model assigned to this controller.
     * @param view  The view assigned to this controller.
     */
    public MenuBarController(MenuBarModel model, MenuBarView view) {
        super(model, view);
        _model = model;
        _view = view;
    }

    public void openFile(){

    }

    public void SaveImage(){

    }

    public void Color(){

    }


}
