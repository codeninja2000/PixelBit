package com.pixelbit.command;

import com.pixelbit.model.EditableImage;

public interface ImageUpdateCommand extends PBCommand {
    EditableImage getUpdatedImage();
}
