package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;
import java.awt.image.BufferedImage;

public class CropFilter implements Filter {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public CropFilter(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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

    private boolean isValidCrop(BufferedImage image) {
        return x >= 0 && 
               y >= 0 && 
               width > 0 && 
               height > 0 && 
               x + width <= image.getWidth() && 
               y + height <= image.getHeight();
    }

    @Override
    public String getName() {
        return "Crop";
    }
}