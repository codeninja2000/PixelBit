package com.codeninja2000.pixelbit;

import java.awt.image.BufferedImage;

public abstract class AbstractImageCommand  implements ImageCommand {
    protected EditableImage editableImage;
    protected BufferedImage previousState;

    public AbstractImageCommand(EditableImage editableImage) {
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

