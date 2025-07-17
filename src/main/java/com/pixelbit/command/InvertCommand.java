package com.pixelbit.command;

import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.InvertFilter;

public class InvertCommand extends AbstractPBCommand {

    /**
     * Creates a new InvertCommand to apply an invert filter to the image.
     *
     * @param image        the image to be inverted
     * @param imageService the service to handle image operations
     */
    public InvertCommand(EditableImage image, ImageService imageService) {
        super(image, imageService);
    }

    /**
     * Executes the invert command by applying the invert filter to the image.
     */
    @Override
    public void execute() {
        saveCurrentState();
        Filter filter = new InvertFilter();
        imageService.applyFilter(editableImage.getBufferedImage(), filter);
    }
}
