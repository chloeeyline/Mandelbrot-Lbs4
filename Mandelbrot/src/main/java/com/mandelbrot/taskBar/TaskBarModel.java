package com.mandelbrot.taskBar;

import com.mandelbrot.base.BaseModel;

public class TaskBarModel extends BaseModel<TaskBarDataModel, TaskBarViewDataModel> {

    public TaskBarModel(){
        setViewDataModel(new TaskBarViewDataModel());
        setDataModel(new TaskBarDataModel());
    }
}
