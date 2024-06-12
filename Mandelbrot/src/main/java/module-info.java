module com.mandelbrot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens com.mandelbrot to javafx.fxml;
    exports com.mandelbrot;
    exports com.mandelbrot.display;
    opens com.mandelbrot.display to javafx.fxml;
    exports MenuBar;
    opens MenuBar to javafx.fxml;
}