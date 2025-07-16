package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filters.ContrastFilter;

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


    private double factor = 1.0; // Contrast factor to adjust the image contrast

    /**
     * Creates a new ContrastCommand to adjust the contrast of an image.
     * @param image the image to be adjusted
     * @param imageService the service to handle image operations
     * @param contrastFactor the factor by which to adjust the contrast
     */
    public ContrastCommand(EditableImage image, ImageService imageService, double contrastFactor) {
        super(image, imageService);
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
            if (factor < 0) {
                throw new CommandExecException("Contrast factor must be non-negative.");
            }
            ContrastFilter filter = new ContrastFilter(factor);
            BufferedImage image = imageService.applyFilter(editableImage.getBufferedImage(), filter);
        } catch (Exception e) {
            throw new CommandExecException("Error applying contrast adjustment: " + e.getMessage(), e);
        }
    }
}