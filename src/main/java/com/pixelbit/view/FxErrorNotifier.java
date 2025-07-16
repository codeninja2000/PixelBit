package com.pixelbit.view;

import javafx.scene.control.Label;

/**
 * Implementation of UIErrorNotifier for JavaFX applications.
 * This class provides methods to display error and warning messages in a Label.
 */
public class FxErrorNotifier implements UIErrorNotifier{
    private final Label errorLabel;

    /**
     * Constructor for FxErrorNotifier.
     * @param errorLabel The Label where error and warning messages will be displayed.
     */
    public FxErrorNotifier(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Displays an error message to the user.
     * @param message The error message to display.
     */
    @Override
    public void showError(String message) {
        errorLabel.setText("Error: " + message);
        errorLabel.setVisible(true);
    }

    /**
     * Displays a warning message to the user.
     * @param message The warning message to display.
     */
    @Override
    public void showWarning(String message) {
        errorLabel.setText("Warning: " + message);
        errorLabel.setVisible(true);
    }
}
