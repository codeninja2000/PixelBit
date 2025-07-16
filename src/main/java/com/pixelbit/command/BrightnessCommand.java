package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BrightnessCommand extends AbstractPBCommand {
    private final float factor;

    public BrightnessCommand(EditableImage image, float brightnessFactor) {
        super(image);
        this.factor = brightnessFactor;
    }

    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            applyBrightness(editableImage.getBufferedImage(), this.factor);
        } catch (Exception e) {
            throw new CommandExecException("Error applying brightness adjustment: " + e.getMessage(), e);
        }
    }

private void applyBrightness(BufferedImage image, float factor) throws CommandExecException {

    if (image == null) {
        throw new CommandExecException("Null image provided for contrast adjustment.");
    }

        for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
            Color color = new Color(image.getRGB(x, y), true); // true = has alpha
            int r = clamp((int) (color.getRed() * factor));
            int g = clamp((int) (color.getGreen() * factor));
            int b = clamp((int) (color.getBlue() * factor));
            int a = color.getAlpha();
            image.setRGB(x, y, new Color(r, g, b, a).getRGB());
        }
    }
        }

}