package com.pixelbit;

import com.pixelbit.command.*;
import com.pixelbit.model.PBModel;
import com.pixelbit.model.filter.FilterType;
import com.pixelbit.view.PBImageView;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
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
        updateUndoRedoButtons(); // Ensure buttons are updated initially

        System.out.println("Exit item: " + view.getExitItem());

        // Set up event handlers for the view
        view.getOpenItem().setOnAction(_ -> model.getCommandManager().executeCommand(new OpenImageCommand(model, view, view.getScene().getWindow())));
        view.getSaveMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(new SaveImageCommand(model, view.getScene().getWindow())));
        view.getExitItem().setOnAction(_ -> model.getCommandManager().executeCommand(new ExitCommand()));
        view.getUndoMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(new UndoCommand(model)));
        view.getRedoMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(new RedoCommand(model)));
        view.getGrayscaleButton().setOnAction(_ -> {
            try {
                if (model.getImage() == null) {
                    view.showError("No image loaded");
                    return;
                }

                // Apply the Grayscale filter using the appropriate command
                model.applyEdit(new ApplyFilterCommand(
                        model.getImageService(),
                        model.getFilterFactory(),
                        model.getImage(),
                        FilterType.GRAYSCALE,
                        null // No additional parameters needed for Grayscale
                ));

                // Update the view with the new image and refresh undo/redo button states
                view.updateImage(model.getImage());
                updateUndoRedoButtons();

            } catch (Exception e) {
                view.showError("Failed to apply Grayscale filter: " + e.getMessage());
            }
        });
    }

    public void onApplyFilter(FilterType filterType, Object... params) {
        try {
            if (model.getImage() == null) {
                view.showError("No image loaded");
                return;
            }

            // Map filter parameters
            Map<String, Object> parameters = new HashMap<>();
            switch (filterType) {
                case BRIGHTNESS -> {
                    if (params.length > 0) {
                        parameters.put("brightness", params[0]);
                    }
                }
                case CONTRAST -> {
                    if (params.length > 0) {
                        parameters.put("contrast", params[0]);
                    }
                }
                case SEPIA, GRAYSCALE -> {
                    // No additional parameters needed
                }
            }

            // Delegate command execution to the model
            model.applyEdit(new ApplyFilterCommand(
                    model.getImageService(),
                    model.getFilterFactory(),
                    model.getImage(),
                    filterType,
                    parameters
            ));

            view.updateImage(model.getImage()); // Update view with new image
            updateUndoRedoButtons();
        } catch (Exception e) {
            view.showError("Failed to apply filter: " + e.getMessage());
        }
    }

    private void handleExit() {
        Platform.exit(); // Terminates the JavaFX application
    }

    public void handleOpenImage(Window parentWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

       File selectedFile = fileChooser.showOpenDialog(parentWindow);

       if (selectedFile != null) {
           try {
               model.loadImage(selectedFile);
                view.updateImage(model.getImage());
           } catch (Exception e) {
               view.showError("Failed to open image: " + e.getMessage());
           }
       }
    }

    public void handleUndo() {
        model.undo();
        view.updateImage(model.getImage());
        updateUndoRedoButtons();
    }

    public void handleRedo() {
        model.redo();
        view.updateImage(model.getImage());
        updateUndoRedoButtons();
    }

    private void updateUndoRedoButtons() {
        view.setUndoEnabled(model.canUndo());
        view.setRedoEnabled(model.canRedo());
    }
}