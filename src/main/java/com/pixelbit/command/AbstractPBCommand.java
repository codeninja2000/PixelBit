package com.pixelbit.command;

import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;

import java.awt.image.BufferedImage;

/**
 * Abstract base class for PixelBit commands.
 * Provides common functionality for commands, such as saving the current state
 * and undoing changes.
 */
public abstract class AbstractPBCommand implements PBCommand {
    protected final EditableImage editableImage;
    protected BufferedImage previousState;
    protected final ImageService imageService;

    protected AbstractPBCommand(EditableImage editableImage, ImageService imageService) {
        this.editableImage = editableImage;
        this.imageService = imageService;
    }


    /**
     * Restores image to the previous state.
     */
    @Override
    public void undo() {
        if (previousState != null) {
            editableImage.setImage(previousState);
        }
    }

    /**
     * Saves the current state of the image before executing the command.
     * This allows the command to restore the image to this state if needed.
     */
    protected void saveCurrentState() {
        if (!editableImage.isEmpty()) {
            previousState = editableImage.deepCopy().getBufferedImage();
        }
    }

}

