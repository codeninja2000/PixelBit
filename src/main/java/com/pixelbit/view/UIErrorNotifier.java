package com.pixelbit.view;

/**
 * Interface for notifying the user about errors and warnings in the UI.
 * Implementations should provide methods to display error and warning messages.
 */
public interface UIErrorNotifier {
    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    void showError(String message);
    /**
     * Displays a warning message to the user.
     *
     * @param message The warning message to display.
     */
    void showWarning(String message);
}
