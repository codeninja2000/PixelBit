package com.codeninja2000.pixelbit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BrightnessCommand extends AbstractImageCommand {
    private final float factor;


    public BrightnessCommand(EditableImage image, float brightnessFactor) {
        super(image);
        this.factor = brightnessFactor;
    }


    @Override
    public void execute() {
        saveCurrentState();
        BufferedImage image = editableImage.getBufferedImage();
        applyBrightness(image, this.factor);
    }

private void applyBrightness(BufferedImage image, float factor) throws ImageCommandException {
    for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
            Color color = new Color(image.getRGB(x, y));
            int r = clamp((int) (color.getRed() * factor));
            int g = clamp((int) (color.getGreen() * factor));
            int b = clamp((int) (color.getBlue() * factor));

            image.setRGB(x, y, new Color(r, g, b).getRGB());
        }
    }
    try {
        ImageIO.write(image, editableImage.getFormat(), new File("src/main/resources/pinkflower_brightness.jpg"));
    } catch (IOException e) {
        throw new ImageCommandException("Error saving image after brightness adjustment: " + e.getMessage());
    }
        }


private static int clamp(int value) {
    return Math.max(0, Math.min(255, value));
}
}