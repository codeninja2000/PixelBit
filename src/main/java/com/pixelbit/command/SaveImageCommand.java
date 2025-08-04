package com.pixelbit.command;

import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.PBModel;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

import static com.pixelbit.util.ImageUtility.getFileExtension;

/**
 * SaveImageCommand is responsible for saving the current image in the PBModel to a file.
 * It uses a FileChooser to allow the user to select the save location and file type.
 */
public class SaveImageCommand implements PBCommand{
    private final PBModel model;
    private final Window window;

    /**
     * Constructor for SaveImageCommand.
     *
     * @param model The PBModel containing the image to be saved.
     * @param window The window from which the FileChooser will be displayed.
     */
    public SaveImageCommand(PBModel model, Window window) {
        this.model = model;
        this.window = window;
    }

    /**
     * Executes the command to save the image.
     * Displays a FileChooser dialog to select the save location and file type.
     * Saves the image from the model to the selected file.
     *
     * @throws CommandExecException if there is an error during the save operation,
     */
    @Override
    public void execute() throws CommandExecException {
        System.out.println("Starting save operation...");
        
        if (model.getImage() == null || model.getImage().isEmpty()) {
            System.out.println("Image is null or empty");
            throw new CommandExecException("No image available to save.");
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg")
        );

        System.out.println("Showing file chooser...");
        File selectedFile = fileChooser.showSaveDialog(window);

        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                // Update the format based on the selected file's extension
                String extension = getFileExtension(selectedFile.getName()).toLowerCase();
                model.getImage().setFormat(extension);
                
                // Save the image
                model.saveImage(selectedFile);
                System.out.println("Save completed successfully");
            } catch (Exception e) {
                System.err.println("Save failed: " + e.getMessage());
                e.printStackTrace();
                throw new CommandExecException("Failed to save image: " + e.getMessage(), e);
            }
        } else {
            System.out.println("No file selected");
        }
    }

    /**
     * Undoes the command. This command does not support undo functionality.
     *
     * @throws UnsupportedOperationException if called
     */
    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo operation is not supported for SaveImageCommand.");
    }
}