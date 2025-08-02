package com.pixelbit.command;

import com.pixelbit.model.EditableImage;

/**
 * This interface represents a command to update an image.
 * It extends the PBCommand interface, which is assumed to define common command behavior.
 */
public interface ImageUpdateCommand extends PBCommand {
    /**
     * Gets the updated image after the command is executed.
     *
     * @return the updated image after the command is executed.
     */
    EditableImage getUpdatedImage();
}
