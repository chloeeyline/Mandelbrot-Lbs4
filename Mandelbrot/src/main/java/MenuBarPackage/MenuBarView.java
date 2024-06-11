package MenuBarPackage;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarView {

    public MenuBarView(){}

    public MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFilePNG = new MenuItem("Save PNG Image");
        MenuItem saveFileJP = new MenuItem("Save JPEG Image");


        fileMenu.getItems().addAll(openFile, saveFileJP, saveFilePNG);

        Menu editMenu = new Menu("Colors");
        MenuItem limits = new MenuItem("");

        editMenu.getItems().addAll(limits);

        Menu helpMenu = new Menu("Help");
        MenuItem helper = new MenuItem("Helper");
        MenuItem impressum = new MenuItem("About us");

        helpMenu.getItems().addAll(helper, impressum);

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        return menuBar;
    }
}
