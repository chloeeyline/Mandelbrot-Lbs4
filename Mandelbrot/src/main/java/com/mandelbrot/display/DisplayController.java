package com.mandelbrot.display;

import com.mandelbrot.base.BaseController;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

/**
 * DisplayController is responsible for handling user interactions and updating the view and model accordingly.
 */
public class DisplayController extends BaseController<DisplayView, DisplayModel, DisplayViewData> {

    /**
     * Constructor to initialize the controller with the given view, model, and view data.
     *
     * @param view     The view instance.
     * @param model    The model instance.
     * @param viewData The view data instance.
     */
    public DisplayController(DisplayView view, DisplayModel model, DisplayViewData viewData) {
        super(view, model, viewData);
        drawMandelbrotSet(); // Draw the Mandelbrot set initially
    }

    /**
     * Draws the Mandelbrot set on the canvas.
     */
    public void drawMandelbrotSet() {
        // Get the PixelWriter to draw pixels on the image
        PixelWriter pw = getView().getImage().getPixelWriter();

        // Iterate over each pixel in the image
        for (int x = 0; x < getViewData().getImageWidth(); x++) {
            for (int y = 0; y < getViewData().getImageHeight(); y++) {
                // Initialize variables for the Mandelbrot computation
                double zx = 0;
                double zy = 0;

                // Calculate the complex number c corresponding to the pixel (x, y)
                double cX = getModel().getXMin() + (x / getViewData().getImageWidth()) * (getModel().getXMax() - getModel().getXMin());
                double cY = getModel().getYMin() + (y / getViewData().getImageHeight()) * (getModel().getYMax() - getModel().getYMin());

                // Compute the number of iterations for the Mandelbrot set
                int iter = getModel().computeIterations(zx, zy, cX, cY);

                // Get the color corresponding to the number of iterations
                Color color = getModel().getColor(iter);

                // Set the color of the pixel (x, y)
                pw.setColor(x, y, color);
            }
        }

        // Ensure the image is drawn on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> getView().getCanvas().getGraphicsContext2D().drawImage(getView().getImage(), 0, 0));
    }

    /**
     * Handles mouse release events to finalize dragging or zooming.
     *
     * @param evt The mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        if (getViewData().isDragging()) {
            // Remove the drag/zoom rectangle from the display pane
            getView().getDisplayPane().getChildren().remove(getViewData().getDragZoomRect());

            // Perform the appropriate action based on the mouse release coordinates
            handleMouseReleased(evt.getX(), evt.getY());

            // Redraw the Mandelbrot set after dragging or zooming
            drawMandelbrotSet();
        }
    }

    /**
     * Handles mouse dragging events to update the display based on user interaction.
     *
     * @param evt The mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        if (!getViewData().isDragging()) return;

        // Get the current mouse coordinates
        double x = evt.getX();
        double y = evt.getY();

        // Calculate the offset from the start position
        double offsetX = x - getViewData().getStartX();
        double offsetY = y - getViewData().getStartY();

        // Check if the mouse has moved enough to be considered a drag
        if (!getViewData().isMovedMouse() && Math.abs(offsetX) < 3 && Math.abs(offsetY) < 3) return;
        getViewData().setMovedMouse(true);

        // Handle different mouse actions (dragging, zooming in, zooming out)
        if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_DRAG) {
            handleMouseDrag(offsetX, offsetY);
        } else if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_IN ||
                getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_OUT) {
            handleMouseZoom(x, y, offsetX, offsetY);
        }
    }

    /**
     * Handles mouse press events to initiate dragging or zooming.
     *
     * @param evt The mouse event.
     */
    public void mousePressed(MouseEvent evt) {
        if (!getViewData().isDragging()) {
            // Record the starting coordinates of the mouse press
            getViewData().setStartX(evt.getX());
            getViewData().setStartY(evt.getY());

            // Ensure the press is within the image bounds
            if (getViewData().getStartX() > getViewData().getImageWidth() ||
                    getViewData().getStartY() > getViewData().getImageHeight()) return;

            // Determine the action based on the mouse button pressed
            if (evt.isPrimaryButtonDown())
                getViewData().setMouseAction(DisplayViewData.MOUSE_ACTION_DRAG);
            else if (evt.isSecondaryButtonDown())
                getViewData().setMouseAction(DisplayViewData.MOUSE_ACTION_ZOOM_IN);
            else if (evt.isMiddleButtonDown())
                getViewData().setMouseAction(DisplayViewData.MOUSE_ACTION_ZOOM_OUT);
            else return;

            // Set the dragging state
            getViewData().setDragging(true);
            getViewData().setMovedMouse(false);
        }
    }


    /**
     * Saves the current image along with its metadata.
     */
    public void saveImageWithMetadata() {
        // Show file chooser dialog to select the save location
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(getView().getCanvas().getScene().getWindow());
        if (file != null) {
            try {
                // Convert the WritableImage to a BufferedImage
                WritableImage fxImage = getView().getImage();
                BufferedImage bufferedImage = new BufferedImage((int) fxImage.getWidth(), (int) fxImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

                // Copy pixels from WritableImage to BufferedImage
                for (int x = 0; x < fxImage.getWidth(); x++) {
                    for (int y = 0; y < fxImage.getHeight(); y++) {
                        javafx.scene.paint.Color fxColor = fxImage.getPixelReader().getColor(x, y);
                        int argb = (int) (fxColor.getOpacity() * 255) << 24 |
                                (int) (fxColor.getRed() * 255) << 16 |
                                (int) (fxColor.getGreen() * 255) << 8 |
                                (int) (fxColor.getBlue() * 255);
                        bufferedImage.setRGB(x, y, argb);
                    }
                }

                // Prepare metadata for saving the model state
                ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
                ImageWriteParam writeParam = writer.getDefaultWriteParam();
                ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB);
                IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

                // Create metadata nodes
                IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
                IIOMetadataNode text = new IIOMetadataNode("tEXt");
                IIOMetadataNode entry = new IIOMetadataNode("tEXtEntry");

                // Save the model state as metadata
                entry.setAttribute("keyword", "ModelState");
                entry.setAttribute("value", getModel().saveModelState());

                // Add metadata to the image
                text.appendChild(entry);
                root.appendChild(text);
                metadata.mergeTree("javax_imageio_png_1.0", root);

                // Write the image with metadata to the file
                try (ImageOutputStream stream = ImageIO.createImageOutputStream(file)) {
                    writer.setOutput(stream);
                    writer.write(metadata, new javax.imageio.IIOImage(bufferedImage, null, metadata), writeParam);
                } finally {
                    writer.dispose();
                }
            } catch (IOException e) {
                // Show an error message if saving fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler beim speichern");
                alert.setHeaderText("Das Bild konnte aufgrund eines Programmfehlers nicht gespeichert werden, versuchen sie es erneut");
                alert.showAndWait();
            }
        }
    }

    /**
     * Loads an image along with its metadata.
     */
    public void loadImageWithMetadata() {
        // Show file chooser dialog to select the file to load
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showOpenDialog(getView().getCanvas().getScene().getWindow());
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Keine Datei ausgewählt");
            alert.setHeaderText("Es würde kein Bild übertragen, versuchen sie es erneut.");
            alert.showAndWait();
            return;
        }
        try {
            // Read the image from the file
            ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
            try (ImageInputStream stream = ImageIO.createImageInputStream(file)) {
                reader.setInput(stream);

                // Read the metadata from the image
                IIOMetadata metadata = reader.getImageMetadata(0);
                String[] metadataNames = metadata.getMetadataFormatNames();
                IIOMetadataNode dataNode = null;
                for (String name : metadataNames) {
                    if (name.equals("javax_imageio_png_1.0")) {
                        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(name);
                        IIOMetadataNode textNode = (IIOMetadataNode) root.getElementsByTagName("tEXt").item(0);
                        if (textNode != null) {
                            for (int i = 0; i < textNode.getLength(); i++) {
                                IIOMetadataNode node = (IIOMetadataNode) textNode.item(i);
                                if (node.getAttribute("keyword").equals("ModelState")) {
                                    dataNode = node;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (dataNode != null) {
                    // Read the model state from the metadata
                    getModel().readModelState(dataNode.getAttribute("value"));

                    // Update the UI controls based on the loaded model state
                    for (Toggle t : getView().getIterationGroup().getToggles()) {
                        if (((int) t.getUserData()) == getModel().getMaxIteration())
                            t.setSelected(true);
                    }
                    for (Toggle t : getView().getColorPaletteGroup().getToggles()) {
                        if (((int) t.getUserData()) == getModel().getColorPalette())
                            t.setSelected(true);
                    }
                    getView().getBackgroundColorPicker().setValue(getModel().getBackgroundColor());

                    // Redraw the Mandelbrot set with the loaded model state
                    drawMandelbrotSet();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ungültige Datei ausgewählt");
                    alert.setHeaderText("Es würde ein Bild ausgewählt welches nicht von diesem Programm ist, wählen sie ein gültiges Bild.");
                    alert.showAndWait();
                }
            }
        } catch (IOException e) {
            // Show an error message if loading fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler beim laden");
            alert.setHeaderText("Das Bild konnte aufgrund eines Programmfehlers nicht geladen werden, versuchen sie es erneut");
            alert.showAndWait();
        }
    }


    /**
     * Handles mouse release events to finalize dragging or zooming.
     *
     * @param x The x-coordinate where the mouse was released.
     * @param y The y-coordinate where the mouse was released.
     */
    private void handleMouseReleased(double x, double y) {
        if (!getViewData().isDragging()) return;

        // Perform the appropriate action based on the current mouse action
        if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_DRAG &&
                (getViewData().getDragOffsetX() != 0 ||
                        getViewData().getDragOffsetY() != 0)) {
            doDrag(x, y); // Perform drag action
        } else if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_IN &&
                getViewData().getDragZoomRect() != null) {
            doZoomInOnRect(); // Perform zoom-in action
        } else if (getViewData().getMouseAction() == DisplayViewData.MOUSE_ACTION_ZOOM_OUT) {
            doZoomOutFromRect(); // Perform zoom-out action
        }

        // Reset dragging state
        if (getViewData().isDragging()) {
            getViewData().setDragging(false);
            getViewData().setDragZoomRect(null);
            getViewData().setDragOffsetX(0);
            getViewData().setDragOffsetX(1);
        }
    }

    /**
     * Handles the mouse drag action to shift the image.
     *
     * @param offsetX The horizontal offset.
     * @param offsetY The vertical offset.
     */
    private void handleMouseDrag(double offsetX, double offsetY) {
        // Update the drag offsets
        getViewData().setDragOffsetX(offsetX);
        getViewData().setDragOffsetY(offsetY);

        // Clear the canvas
        getView().getCanvas().getGraphicsContext2D().setFill(Color.LIGHTGRAY);
        getView().getCanvas().getGraphicsContext2D().fillRect(0, 0, getViewData().getImageWidth(), getViewData().getImageHeight());

        // Draw the shifted image
        getView().getCanvas().getGraphicsContext2D().drawImage(getView().getImage(), getViewData().getDragOffsetX(), getViewData().getDragOffsetY());
    }

    /**
     * Executes the drag action to update the view limits based on user interaction.
     *
     * @param endX The end X coordinate.
     * @param endY The end Y coordinate.
     */
    private void doDrag(double endX, double endY) {
        // Calculate the shift in x and y coordinates
        double xShift = endX - getViewData().getStartX();
        double yShift = endY - getViewData().getStartY();

        // Calculate the current ranges for x and y coordinates
        double xRange = getModel().getXMax() - getModel().getXMin();
        double yRange = getModel().getYMax() - getModel().getYMin();

        // Calculate the new limits based on the shift
        double newXMin = getModel().getXMin() - (xShift / getViewData().getImageWidth()) * xRange;
        double newXMax = getModel().getXMax() - (xShift / getViewData().getImageWidth()) * xRange;
        double newYMin = getModel().getYMin() - (yShift / getViewData().getImageHeight()) * yRange;
        double newYMax = getModel().getYMax() - (yShift / getViewData().getImageHeight()) * yRange;

        // Update the model limits
        getModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }


    /**
     * Handles the mouse zoom action to update the drag/zoom rectangle.
     *
     * @param x       The current x-coordinate of the mouse.
     * @param y       The current y-coordinate of the mouse.
     * @param offsetX The horizontal offset from the start position.
     * @param offsetY The vertical offset from the start position.
     */
    private void handleMouseZoom(double x, double y, double offsetX, double offsetY) {
        double width = Math.abs(offsetX);
        double height = Math.abs(offsetY);

        if (width < 3 || height < 3) {
            // Remove the drag/zoom rectangle if it is too small
            getView().getDisplayPane().getChildren().remove(getViewData().getDragZoomRect());
            getViewData().setDragZoomRect(null);
        } else {
            adjustZoomRectangle(x, y, width, height);
        }
    }

    /**
     * Adjusts the zoom rectangle based on the current mouse position and aspect ratio.
     *
     * @param x      The current x-coordinate of the mouse.
     * @param y      The current y-coordinate of the mouse.
     * @param width  The width of the zoom rectangle.
     * @param height The height of the zoom rectangle.
     */
    private void adjustZoomRectangle(double x, double y, double width, double height) {
        double aspect = getViewData().getImageWidth() / getViewData().getImageHeight();
        double rectAspect = width / height;

        // Adjust the width and height to maintain the aspect ratio
        if (aspect > rectAspect) width = width * aspect / rectAspect + 0.499;
        else if (aspect < rectAspect) height = height * rectAspect / aspect + 0.499;

        // Determine the position of the zoom rectangle
        double xMin = getViewData().getStartX() < x ? getViewData().getStartX() : getViewData().getStartX() - width;
        double yMin = getViewData().getStartY() < y ? getViewData().getStartY() : getViewData().getStartY() - height;

        // Update the zoom rectangle
        getView().getDisplayPane().getChildren().remove(getViewData().getDragZoomRect());
        getViewData().setDragZoomRect(new Rectangle(xMin, yMin, width, height));
        getViewData().getDragZoomRect().setStroke(Color.BLACK);
        getViewData().getDragZoomRect().setFill(Color.TRANSPARENT);
        getView().getDisplayPane().getChildren().add(getViewData().getDragZoomRect());
    }

    /**
     * Executes the zoom-in action based on the selected rectangle.
     */
    private void doZoomInOnRect() {
        // Calculate the new limits based on the selected zoom rectangle
        double newXMin = getModel().getXMin() + (getViewData().getDragZoomRect().getX() / getViewData().getImageWidth()) * (getModel().getXMax() - getModel().getXMin());
        double newXMax = getModel().getXMin() + ((getViewData().getDragZoomRect().getX() + getViewData().getDragZoomRect().getWidth()) / getViewData().getImageWidth()) * (getModel().getXMax() - getModel().getXMin());
        double newYMin = getModel().getYMin() + (getViewData().getDragZoomRect().getY() / getViewData().getImageHeight()) * (getModel().getYMax() - getModel().getYMin());
        double newYMax = getModel().getYMin() + ((getViewData().getDragZoomRect().getY() + getViewData().getDragZoomRect().getHeight()) / getViewData().getImageHeight()) * (getModel().getYMax() - getModel().getYMin());

        // Update the model limits
        getModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }

    /**
     * Executes the zoom-out action based on the selected rectangle.
     */
    private void doZoomOutFromRect() {
        if (getViewData().getDragZoomRect() == null) return;

        // Get the dimensions of the zoom rectangle
        double rectX = getViewData().getDragZoomRect().getX();
        double rectY = getViewData().getDragZoomRect().getY();
        double rectW = getViewData().getDragZoomRect().getWidth();
        double rectH = getViewData().getDragZoomRect().getHeight();
        double imageWidth = getViewData().getImageWidth();
        double imageHeight = getViewData().getImageHeight();

        // Calculate the new pixel dimensions based on the zoom rectangle
        double newPixelWidth = (getModel().getXMax() - getModel().getXMin()) / rectW;
        double newPixelHeight = (getModel().getYMax() - getModel().getYMin()) / rectH;

        // Calculate the new limits based on the zoom-out action
        double newXMin = getModel().getXMin() - newPixelWidth * rectX;
        double newYMax = getModel().getYMax() + newPixelHeight * rectY;
        double newWidth = newPixelWidth * imageWidth;
        double newHeight = newPixelHeight * imageHeight;
        double newXMax = newXMin + newWidth;
        double newYMin = newYMax - newHeight;

        // Update the model limits
        getModel().setLimits(newXMin, newXMax, newYMin, newYMax);
    }
}
