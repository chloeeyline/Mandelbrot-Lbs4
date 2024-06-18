package com.mandelbrot.taskBar;

import com.mandelbrot.base.BaseView;
import com.mandelbrot.taskBar.limitsDialog.LimitsDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class TaskBarView extends BaseView<TaskBarController> {

    private Menu _setLimits;

    @Override
    public void DefineControls() {

        MenuBar taskbar = new MenuBar();
        _setLimits = new Menu("Set Limits");
        Menu mouseCoordinate = new Menu("test");

        taskbar.getMenus().addAll(mouseCoordinate, _setLimits);

        getChildren().add(taskbar);

    }

    @Override
    public void BindActions() {

        _setLimits.setOnAction(e ->{
            //LimitsDialog limitsDialog = new LimitsDialog();
            //limitsDialog.start(stage);
        });

    }

    public Menu get_setLimits() {
        return _setLimits;
    }

    public void set_setLimits(Menu _setLimits) {
        this._setLimits = _setLimits;
    }
}
