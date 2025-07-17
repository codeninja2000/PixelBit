package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filters.SepiaFilter;

public class SepiaCommand extends AbstractPBCommand {

    SepiaCommand(EditableImage image, ImageService imageService) {
        super(image, imageService);
    }

    /**
     * Executes the Sepia command.
     * @throws CommandExecException if an error occurs during command execution.
     */
    @Override
    public void execute() throws CommandExecException {
        try {
            saveCurrentState();
            Filter filter = new SepiaFilter();
            imageService.applyFilter(editableImage.getBufferedImage(), filter);
        } catch (Exception e) {
            throw new CommandExecException("Failed to apply Sepia filter", e);
        }
    }
}
