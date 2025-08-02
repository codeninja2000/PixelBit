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
    private final PBModel model; // Model to interact with the image data
    private final PBImageView view; // View to update the image display
    private final Window window; // Window to show the file chooser dialog

    /**
     * Constructor for OpenImageCommand.
     * @param model the model containing image data
     * @param view the view to display the image
     * @param window the window to show the file chooser dialog
     */
    public OpenImageCommand(PBModel model, PBImageView view, Window window) {
        this.model = model;
        this.view = view;
        this.window = window;
    }

    /**
     * Executes the command to open an image file.
     * Displays a file chooser dialog to select an image file and loads it into the model.
     * Updates the view with the loaded image.
     *
     * @throws CommandExecException if there is an error during execution
     */
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

    /**
     * Undoes the command. This command does not support undo functionality.
     *
     * @throws UnsupportedOperationException if called
     */
    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo operation is not supported for OpenImageCommand.");
    }
}