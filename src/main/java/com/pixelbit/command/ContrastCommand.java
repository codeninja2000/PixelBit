package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ContrastCommand is a command that adjusts the contrast of an image.
 * It extends AbstractPBCommand to provide functionality for applying contrast adjustments.
 * This command modifies the pixel values of the image based on a contrast factor.
 * The contrast factor is a multiplier that affects the difference between the pixel values
 * and the average pixel value. A factor of 1.0 means no change, less than 1.0 reduces contrast,
 * and greater than 1.0 increases contrast.
 */
public class ContrastCommand extends AbstractPBCommand {


    private final double factor; // Contrast factor to adjust the image contrast

    public ContrastCommand(EditableImage image, double contrastFactor) {
        super(image);
        this.factor = contrastFactor;
    }

    /**
     * Executes the contrast adjustment command on the editable image.
     * @throws CommandExecException if an error occurs during the execution of the command.
     */
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

    /**
     * Applies the contrast adjustment to the provided BufferedImage.
     * @param image The BufferedImage to adjust.
     * @param factor The contrast factor to apply.
     * @throws CommandExecException if the image is null or the factor is invalid.
     */
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
                int a = color.getAlpha();

                image.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }
    }
}