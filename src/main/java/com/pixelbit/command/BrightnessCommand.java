package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.FilterType;
import com.pixelbit.model.filters.BrightnessFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * BrightnessCommand is a command that adjusts the brightness of an image.
 * It extends AbstractPBCommand to provide functionality for applying brightness adjustments.
 * This command modifies the pixel values of the image based on a brightness factor.
 * A factor of 1.0 means no change, less than 1.0 reduces brightness, and greater than 1.0 increases brightness.
 */
public class BrightnessCommand extends AbstractPBCommand {

    private double factor = 1.0;

    /**
     * Creates a new BrightnessCommand to adjust the brightness of an image.
     * @param image the image to be adjusted
     * @param imageService the service to handle image operations
     * @param brightnessFactor the factor by which to adjust the brightness
     */
    public BrightnessCommand(EditableImage image, ImageService imageService, double brightnessFactor) {
        super(image, imageService);
        this.factor = brightnessFactor;

    }

    /**
     * Executes the brightness adjustment command.
     * @throws CommandExecException if an error occurs during execution
     */
    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            if (factor < 0) {
                throw new CommandExecException("Brightness factor must be non-negative.");
            }
            Filter filter = new BrightnessFilter(factor);
            imageService.applyFilter(editableImage.getBufferedImage(), filter);
        } catch (Exception e) {
            throw new CommandExecException("Error applying brightness adjustment: " + e.getMessage(), e);
        }
    }
}