module com.mandelbrot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.mandelbrot to javafx.fxml;
    exports com.mandelbrot;
}