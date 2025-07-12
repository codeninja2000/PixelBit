package com.codeninja2000.pixelbit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditableImage {

    private BufferedImage image;
    private String filename = "default";
    private String format = "jpg";
    final private long createdAt = System.currentTimeMillis();

    public EditableImage() {
        this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

    public EditableImage(BufferedImage image, String filename, String format) {
        this.image = image;
        this.filename = filename;
        this.format = format;
    }

    public EditableImage(BufferedImage image, String filename) {
        this.image = image;
        this.filename = filename;
    }

    public EditableImage(BufferedImage image) {
        this.image = image;
    }

    public EditableImage(String path) throws IOException {
        this(ImageIO.read(new File(path)));
    }


    public EditableImage deepCopy() {
        BufferedImage deepCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        deepCopy.getGraphics().drawImage(image,  0, 0, null);
        return new EditableImage(deepCopy, filename, format);
    }

    public boolean isEmpty() {
        if (image == null) {
            return true;
        }
        return image.getWidth() == 1 || image.getHeight() == 1;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFormat() {
        return this.format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public int getHeight() {
        return this.image.getHeight();
    }

    public int getWidth() {
        return this.image.getWidth();
    }

    public BufferedImage getBufferedImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getRGB(int x, int y) {
        return image.getRGB(x, y);
    }

    public void setRGB(int x, int y, int value) {
        image.setRGB(x, y, value);
    }

    public void applyGrayScale() {
    }

    public void applyInvert() {
    }

    public void applySepia() {
    }

    public void applyCrop() {
    }

    public void applyRotate() {
    }
}

