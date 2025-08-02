package com.pixelbit.model.filters;

import com.pixelbit.model.filter.Filter;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ContrastFilter implements Filter {
    private final double adjustment;

    public ContrastFilter(double adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage contrastImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Convert adjustment from -1.0 to 1.0 range to a gentler contrast factor
        double factor;
        if (adjustment > 0) {
            // For positive adjustments (0 to 1), map to range 1.0 to 2.0
            factor = 1.0 + adjustment;
        } else {
            // For negative adjustments (-1 to 0), map to range 0.5 to 1.0
            factor = 1.0 + adjustment/2.0;
        }
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                
                // Apply contrast adjustment to each channel
                int r = adjustContrast(color.getRed(), factor);
                int g = adjustContrast(color.getGreen(), factor);
                int b = adjustContrast(color.getBlue(), factor);
                
                Color newColor = new Color(r, g, b, color.getAlpha());
                contrastImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return contrastImage;
    }

    private int adjustContrast(int value, double factor) {
        // Adjust relative to middle gray (128)
        double difference = value - 128;
        double adjusted = 128 + (difference * factor);
        return clamp((int) adjusted);
    }

    private int clamp(int value) {
        return Math.min(255, Math.max(0, value));
    }

    @Override
    public String getName() {
        return "Contrast Filter";
    }
}