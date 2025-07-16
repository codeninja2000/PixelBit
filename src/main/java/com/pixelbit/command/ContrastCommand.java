package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ContrastCommand extends AbstractPBCommand {

    private final double factor;

    public ContrastCommand(EditableImage image, double contrastFactor) {
        super(image);
        this.factor = contrastFactor;
    }

    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            applyContrast(editableImage.getBufferedImage(), factor);
        } catch (CommandExecException e) {
            throw new CommandExecException("Error applying contrast adjustment: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CommandExecException("Error applying contrast adjustment: " + e.getMessage(), e);
        }
    }

    private void applyContrast(BufferedImage image, double factor) throws CommandExecException {
        if (image == null) {
            throw new CommandExecException("Null image provided for contrast adjustment.");
        }
        if (factor < 0) {
            throw new CommandExecException("Contrast factor must be non-negative.");
        }

        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true); // true = has alpha
                int r = clamp((int) ((color.getRed() - 128) * factor + 128));
                int g = clamp((int) ((color.getGreen() - 128) * factor + 128));
                int b = clamp((int) ((color.getBlue() - 128) * factor + 128));
                int a = color.getAlpha(); // preserve original alpha

                image.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
    }
}