package com.pixelbit.model.filters;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayscaleFilter implements Filter {
    /**
     * Coefficients for converting RGB to grayscale using the luminosity method.
     * These coefficients are based on the perceived brightness for humans of each color channel.
     */
    private static final double RED_COEFFICIENT = 0.299;
    private static final double GREEN_COEFFICIENT = 0.587;
    private static final double BLUE_COEFFICIENT = 0.114;

    @Override
    public BufferedImage apply(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = grayImage.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Convert to grayscale using the luminosity method
                int gray = (int)(RED_COEFFICIENT * color.getRed() +
                        GREEN_COEFFICIENT * color.getGreen() +
                        BLUE_COEFFICIENT * color.getBlue());
                int a = color.getAlpha(); // preserve original alpha
                grayImage.setRGB(x, y, new Color(gray, gray, gray, a).getRGB());
            }
        }
        return grayImage;
    }

    @Override
    public String getName() {
        return "Grayscale Filter";
    }
}
