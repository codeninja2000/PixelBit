package com.pixelbit.command;

import com.pixelbit.model.EditableImage;

import java.awt.image.BufferedImage;

/**
 * Abstract base class for PixelBit commands.
 * Provides common functionality for commands, such as saving the current state
 * and undoing changes.
 */
public abstract class AbstractPBCommand implements PBCommand {
    protected EditableImage editableImage;
    protected BufferedImage previousState;

    public AbstractPBCommand(EditableImage editableImage) {
        this.editableImage = editableImage;
    }

    /**
     * Clamps an integer value to the range [0, 255].
     * @param value the integer value to clamp
     * @return clamped value within the range [0, 255]
     */
    protected static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
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

