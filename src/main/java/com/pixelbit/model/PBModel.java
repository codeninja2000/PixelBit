package com.pixelbit.model;

import com.pixelbit.command.ApplyFilterCommand;
import com.pixelbit.command.CommandManager;
import com.pixelbit.command.PBCommand;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.util.ImageUtility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * PBModel is the model class for the PixelBit application.
 * It manages the image being edited, command history, and modification state.
 * It provides methods to load, save, and apply edits to the image.
 */
public class PBModel {
    // CommandManager handles the command history for undo/redo functionality
    private final CommandManager commandManager;
    // EditableImage is a wrapper around BufferedImage that allows for editing operations
    private EditableImage image;
    // isModified is set to true when an edit is applied
    // and reset to false when the image is loaded, replaced, or saved.
    private boolean isModified = false;

    public PBModel() {
        this.image = new EditableImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        FilterFactory filterFactory = new FilterFactory();
        this.commandManager = new CommandManager(filterFactory);
    }

    /**
     * Constructs a PBModel with an image loaded from the specified path.
     *
     * @param path the file path to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public PBModel(String path) throws IOException {
        this();
        load(path);
    }

    /**
     * Constructs a PBModel with an image loaded from the specified file.
     *
     * @param file the file to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public PBModel(File file) throws IOException {
        this();
        load(file);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Loads an image from the specified path.
     *
     * @param path the file path to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public void loadImage(String path) throws IOException {
        load(path);
    }

    /**
     * Loads an image from the specified file.
     *
     * @param file the file to load the image from
     * @throws IOException if the image cannot be loaded
     */
    public void loadImage(File file) throws IOException {
        load(file);
    }

    /**
     * Saves the current image to the specified path.
     *
     * @param path the file path to save the image to
     * @throws IOException if the image cannot be saved
     */
    public void saveImage(String path) throws IOException {
        ImageUtility.saveToFile(image, path);
        resetModifiedFlag();
    }

    /**
     * Saves the current image to the specified file.
     *
     * @param file the file to save the image to
     * @throws IOException if the image cannot be saved
     */
    public void saveImage(File file) throws IOException {
        ImageUtility.saveToFile(image, file);
        resetModifiedFlag();
    }

    /**
     * Returns the current image being edited.
     *
     * @return the EditableImage object representing the current image
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * Applies a command to the image, executing it and marking the model as modified.
     *
     * @param command the command to apply
     */
    public void applyEdit(PBCommand command) {
        commandManager.executeCommand(command);
        updateImageIfNeeded(command);
        isModified = true;
    }

    private void updateImageIfNeeded(PBCommand command) {
        if (command instanceof ApplyFilterCommand imageCommand) {
            this.image = imageCommand.getUpdatedImage();
        }
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
     *
     * @return true if there are commands to undo, false otherwise
     */
    public boolean canUndo() {
        return commandManager.canUndo();
    }

    /**
     * Checks if there are commands that can be redone.
     *
     * @return true if there are commands to redo, false otherwise
     */
    public boolean canRedo() {
        return commandManager.canRedo();
    }

    public FilterFactory getFilterFactory() {
        return this.commandManager.getFilterFactory();
    }

    /**
     * Replaces the current image with a new one loaded from the specified path.
     *
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
     *
     * @param file the file to load the image from
     * @throws IOException if the image cannot be loaded
     */
    private void load(File file) throws IOException {
        image = ImageUtility.loadFromFile(file);
        commandManager.clearHistory();
        resetModifiedFlag();
    }

    /**
     * Resets the modified flag to false, indicating that the image is in an unmodified state.
     */
    public void resetModifiedFlag() {
        isModified = false;
    }

    /**
     * Checks if the image has been modified since the last save.
     *
     * @return true if the image has been modified, false otherwise
     */
    public boolean isModified() {
        return isModified;
    }
}