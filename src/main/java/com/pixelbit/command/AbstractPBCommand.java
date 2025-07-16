package com.pixelbit.command;

import com.pixelbit.model.EditableImage;

import java.awt.image.BufferedImage;

public abstract class AbstractPBCommand implements PBCommand {
    protected EditableImage editableImage;
    protected BufferedImage previousState;

    public AbstractPBCommand(EditableImage editableImage) {
        this.editableImage = editableImage;
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

