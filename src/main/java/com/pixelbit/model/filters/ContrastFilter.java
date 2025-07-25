package com.pixelbit.model.filters;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ContrastFilter implements Filter {

    private final double factor;

    public ContrastFilter(double factor) {
        this.factor = factor;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage contrastImage = new BufferedImage(width, image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                int r = clamp((int) ((color.getRed() - 128) * factor + 128));
                int g = clamp((int) ((color.getGreen() - 128) * factor + 128));
                int b = clamp((int) ((color.getBlue() - 128) * factor + 128));
                int a = color.getAlpha(); // preserve original alpha

                contrastImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
        return contrastImage;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    @Override
    public String getName() {
        return "Contrast";
    }

    @Override
    public String toString() {
        return String.format("%sFilter[factor=%s]", this.getName(), this.factor);
    }
}
