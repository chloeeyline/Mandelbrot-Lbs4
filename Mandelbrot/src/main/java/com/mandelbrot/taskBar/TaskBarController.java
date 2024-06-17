package com.mandelbrot.taskBar;

import com.mandelbrot.base.BaseController;

public class TaskBarController extends BaseController<TaskBarView, TaskBarModel> {
    /**
     * Constructor for BaseController.
     *
     * @param model The model assigned to this controller.
     * @param view  The view assigned to this controller.
     */
    public TaskBarController(TaskBarModel model, TaskBarView view) {
        super(model, view);
    }
}
