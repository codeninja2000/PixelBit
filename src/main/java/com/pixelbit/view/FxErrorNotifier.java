package com.pixelbit.view;

import javafx.scene.control.Label;

public class FxErrorNotifier implements UIErrorNotifier{
    private final Label errorLabel;

    public FxErrorNotifier(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    @Override
    public void showError(String message) {
        errorLabel.setText("Error: " + message);
        errorLabel.setVisible(true);
    }

    @Override
    public void showWarning(String message) {
        errorLabel.setText("Warning: " + message);
        errorLabel.setVisible(true);
    }
}
