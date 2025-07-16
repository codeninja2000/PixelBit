package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * BrightnessFilter is a filter that adjusts the brightness of an image.
 * The factor determines how much to increase or decrease the brightness.
 * A factor greater than 1.0 increases brightness, while a factor less than 1.0 decreases it.
 */
public class BrightnessFilter implements Filter {
    private double factor = 1.0; // Default factor is 1.0 (no change)

    public BrightnessFilter() {
        // Default constructor with no factor specified
    }
    public BrightnessFilter(double factor) {
        this.factor = factor;
    }

    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage brightImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(brightImage.getRGB(x, y), true);
                int r = clamp((int) (color.getRed() * factor));
                int g = clamp((int) (color.getGreen() * factor));
                int b = clamp((int) (color.getBlue() * factor));
                int a = color.getAlpha();
                brightImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
        return brightImage;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public double getFactor() {
        return factor;
    }

public String getName() {
    return "Brightness Filter";
}
}
