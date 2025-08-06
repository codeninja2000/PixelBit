package com.pixelbit;

import com.pixelbit.command.ApplyFilterCommand;
import com.pixelbit.command.ExitCommand;
import com.pixelbit.command.OpenImageCommand;
import com.pixelbit.command.SaveImageCommand;
import com.pixelbit.exception.CommandExecException;
import com.pixelbit.model.EditableImage;
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
    public static final double DEFAULT_BRIGHTNESS = 1.2;
    public static final double DEFAULT_CONTRAST = 1.1;
    public static final double MAX_SLIDER_VALUE = 100.0;
    public static final double BRIGHTNESS_SCALE_FACTOR = 0.5;
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

        initializeView();

        // Set up event handlers for menu items
        setupMenuHandlers();

        // Set up event handlers for buttons on toolbar
        setupToolbarHandlers();

        // Set up adjustment sliders and their listeners
        setupSliderHandlers();


    }

    private void setupSliderHandlers() {
        view.getBrightnessSlider().valueProperty().addListener((obs, oldVal, newVal) -> handleBrightness(newVal.doubleValue()));

        view.getContrastSlider().valueProperty().addListener((obs, oldVal, newVal) -> handleContrast(newVal.doubleValue()));

        view.getResetButton().setOnAction(_ -> handleReset());


    }

    private void setupToolbarHandlers() {
        view.getUndoButton().setOnAction(_ -> {
            handleUndo();
        });

        view.getRedoButton().setOnAction(_ -> {
            handleRedo();
        });

        view.getGrayscaleButton().setOnAction(_ -> applyFilter(FilterType.GRAYSCALE));
        view.getSepiaButton().setOnAction(_ -> applyFilter(FilterType.SEPIA));
        view.getInvertButton().setOnAction(_ -> applyFilter(FilterType.INVERT));
        view.getCropButton().setOnAction(_ -> {
            handleCrop();
        });
    }

    private void setupMenuHandlers() {
        view.getOpenItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new OpenImageCommand(model, view, view.getScene().getWindow())));

        view.getSaveMenuItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new SaveImageCommand(model, view.getScene().getWindow())));

        view.getExitItem().setOnAction(_ -> model.getCommandManager().executeCommand(
                new ExitCommand()));

        view.getUndoMenuItem().setOnAction(_ -> handleUndo());

        view.getRedoMenuItem().setOnAction(_ -> handleRedo());
    }

    private void handleRedo() {
        model.getCommandManager().redo();
        updateImageAndButtons();
    }

    private void handleUndo() {
        model.getCommandManager().undo();
        updateImageAndButtons();
        resetFilterCounts();
    }

    private void handleReset() {
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
    }

    private void handleContrast(double newVal) {
        if (model.getImage() != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("contrast", newVal);

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
    }

    private void handleBrightness(double value) {
        if (model.getImage() != null) {
            // Convert slider value (-100 to 100) to a reasonable brightness adjustment
            // Divide by 100 to get a value between -1.0 and 1.0, then multiply by 0.5
            // to make the adjustment more subtle (-0.5 to 0.5)
            int brightnessAdjustment = (int) ((value / MAX_SLIDER_VALUE) * BRIGHTNESS_SCALE_FACTOR * 255);

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
    }

    private void handleCrop() {
        if (model.getImage() != null) {
            if (!view.getCropButton().getText().equals("Apply Crop")) {
                // Enter crop mode
                view.initCropMode();
            } else {
                // Validate and apply crop
                if (view.validateCropParameters()) {
                    applyCrop();
                }
            }
        } else {
            view.showError("No image loaded");
        }
    }

    private void applyCrop() {
        try {
            Map<String, Object> cropParams = view.getCropParameters();
            ApplyFilterCommand command = new ApplyFilterCommand(
                    model.getImage(),
                    model.getFilterFactory(),
                    FilterType.CROP,
                    cropParams
            );

            model.getCommandManager().executeCommand(command);
            updateImageAndButtons();

            // Exit crop mode
            view.exitCropMode();
        } catch (Exception ex) {
            view.showError("Failed to crop image: " + ex.getMessage());
        }
    }

    private void updateImageAndButtons() {
        view.updateImage(model.getImage());
        updateUndoRedoButtons();
    }

    private void initializeView() {
        view.updateImage(null);
        updateUndoRedoButtons();
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
                case BRIGHTNESS -> parameters.put("brightness", DEFAULT_BRIGHTNESS);
                case CONTRAST -> parameters.put("contrast", DEFAULT_CONTRAST);
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
            updateImageAndButtons();

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