package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.PBModel;
import com.pixelbit.view.PBImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * Command to open an image file.
 */
public class OpenImageCommand implements PBCommand {
    private final PBModel model;
    private final PBImageView view;
    private final Window window;

    public OpenImageCommand(PBModel model, PBImageView view, Window window) {
        this.model = model;
        this.view = view;
        this.window = window;
    }

    @Override
    public void execute() throws CommandExecException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            try {
                model.loadImage(selectedFile); // Load the image in the model
                view.updateImage(model.getImage()); // Update the image view
            } catch (Exception e) {
                throw new CommandExecException("Failed to open image: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void undo() {
        // No undo logic for opening a file
    }
}