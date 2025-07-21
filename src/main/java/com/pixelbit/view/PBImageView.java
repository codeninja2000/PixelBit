package com.pixelbit.view;

import com.pixelbit.model.EditableImage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


/**
 * PBImageView will contain GUI logic for displaying images.
 */
public class PBImageView extends BorderPane {
private final ImageView imageView;
private final Button undoButton;
private final Button redoButton;
private final Button saveButton;
private final Label errorLabel;

public PBImageView() {
    // Initialize the UI components
    imageView = new ImageView();
    undoButton = new Button("Undo");
    redoButton = new Button("Redo");
    saveButton = new Button("Save");
    errorLabel = new Label();
    errorLabel.setVisible(false);

    // Configure ImageView
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(800);
    imageView.setFitHeight(600);

    // Create a toolbar with buttons
    ToolBar toolbar = new ToolBar(undoButton, redoButton, saveButton);
    // Set up the layout
    setTop(toolbar);
    setCenter(imageView);
    setBottom(errorLabel);
    setStyle("-fx-padding: 10;");

}
    public void updateImage(EditableImage editableImage) {
        if (editableImage != null && editableImage.getBufferedImage() != null) {
            // Convert EditableImage to JavaFX Image
            Image javafxImage = ne
        }
    }
}
