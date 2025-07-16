// src/main/java/com/pixelbit/model/PBModel.java
package com.pixelbit.model;
import com.pixelbit.util.ImageUtility;
import com.pixelbit.command.CommandManager;

import com.pixelbit.command.PBCommand;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * PBModel is the model class for the PixelBit application.
 * It manages the image being edited, command history, and modification state.
 * It provides methods to load, save, and apply edits to the image.
 */
public class PBModel {
    // EditableImage is a wrapper around BufferedImage that allows for editing operations
    private EditableImage image ;
    // CommandManager handles the command history for undo/redo functionality
    private final CommandManager commandManager = new CommandManager();
    // isModified is set to true when an edit is applied
    // and reset to false when the image is loaded, replaced, or saved.
    private boolean isModified = false;

    public PBModel() {
        this.image = new EditableImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }

    /**
     * Constructs a PBModel with an image loaded from the specified path.
     * @param path the file path to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public PBModel(String path) throws IOException {
        load(path);
    }
/**
     * Constructs a PBModel with an image loaded from the specified file.
     * @param file the file to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public PBModel(File file) throws IOException {
        load(file);
    }

    /**
     * Loads an image from the specified path.
     * @param path the file path to load the image from
     * @throws IOException
     */
    public void loadImage(String path) throws IOException {
        load(path);
    }

    /**
     * Loads an image from the specified file.
     * @param file the file to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public void loadImage(File file) throws IOException {
        load(file);
    }

    /**
     * Saves the current image to the specified path.
     * @param path the file path to save the image to
     * @throws IOException if the image cannot be saved
     */
    public void saveImage(String path) throws IOException {
        ImageUtility.saveToFile(image, path);
        resetModifiedFlag();
    }

    /**
     * Saves the current image to the specified file.
     * @param file the file to save the image to
     * @throws IOException if the image cannot be saved
     */
    public void saveImage(File file) throws IOException {
        ImageUtility.saveToFile(image, file);
        resetModifiedFlag();
    }

    /**
     * Returns the current image being edited.
     * @return the EditableImage object representing the current image
     */
    public EditableImage getImage() { return image; }

    

    /**
     * Replaces the current image with a new one loaded from the specified path.
     * @param path the file path to load the new image from
     * @throws IOException if the new image cannot be loaded
     */
    private void load(String path) throws IOException {
        image = ImageUtility.loadFromFile(path);
        commandManager.clearHistory();
        resetModifiedFlag();
    }

    /**
     * Loads an image from the specified file.
     * @param file the file to load the image from
     * @throws IOException if the image cannot be loaded
     */
    private void load(File file) throws IOException {
        image = ImageUtility.loadFromFile(file);
        commandManager.clearHistory();
        resetModifiedFlag();
    }

    /**
     * Checks if the image has been modified since the last save.
     * @return true if the image has been modified, false otherwise
     */
    public boolean isModified() { return isModified; }

    /**
     * Resets the modified flag to false, indicating that the image is in an unmodified state.
     */
    public void resetModifiedFlag() {
        isModified = false;
}

    /**
     * Applies a command to the image, executing it and marking the model as modified.
     * @param command the command to apply
     */
    public void applyEdit(PBCommand command) {
        commandManager.executeCommand(command);
        isModified = true;
    }

    /**
     * Undoes the last command applied to the image.
     * If there are no commands to undo, this method does nothing.
     */
    public void undo() {
        commandManager.undo();
    }

    /**
     * Redoes the last undone command.
     * If there are no commands to redo, this method does nothing.
     */
    public void redo() {
        commandManager.redo();
    }

    /**
     * Checks if there are commands that can be undone.
     * @return true if there are commands to undo, false otherwise
     */
    public boolean canUndo() {
        return commandManager.canUndo();
    }

    /**
     * Checks if there are commands that can be redone.
     * @return true if there are commands to redo, false otherwise
     */
    public boolean canRedo() {
        return commandManager.canRedo();
    }
}