package com.pixelbit;

import com.pixelbit.command.ApplyFilterCommand;
import com.pixelbit.command.CommandManager;
import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
import com.pixelbit.model.ImageService;
import com.pixelbit.model.PBModel;
import com.pixelbit.model.filter.Filter;
import com.pixelbit.model.filter.FilterFactory;
import com.pixelbit.model.filter.FilterType;
import com.pixelbit.util.ImageUtility;
import com.pixelbit.view.PBImageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * PBController is the controller class for the JavaFX application.
 * It handles user interactions and updates the UI accordingly.
 * It also acts as a bridge between the view and the model.
 */
public class PBController {
    private final PBModel model;
    private final PBImageView view;
    private final ImageService imageService;
    private final FilterFactory filterFactory;

    public PBController(PBModel model, PBImageView view, ImageService imageService, FilterFactory filterFactory) {
        this.model = model;
        this.view = view;
        this.imageService = imageService;
        this.filterFactory = filterFactory;
    }

    public void onApplyFilter(FilterType filterType, Object... params) {
        try {
            Filter filter = filterFactory.createFilter(filterType, params);
            ApplyFilterCommand command = new ApplyFilterCommand(imageService, model.getImage(), filter);
            model.applyEdit(command);
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
        } catch (Exception e) {
            view.showError("Failed to apply filter: " + e.getMessage());
        }
    }

    public void onUndo() {
        if (model.canUndo()) {
            model.undo();
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
        }
    }

    public void onRedo() {
        if (model.canRedo()) {
            model.redo();
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
        }
    }

    public void onLoadImage(String filepath) {
        try {
            model.loadImage(filepath);
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
        } catch (IOException e) {
            view.showError("Failed to load image: " + e.getMessage());
        }
    }

    public void onSaveImage(String filepath) {
        try {
            model.saveImage(filepath);
        } catch (IOException e) {
            view.showError("Failed to save image: " + e.getMessage());
        }
    }

    private void updateUndoRedoButtons() {
        view.setUndoEnabled(model.canUndo());
        view.setRedoEnabled(model.canRedo());
    }
}