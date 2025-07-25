package com.pixelbit;

import com.pixelbit.command.*;
import com.pixelbit.model.PBModel;
import com.pixelbit.model.filter.FilterType;
import com.pixelbit.view.PBImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * PBController is the controller class for the JavaFX application.
 * It handles user interactions and updates the UI accordingly.
 * It also acts as a bridge between the view and the model.
 */
public class PBController {
    private final PBModel model;
    private final PBImageView view;

    public PBController(PBModel model, PBImageView view) {
        this.model = model;
        this.view = view;

        view.updateImage(null);
        updateUndoRedoButtons();

        // Set up event handlers for the view
        view.getOpenItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new OpenImageCommand(model, view, view.getScene().getWindow())));
        
        view.getSaveMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new SaveImageCommand(model, view.getScene().getWindow())));
        
        view.getExitItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new ExitCommand()));
        
        view.getUndoMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new UndoCommand(model)));
        
        view.getRedoMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new RedoCommand(model)));
        
        view.getGrayscaleButton().setOnAction(_ -> applyFilter(FilterType.GRAYSCALE));
    }

    private void applyFilter(FilterType filterType) {
        try {
            if (model.getImage() == null) {
                view.showError("No image loaded");
                return;
            }

            Map<String, Object> parameters = new HashMap<>();
            switch (filterType) {
                case BRIGHTNESS -> parameters.put("brightness", 1.2);
                case CONTRAST -> parameters.put("contrast", 1.1);
                case SEPIA, GRAYSCALE -> {} // No parameters needed
            }

            ApplyFilterCommand command = new ApplyFilterCommand(
                    model.getImage(),
                    model.getFilterFactory(),
                    filterType,
                    parameters
            );
            
            model.getCommandManager().executeCommand(command);
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
            
        } catch (Exception e) {
            view.showError("Failed to apply filter: " + e.getMessage());
        }
    }

    private void updateUndoRedoButtons() {
        view.setUndoEnabled(model.canUndo());
        view.setRedoEnabled(model.canRedo());
    }
}