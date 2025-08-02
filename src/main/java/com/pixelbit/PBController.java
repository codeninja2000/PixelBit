package com.pixelbit;

import com.pixelbit.command.ApplyFilterCommand;
import com.pixelbit.command.ExitCommand;
import com.pixelbit.command.OpenImageCommand;
import com.pixelbit.command.SaveImageCommand;
import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.PBModel;
import com.pixelbit.model.filter.FilterType;
import com.pixelbit.view.PBImageView;
import com.pixelbit.model.EditableImage;

import java.util.HashMap;
import java.util.Map;

/**
 * PBController is the controller class for the JavaFX application.
 * It handles user interactions and updates the UI accordingly.
 * It also acts as a bridge between the view and the model.
 */
public class PBController {
    private final PBModel model; // The model that holds the image and command history
    private final PBImageView view; // The view that displays the image and UI components
    private final Map<FilterType, Integer> filterApplicationCount = new HashMap<>();

    /**
     * Constructor for PBController.
     * Initializes the controller with the model and view.
     *
     * @param model The model that holds the image and command history.
     * @param view  The view that displays the image and UI components.
     */
    public PBController(PBModel model, PBImageView view) {
        this.model = model;
        this.view = view;

        view.updateImage(null);
        updateUndoRedoButtons();

        // Set up event handlers for menu items
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

        // Set up event handlers for buttons on toolbar
        view.getUndoButton().setOnAction(_ -> {
            model.getCommandManager().undo();
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
            resetFilterCounts();
        });

        view.getRedoButton().setOnAction(_ -> {
            model.getCommandManager().redo();
            view.updateImage(model.getImage());
            updateUndoRedoButtons();
        });

        view.getGrayscaleButton().setOnAction(_ -> applyFilter(FilterType.GRAYSCALE));
        view.getSepiaButton().setOnAction(_ -> applyFilter(FilterType.SEPIA));
        view.getInvertButton().setOnAction(_ -> applyFilter(FilterType.INVERT));

        // Set up adjustment sliders and their listeners
        view.getBrightnessSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (model.getImage() != null) {
                // Convert slider value (-100 to 100) to a reasonable brightness adjustment
                // Divide by 100 to get a value between -1.0 and 1.0, then multiply by 0.5
                // to make the adjustment more subtle (-0.5 to 0.5)
                int brightnessAdjustment = (int) ((newVal.doubleValue() / 100.0) * 0.5 * 255);

                Map<String, Object> params = new HashMap<>();
                params.put("brightness", brightnessAdjustment);

                ApplyFilterCommand command = new ApplyFilterCommand(
                        model.getImage(),
                        model.getFilterFactory(),
                        FilterType.BRIGHTNESS,
                        params
                );

                try {
                    model.replaceEdit(command);
                    view.updateImage(model.getImage());
                } catch (CommandExecException e) {
                    view.showError("Failed to apply brightness: " + e.getMessage());
                }
                view.updateImage(model.getImage());
            }
        });

        view.getResetButton().setOnAction(_ -> {
            if (model.getImage() != null) {
                try {
                    model.getImage().resetToOriginal();
                    view.updateImage(model.getImage());

                    // Reset sliders to default positions
                    view.getBrightnessSlider().setValue(0);
                    view.getContrastSlider().setValue(0);

                    // Clear command history since we're resetting to original
                    model.getCommandManager().clearHistory();
                    updateUndoRedoButtons();

                    view.showStatus("Image reset to original");
                } catch (Exception e) {
                    view.showError("Failed to reset image: " + e.getMessage());
                }
            }
        });


        view.getContrastSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (model.getImage() != null) {
                Map<String, Object> params = new HashMap<>();
                params.put("contrast", newVal.doubleValue());

                ApplyFilterCommand command = new ApplyFilterCommand(
                        model.getImage(),
                        model.getFilterFactory(),
                        FilterType.CONTRAST,
                        params
                );

                try {
                    model.replaceEdit(command);
                    view.updateImage(model.getImage());
                } catch (CommandExecException e) {
                    view.showError("Failed to apply contrast: " + e.getMessage());
                }
            }
        });

        view.getCropButton().setOnAction(_ -> {
            if (model.getImage() != null) {
                if (!view.getCropButton().getText().equals("Apply Crop")) {
                    // Enter crop mode
                    view.initCropMode();
                } else {
                    // Validate and apply crop
                    if (view.validateCropParameters()) {
                        try {
                            Map<String, Object> cropParams = view.getCropParameters();
                            ApplyFilterCommand command = new ApplyFilterCommand(
                                    model.getImage(),
                                    model.getFilterFactory(),
                                    FilterType.CROP,
                                    cropParams
                            );

                            model.getCommandManager().executeCommand(command);
                            view.updateImage(model.getImage());
                            updateUndoRedoButtons();

                            // Exit crop mode
                            view.exitCropMode();
                        } catch (Exception ex) {
                            view.showError("Failed to crop image: " + ex.getMessage());
                        }
                    }
                }
            } else {
                view.showError("No image loaded");
            }
        });
    }

    /**
     * Validates the crop parameters before applying the crop filter.
     *
     * @param params The parameters for cropping, including x, y, width, and height.
     * @param image  The image to be cropped.
     * @return true if the parameters are valid, false otherwise.
     */
    private boolean validateCropParams(Map<String, Object> params, EditableImage image) {
        int x = ((Number) params.get("x")).intValue();
        int y = ((Number) params.get("y")).intValue();
        int width = ((Number) params.get("width")).intValue();
        int height = ((Number) params.get("height")).intValue();

        return x >= 0 && y >= 0 && width > 0 && height > 0 &&
               x + width <= image.getWidth() &&
               y + height <= image.getHeight();
    }

    /**
     * Applies the specified filter to the current image.
     * Updates the image view and command history accordingly.
     *
     * @param filterType The type of filter to apply.
     */
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
                case CROP -> {
                    // crop params handled separately
                }
                case SEPIA, GRAYSCALE, INVERT -> {
                } // No parameters needed
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

    /**
     * Resets the filter application counts.
     * This is called when an undo or redo operation is performed.
     */
    private void resetFilterCounts() {
        filterApplicationCount.clear();
    }

    /**
     * Updates the state of the undo and redo buttons based on the command history.
     * This method is called after executing commands or undo/redo operations.
     */
    private void updateUndoRedoButtons() {
        view.setUndoEnabled(model.canUndo());
        view.setRedoEnabled(model.canRedo());
    }
}