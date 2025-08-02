package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BrightnessFilter implements Filter {
    private final int adjustment;

    public BrightnessFilter(int adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage brightImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        float adjustmentFactor = adjustment / 255.0f;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                
                if (adjustmentFactor > 0) {
                    // When brightening, scale up towards 255
                    r += (255 - r) * adjustmentFactor;
                    g += (255 - g) * adjustmentFactor;
                    b += (255 - b) * adjustmentFactor;
                } else {
                    // When darkening, scale down towards 0
                    r += r * adjustmentFactor;
                    g += g * adjustmentFactor;
                    b += b * adjustmentFactor;
                }
                
                // Create new color with clamped RGB values and original alpha
                Color newColor = new Color(
                    clamp((int)r),
                    clamp((int)g),
                    clamp((int)b),
                    color.getAlpha()
                );
                brightImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return brightImage;
    }

    private int clamp(int value) {
        return Math.min(255, Math.max(0, value));
    }

    @Override
    public String getName() {
        return "Brightness Filter";
    }
}