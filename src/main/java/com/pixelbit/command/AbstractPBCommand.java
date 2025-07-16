package com.pixelbit.command;

import com.pixelbit.model.EditableImage;

import java.awt.image.BufferedImage;

public abstract class AbstractPBCommand implements PBCommand {
    protected EditableImage editableImage;
    protected BufferedImage previousState;

    public AbstractPBCommand(EditableImage editableImage) {
        this.editableImage = editableImage;
    }

    protected static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    @Override
    public void undo() {
        if (previousState != null) {
            editableImage.setImage(previousState);
        }
    }
    protected void saveCurrentState() {
        if (!editableImage.isEmpty()) {
            previousState = editableImage.deepCopy().getBufferedImage();
        }
    }

}

