package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InvertFilter implements Filter {

    public static final int MAX_RGB_VALUE = 255;

    /**
     * Applies an invert filter to the given image.
     *
     * @param image The image to which the filter will be applied.
     * @return A new BufferedImage with the invert filter applied.
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Invert RGB values
                int r = MAX_RGB_VALUE - color.getRed();
                int g = MAX_RGB_VALUE - color.getGreen();
                int b = MAX_RGB_VALUE - color.getBlue();
                int a = color.getAlpha(); // preserve original alpha
                invertedImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
        return invertedImage;
}

@Override
public String getName() {
        return "Invert";
}

@Override
public String toString() {
    return String.format("%sFilter", this.getName());
}
}
