package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filters.GrayscaleFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

// TODO: Add Javadoc comments to the class and methods
// TODO: Add unit tests for the GrayscaleCommand class
// TODO: Wire up the GrayscaleCommand in the command factory or service locator
public class GrayscaleCommand extends AbstractPBCommand {

    private static final double RED_COEFFICIENT = 0.299;
    private static final double GREEN_COEFFICIENT = 0.587;
    private static final double BLUE_COEFFICIENT = 0.114;

    public GrayscaleCommand(EditableImage image, ImageService imageService) {
        super(image, imageService);
    }

    @Override
    public void execute() {
            saveCurrentState();
            Filter filter = new GrayscaleFilter();
            imageService.applyFilter(editableImage.getBufferedImage(), filter);
    }
}
