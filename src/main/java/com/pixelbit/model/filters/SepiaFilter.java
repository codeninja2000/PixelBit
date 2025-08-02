package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * SepiaFilter applies a sepia tone effect to an image.
 * The sepia effect is achieved by applying a specific transformation to the RGB values of each pixel.
 */
public class SepiaFilter implements Filter {

    /**
     * Default constructor for SepiaFilter.
     */
    public SepiaFilter() {}

    /**
     * @param image The image to which the filter will be applied.
     * @return
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Apply sepia transformation
                int r = clamp((int)(color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189));
                int g = clamp((int)(color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168));
                int b = clamp((int)(color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131));
                int a = color.getAlpha(); // preserve original alpha
                sepiaImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
                return sepiaImage;
    }
/**
     * Applies the sepia filter to the given image and returns a new image.
     * This method is an alternative to the apply method, providing a functional interface.
     *
     * @param image The image to which the filter will be applied.
     * @return A new BufferedImage with the sepia effect applied.
     */
    public static BufferedImage applyf(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Apply sepia transformation
                int r = clamp((int)(color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189));
                int g = clamp((int)(color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168));
                int b = clamp((int)(color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131));
                int a = color.getAlpha(); // preserve original alpha
                sepiaImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
                return sepiaImage;
    }

    /**
     * Clamps the RGB values to ensure they are within the valid range of 0-255.
     *
     * @param value The RGB value to clamp.
     * @return The clamped RGB value.
     */
    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    /**
     * Returns the name of the filter.
     *
     * @return The name of the filter.
     */
    @Override
    public String getName() {
        return "Sepia";
    }

    /**
     * Returns the file name of the filter.
     *
     * @return The file name of the filter.
     */
    @Override
    public String toString() {
        return String.format("%sFilter", this.getName());
    }


}
