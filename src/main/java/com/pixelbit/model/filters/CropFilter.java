package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;

import java.awt.image.BufferedImage;

/**
 * CropFilter is a filter that crops an image to a specified rectangle.
 * It allows you to define the top-left corner and the dimensions of the crop rectangle.
 * This filter is useful for extracting a specific portion of an image.
 */
public class CropFilter implements Filter {
    private int x; // X coordinate of the top-left corner of the crop rectangle
    private int y; // Y coordinate of the top-left corner of the crop rectangle
    private int width; // Width of the crop rectangle
    private int height; // Height of the crop rectangle

    /**
     * Constructs a new CropFilter.
     * This filter is used to crop an image to a specified rectangle.
     *
     * @param x      X coordinate of the top-left corner of the crop rectangle
     * @param y      Y coordinate of the top-left corner of the crop rectangle
     * @param width  Width of the crop rectangle
     * @param height Height of the crop rectangle
     */
    public CropFilter(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new CropFilter with the top-left corner at (0, 0).
     * This constructor is useful for cropping the entire image.
     *
     * @param width  Width of the crop rectangle
     * @param height Height of the crop rectangle
     */
    public CropFilter(int width, int height) {
        this(0, 0, width, height);
    }

    /**
     * Gets the X coordinate of the top-left corner of the crop rectangle.
     *
     * @return The X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate of the top-left corner of the crop rectangle.
     *
     * @return The Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the width of the crop rectangle.
     *
     * @return The width of the crop rectangle
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the crop rectangle.
     *
     * @return The height of the crop rectangle
     */
    public int getHeight() {
        return height;
    }

    /**
     * Applies the crop filter to the given image.
     * This method crops the image to the specified rectangle
     * defined by the x, y, width, and height class fields.
     *
     * @param image The image to which the filter will be applied.
     * @return A new BufferedImage that is cropped to the specified rectangle.
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        isValidDimension(image);

        BufferedImage subimage = image.getSubimage(x, y, width, height);
        BufferedImage copy = new BufferedImage(width, height, image.getType());
        copy.getGraphics().drawImage(subimage, 0, 0, null);
        return copy;

    }

    /**
     * Checks if the crop dimensions are valid for the given image.
     *
     * @param image The image to check the dimensions against.
     * @return true if the dimensions are valid, false otherwise.
     */
    private boolean isValidDimension(BufferedImage image) {
        return !(x < 0 || y < 0 || width <= 0 || height <= 0 ||
                x + width > image.getWidth() || y + height > image.getHeight());
    }


    /**
     * Returns the name of the filter.
     *
     * @return A string representing the name of the filter.
     */
    @Override
    public String getName() {
        return "Crop";
    }

    @Override

    /**
     * Returns a string representation of the CropFilter.
     * @return A string containing the filter's name and its crop parameters.
     */
    public String toString() {
        return String.format("%sFilter[x=%d, y=%d, width=%d, height=%d]",
                this.getName(), x, y, width, height);
    }

}
