package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

// TODO: Add Javadoc comments to the class and methods
// TODO: Add unit tests for the GrayscaleCommand class
// TODO: Wire up the GrayscaleCommand in the command factory or service locator
public class GrayscaleCommand extends AbstractPBCommand {

    private static final double RED_COEFFICIENT = 0.299;
    private static final double GREEN_COEFFICIENT = 0.587;
    private static final double BLUE_COEFFICIENT = 0.114;

    public GrayscaleCommand(EditableImage image) {
        super(image);
    }

    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            applyGrayscale(editableImage.getBufferedImage());
        } catch (CommandExecException e) {
            throw new CommandExecException("Error applying grayscale: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CommandExecException("Error applying grayscale: " + e.getMessage(), e);
        }
    }

    private void applyGrayscale(BufferedImage targetImage) throws CommandExecException {
        if (targetImage == null) {
            throw new CommandExecException("Null image provided for grayscale conversion.");
        }

        int width = targetImage.getWidth();
        int height = targetImage.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = targetImage.getRGB(x, y);
                Color color = new Color(pixel, true); // true = has alpha
                // Convert to grayscale using the luminosity method
                int gray = (int)(RED_COEFFICIENT * color.getRed() +
                                 GREEN_COEFFICIENT * color.getGreen() +
                                 BLUE_COEFFICIENT * color.getBlue());
                int a = color.getAlpha(); // preserve original alpha
                targetImage.setRGB(x, y, new Color(gray, gray, gray, a).getRGB());
            }
        }
    }
}
