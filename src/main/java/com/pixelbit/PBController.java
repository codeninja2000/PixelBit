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

        view.getUndoMenuItem().setOnAction(_ -> {
            model.getCommandManager().undo();
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
            resetFilterCounts();
        });

        view.getRedoMenuItem().setOnAction(_ -> {
            model.getCommandManager().redo();
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
                }
               );

        view.getGrayscaleButton().setOnAction(_ -> applyFilter(FilterType.GRAYSCALE));
        view.getSepiaButton().setOnAction(_ -> applyFilter(FilterType.SEPIA));
        view.getInvertButton().setOnAction(_ -> applyFilter(FilterType.INVERT));

        // Set up slider listeners
        view.getBrightnessSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (model.getImage() != null) {
                // Convert slider value (-100 to 100) to brightness adjustment (-25 to 25)
                // Using 0.25 as the multiplier for very subtle changes
                int brightnessAdjustment = (int)(newVal.doubleValue() * 0.25);

                Map<String, Object> params = new HashMap<>();
                params.put("brightness", brightnessAdjustment);

                ApplyFilterCommand command = new ApplyFilterCommand(
            model.getImage(),
            model.getFilterFactory(),
            FilterType.BRIGHTNESS,
            params
        );

        model.applyEdit(command);
        view.updateImage(model.getImage());
    }
});

        view.getContrastSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            Map<String, Object> params = new HashMap<>();
            params.put("contrast", newVal.doubleValue());

            ApplyFilterCommand command = new ApplyFilterCommand(
                    model.getImage(),
                    model.getFilterFactory(),
                    FilterType.CONTRAST,
                    params
            );

            model.applyEdit(command);
            view.updateImage(model.getImage());
        });

    }

    private final Map<FilterType, Integer> filterApplicationCount = new HashMap<>();

    private void applyFilter(FilterType filterType) {
        try {
            if (model.getImage() == null) {
                view.showError("No image loaded");
                return;
            }

            // Update and get the count of filter applications
            int count = filterApplicationCount.getOrDefault(filterType, 0) + 1;
            filterApplicationCount.put(filterType, count);

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

            // Show status message
            view.showStatus(filterType.toString() + " filter applied" +
                       (count > 1 ? " (" + count + " times)" : ""));

        } catch (Exception e) {
            view.showError("Failed to apply filter: " + e.getMessage());
        }
    }

    // Add this method to reset counts when loading a new image or undoing
    private void resetFilterCounts() {
        filterApplicationCount.clear();
    }

    private void updateUndoRedoButtons() {
        view.setUndoEnabled(model.canUndo());
        view.setRedoEnabled(model.canRedo());
    }
}