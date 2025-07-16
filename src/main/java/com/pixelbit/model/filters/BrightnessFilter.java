package com.pixelbit.model.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BrightnessFilter {
    private double factor;

    public BrightnessFilter(double factor) {
        this.factor = factor;
    }

    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage brightImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(brightImage.getRGB(x, y), true); // true = has alpha
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
