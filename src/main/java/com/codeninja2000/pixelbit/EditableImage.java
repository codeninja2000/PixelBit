import ij.process.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;

public class EditableImage {

    private ImageProcessor image;
    private String path;
    private String format;
    private long createdAt;

    public EditableImage(ImageProcessor image, String path, String format) {
        this.image = image;
        this.path = path;
        this.format = format;
        this.createdAt = System.currentTimeMillis();
    }
    public EditableImage(ImageProcessor image, String path) {
        this.image = image;
        this.path = path;
        this.format = "png";
        this.createdAt = System.currentTimeMillis();
    }
    public EditableImage(ImageProcessor image) {
        this.image = image;
        this.path = null;
        this.format = "png"; // Default format
        this.createdAt = System.currentTimeMillis();
    }
    public EditableImage(File file) {
        this.image = null; // Placeholder, actual image loading logic should be implemented
        this.path = file.getAbsolutePath();
        this.format = "png"; // Default format
        this.createdAt = System.currentTimeMillis();
    }
    public EditableImage() {
        this.image = null; // Placeholder, actual image loading logic should be implemented
        this.path = null;
        this.format = "png"; // Default format
        this.createdAt = System.currentTimeMillis();
    }
    public EditableImage deepCopy() {
        EditableImage copy = new EditableImage(this.image.duplicate(), this.path, this.format);
        copy.createdAt = this.createdAt; // Copy the creation time
        return copy;
    }
    public String getFilename() {}
    public void setFilename() {}
    public String getFormat() {}
    public long getCreatedAt() {}
    public int getHeight() {}
    public int getWidth() {}
    public ImageProcessor getProcessor() {}
    public void setProcessor(ImageProcessor processor) {}
    public BufferedImage getDisplayImage() {}
    public int getPixel(int x, int y) {}
    public void setPixel(int x, int y, int value) {}
    public void applyGrayScale() {}
    public void applyInvert() {}
    public void applySepia() {}
    public void applyCrop() {}
    public void applyRotate() {}
    }
}
