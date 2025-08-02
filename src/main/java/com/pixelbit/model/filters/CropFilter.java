package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;
import java.awt.image.BufferedImage;

/**
 * CropFilter is a filter that crops a specified rectangular area from an image.
 * It takes the x and y coordinates of the top-left corner, as well as the width
 * and height of the crop area.
 */
public class CropFilter implements Filter {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    /**
     * Constructs a CropFilter with the specified crop parameters.
     *
     * @param x      The x coordinate for the top-left corner of the crop area.
     * @param y      The y coordinate for the top-left corner of the crop area.
     * @param width  The width of the crop area.
     * @param height The height of the crop area.
     */
    public CropFilter(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Applies the crop filter to the given image.
     *
     * @param image The image to be cropped.
     * @return A new BufferedImage that is the cropped version of the input image.
     * @throws IllegalArgumentException if the crop parameters are invalid.
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        // Validate crop parameters
        if (!isValidCrop(image)) {
            throw new IllegalArgumentException(
                String.format("Invalid crop parameters: x=%d, y=%d, width=%d, height=%d for image size %dx%d",
                    x, y, width, height, image.getWidth(), image.getHeight())
            );
        }

        // Create a new image with the cropped dimensions
        BufferedImage croppedImage = new BufferedImage(
            width, 
            height, 
            image.getType()
        );

        // Copy the cropped region to the new image
        croppedImage.getGraphics().drawImage(
            image,
            0, 0, width, height,  // Destination coordinates
            x, y, x + width, y + height,  // Source coordinates
            null
        );

        return croppedImage;
    }

    /**
     * Validates the crop parameters against the dimensions of the image.
     *
     * @param image The image to validate against.
     * @return true if the crop parameters are valid, false otherwise.
     */
    private boolean isValidCrop(BufferedImage image) {
        return x >= 0 && 
               y >= 0 && 
               width > 0 && 
               height > 0 && 
               x + width <= image.getWidth() && 
               y + height <= image.getHeight();
    }

    /**
     * Returns the name of the filter.
     *
     * @return The name of the filter.
     */
    @Override
    public String getName() {
        return "Crop";
    }
}