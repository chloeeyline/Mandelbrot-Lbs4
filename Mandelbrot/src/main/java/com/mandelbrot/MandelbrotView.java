package com.mandelbrot;


import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class MandelbrotView {
    private Canvas canvas;
    private WritableImage image;
    private MandelbrotModel model;

    public MandelbrotView(MandelbrotModel model, int width, int height) {
        this.model = model;
        this.canvas = new Canvas(width, height);
        this.image = new WritableImage(width, height);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void drawMandelbrotSet(double zoom, double offsetX, double offsetY) {
        PixelWriter pixelWriter = image.getPixelWriter();
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - width / 2.0) / zoom + offsetX;
                double cY = (y - height / 2.0) / zoom + offsetY;
                int iter = model.computeIterations(zx, zy, cX, cY);
                pixelWriter.setColor(x, y, model.getColor(iter));
            }
        }

        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        });
    }


}
