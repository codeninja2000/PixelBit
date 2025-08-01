package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BrightnessFilter implements Filter {
    private final int adjustment;

    public BrightnessFilter() {
        this(0);
    }

    public BrightnessFilter(int adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage brightImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                
                // Calculate new RGB values while preserving color relationships
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                // Adjust brightness while preserving hue and saturation
                float newBrightness = Math.max(0.0f, Math.min(1.0f, hsb[2] + (adjustment / 255.0f)));
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], newBrightness);
                
                // Preserve original alpha
                int alpha = color.getAlpha();
                brightImage.setRGB(x, y, (alpha << 24) | (rgb & 0x00ffffff));
            }
        }
        return brightImage;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    @Override
    public String getName() {
        return "Brightness Filter";
    }
}