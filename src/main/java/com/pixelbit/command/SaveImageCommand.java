package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.PBModel;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class SaveImageCommand implements PBCommand{
    private final PBModel model;
    private final Window window;

    public SaveImageCommand(PBModel model, Window window) {
        this.model = model;
        this.window = window;
    }




    /**
     * @throws CommandExecException
     */
    @Override
    public void execute() throws CommandExecException {
        if (model.getImage() == null || model.getImage().isEmpty()) {
            throw new CommandExecException("No image available to save.");
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showSaveDialog(window);

        if (selectedFile != null) {
            try {
                model.saveImage(selectedFile);
            } catch (Exception e) {
                throw new CommandExecException("Failed to save image: " + e.getMessage(), e);
            }
        }

    }

    /**
     *
     */
    @Override
    public void undo() {

    }
}
